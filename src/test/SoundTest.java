package test;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundTest {

	private static final int SAMPLE_BUFFER_SIZE = 1024;   // Lag of 1/40th to 1/20th of a second at 22050Hz
	private static final float MAX_SOUND_WORD = 32767f;   // Min and max limits on sound resolution
	private static final float MIN_SOUND_WORD = -32768f;
	private static final float SAMPLE_RATE = 22050;       // Sound samples per second
	private static final int SAMPLE_DURATION = (int) (1000000000/SAMPLE_RATE);    // Sample length in nanoseconds

	private static final int SKIP_CYCLES = 4;  // Granularity of physics simulation (speaker sensitivity)
	                                           // A value of 1 checks for changes every cycle, 4 every 4 cycles
	private static int cycleCount;             // Number of cycles played (used to tell which cycles to skip)
	
	private static final float FRICTION = .01f;                                 // 1% acceleration loss per unit velocity
	private static final int CHARGE_DURATION = 20/SKIP_CYCLES;                  // 20 cycles
	private static final float MAGNET_FORCE = 11f*SKIP_CYCLES*SKIP_CYCLES;      // Magnet acceleration in units distance per increment
	private static final float SPRING_FORCE = .00049f*SKIP_CYCLES*SKIP_CYCLES;  // Units reverse spring acceleration per unit distance per increment

/*
	// Remove typical Apple IIe speaker static
	private static final float FRICTION = .02;        
	private static final Sint32 CHARGE_DURATION = 10/SKIP_CYCLES; 
	private static final float MAGNET_FORCE = 34*SKIP_CYCLES*SKIP_CYCLES;
	private static final float SPRING_FORCE = .0002*SKIP_CYCLES*SKIP_CYCLES;
*/

	private byte [] buffer = new byte[SAMPLE_BUFFER_SIZE];
	private AudioFormat audioFormat;
	private SourceDataLine sdl;
    private int bufferIndex;

	private float pos;
	private float vel;

	private boolean toggleChargeNegative;
	private float charge;
	private int chargeDur;
	
	private float sampleSum;
	private int sampleTotal;
	private int sampleLength;
	
	public SoundTest() throws LineUnavailableException {
		
        audioFormat = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
        sdl = AudioSystem.getSourceDataLine(audioFormat);
        bufferIndex = 0;
	    open();

		pos = 0f;
		vel = 0f;
		toggleChargeNegative = false;
		charge = 0f;
		chargeDur = 0;
		
		sampleSum = 0f;
		sampleTotal = 0;
		sampleLength = 0;
	
		cycleCount = 0;
		
	}
	
	public void toggle()
	{
		chargeDur = CHARGE_DURATION;
		charge = toggleChargeNegative ? -MAGNET_FORCE : MAGNET_FORCE;
		toggleChargeNegative = !toggleChargeNegative;
	}

	public void cycle( int elapsedNanoseconds )
	{

		sampleLength += elapsedNanoseconds;
	
		// Slow down cycle feed to avoid needlessly ramping up CPU
		// Speaker can only be toggled at most approx every 4 cycles (255,000 Hz) which is the value allowed here
		// This speed provides about 12 averaged physics calculations per sound sample
		if( (cycleCount++)%SKIP_CYCLES!=0 )
			return;
	
		// Check for underflow and set variables to 0 as needed to speed up math routines in idle state
		/// It may be slightly faster to use fixed point integers ///
		if( pos>-1./32768. && pos<1./32767. )
			pos = 0f;
		if( vel>-1./32768. && vel<1./32767. )
			vel = 0f;
	
		// Move diafragm
		float accel = -SPRING_FORCE*pos;
	
		accel += charge;
		accel -= vel*FRICTION;
		vel += accel;
		pos += vel;
	
		// Check magnet-charge duration expiration
		if( chargeDur>0 ) {
			chargeDur--;
			if( chargeDur==0 )
				charge = 0f;
		}
	
		if( pos<-100000. || pos>100000. ) {
			System.err.println("Warning: diafragm position far exceeds limits:\n");
			System.err.println("pos "+new Float(pos)+", vel "+new Float(vel)+", charge "+new Float(charge)+
					", accel "+new Float(accel)+" chargeDur: "+chargeDur);
		}
	
		// Apply diafragm position to average for sampling and commit sample when necessary
		sampleTotal++;
		sampleSum += pos;
	
		if( sampleLength >= SAMPLE_DURATION ) {
	
			sampleSum /= (float) sampleTotal;
			sampleSum = sampleSum>MAX_SOUND_WORD ? MAX_SOUND_WORD:sampleSum;
			sampleSum = sampleSum<MIN_SOUND_WORD ? MIN_SOUND_WORD:sampleSum;
	
			buffer[bufferIndex++] = (byte) ((sampleSum/256f) + 128);
			if( bufferIndex==SAMPLE_BUFFER_SIZE ) {
				bufferIndex = 0;
				sdl.write(buffer, 0, SAMPLE_BUFFER_SIZE);
			}
			sampleLength -= SAMPLE_DURATION;
			
			sampleSum = 0f;
			sampleTotal = 0;
	
		}
	
	}
	
    private void open() throws LineUnavailableException {
		sdl.open();
		sdl.start();
	}

    private void close() {
	    sdl.drain();
	    sdl.stop();
	}

	public static void main( String [] args ) throws LineUnavailableException {
		SoundTest speaker = new SoundTest();
		long timer = System.currentTimeMillis();
		for( int count = 0; count<100000; count++ ) {
			for( int cycleWait = 0; cycleWait<500; cycleWait++ ) {
				long newTime = System.currentTimeMillis();
				long clockTime = newTime-timer;
				long waitTime = 4-clockTime;
				if( waitTime>0 )
					try { Thread.sleep(waitTime); } catch (InterruptedException e) { e.printStackTrace(); }
				speaker.cycle(4);
			}
			speaker.toggle();
		}
		speaker.close();
	}

}
