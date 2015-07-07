package device.display;

import core.emulator.HardwareManager;

public abstract class DisplayWindow extends HardwareManager {

	public DisplayWindow(long unitsPerCycle) {
		super(unitsPerCycle);
	}

}
