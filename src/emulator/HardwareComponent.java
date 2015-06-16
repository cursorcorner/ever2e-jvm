package emulator;

import core.exception.HardwareException;

public interface HardwareComponent {

	/**
	 * Simulates events during a cold-restart such as the machine powering off and then on
	 * @throws HardwareException
	 */
	void coldRestart() throws HardwareException;
	
	/**
	 * Simulates events during a warm-restart event such as a hardware reset
	 * @throws HardwareException
	 */
	void warmRestart() throws HardwareException;
	
}
