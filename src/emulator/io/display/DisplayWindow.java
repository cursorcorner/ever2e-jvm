package emulator.io.display;

import core.exception.HardwareException;
import emulator.HardwareManager;

public abstract class DisplayWindow extends HardwareManager {

	public DisplayWindow(long unitsPerCycle) {
		super(unitsPerCycle);
	}

}
