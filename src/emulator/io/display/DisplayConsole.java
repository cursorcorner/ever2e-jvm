package emulator.io.display;

import core.exception.HardwareException;
import core.memory.memory8.MemoryBus8;
import emulator.HardwareManager;

public abstract class DisplayConsole extends HardwareManager {

	protected MemoryBus8 memory;
	Long timer;
	Double fpsAvg;
	
	public DisplayConsole( MemoryBus8 memory, long unitsPerCycle ) {
		super(unitsPerCycle);
		this.memory = memory;
	}
	
	protected void showFps(){
		long newTimer = System.currentTimeMillis();
		if( timer!=null ) {
			double fps = 1000d/(newTimer-timer);
			if( fpsAvg==null )
				fpsAvg = fps;
			fpsAvg = (fpsAvg*7d+fps)/8d;
			System.out.println(String.format("%.1f fps", fpsAvg));
			System.out.println();
		}
		timer = newTimer;
	}

	@Override
	public void coldRestart() throws HardwareException {
		warmRestart();
	}

	@Override
	public void warmRestart() throws HardwareException {
		timer = null;
		fpsAvg = null;
	}
	
}
