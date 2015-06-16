package emulator.io.display;

import core.exception.HardwareException;
import core.memory.memory8.MemoryBus8;
import emulator.HardwareManager;

public abstract class DisplayWindow extends HardwareManager {

	protected MemoryBus8 memory;
	
	public DisplayWindow( MemoryBus8 memory, long unitsPerCycle ) {
		super(unitsPerCycle);
		this.memory = memory;
	}
	
	@Override
	public void coldRestart() throws HardwareException {
	}

	@Override
	public void warmRestart() throws HardwareException {
	}
	
}
