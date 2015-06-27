package emulator.io.keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import emulator.HardwareManager;

public abstract class Keyboard extends HardwareManager implements KeyListener {

	protected Set<Integer> keyPressed = new HashSet<>();

	public Keyboard( long unitsPerCycle ) {
		super(unitsPerCycle);
	}
	
	@Override
	public void keyPressed( KeyEvent e ) {
		keyPressed.add(e.getKeyCode());
	}

	@Override
	public void keyReleased( KeyEvent e ) {
		keyPressed.remove(e.getKeyCode());
	}
	
	public boolean isKeyPressed( int keyIndex ) {
		return keyPressed.contains(keyIndex);
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
		Iterator<Integer> i = keyPressed.iterator();
		while( i.hasNext() ) 
			str.append(i.next());
		return str.toString();
	}

}
