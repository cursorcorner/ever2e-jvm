package emulator.io.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.BitSet;
import java.util.PrimitiveIterator.OfInt;

import emulator.HardwareManager;

public abstract class Keyboard extends HardwareManager implements KeyListener {

	protected BitSet keyPressed = new BitSet();

	public Keyboard( long unitsPerCycle ) {
		super(unitsPerCycle);
	}
	
	@Override
	public void keyPressed( KeyEvent e ) {
		keyPressed.set(e.getKeyCode());
	}

	@Override
	public void keyReleased( KeyEvent e ) {
		keyPressed.clear(e.getKeyCode());
	}
	
	public boolean isKeyPressed( int keyIndex ) {
		return keyPressed.get(keyIndex);
	}
	
	public abstract int getHeldKeyCode();
	
	public abstract int getTypedKeyCode();
	
	@Override
	public final void keyTyped( KeyEvent e ) {
		// Ignore system-timed key presses in order to fully
		//   emulate timing and key-combination behavior
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		OfInt i = keyPressed.stream().iterator();
		while( i.hasNext() ) 
			str.append(i.next());
		return str.toString();
	}

}
