package emulator.machine8;

import java.util.PriorityQueue;

import core.exception.HardwareException;
import emulator.HardwareManager;
import emulator.machine.Emulator;

public class Emulate65c02 extends Emulator {

	public Emulate65c02(PriorityQueue<HardwareManager> hardwareManagerQueue,
			int granularityBitsPerSecond) throws HardwareException {
		super(hardwareManagerQueue, granularityBitsPerSecond);
	}

/*	@Override
	public void start() throws HardwareException, InterruptedException {
		long timer = System.currentTimeMillis();
		long oldTime = timer;
		do {
			HardwareManager nextManager = hardwareManagerQueue.remove();
			long newTime = System.currentTimeMillis();
			if( newTime-oldTime>100 )
				timer += newTime-oldTime-100;
			oldTime = newTime;
			long clockTime = newTime-timer;
			long waitTime = (nextManager.getNextCycleUnits()>>granularityBitsPerSecond)-clockTime;
			if( waitTime>0 )
				Thread.sleep(waitTime);
			nextManager.cycle();
			hardwareManagerQueue.add(nextManager);
			if( nextManager instanceof Cpu65c02 ) {
				if( ((Cpu65c02) nextManager).getOpcode().getMnemonic()==Cpu65c02.OpcodeMnemonic.BRK )
					break;
			}
		} while(true);		
		
		// Show final state of display(s) before returning
		HardwareManager[] managerList = new HardwareManager[hardwareManagerQueue.size()];
		for( HardwareManager manager : hardwareManagerQueue.toArray(managerList) )
			if( manager instanceof DisplayConsole ) {
				manager.coldRestart();
				manager.cycle();
			}
	}
*/
}
