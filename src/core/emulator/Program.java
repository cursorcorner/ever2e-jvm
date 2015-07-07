package core.emulator;


public class Program {

	public static enum MachineLayoutType {
		DEMO_32x32,
		DEMO_32x32_CONSOLE,
		APPLE_IIE,
		DEBUG_65C02
	}

	private MachineLayoutType layout;
	private int programStart;
	private int[] opcodeList;
	
	public Program(MachineLayoutType layout, int programStart, String code) {
		try {
			this.layout = layout;
			this.programStart = programStart;
			String[] codeList;
			if( code.contains(" ") )
				codeList = code.split(" ");
			else {
				codeList = new String[code.length()>>1];
				for( int i = 0; i<code.length(); i+=2 )
					codeList[i>>1] = code.substring(i, i+2);
			}
			opcodeList = new int[codeList.length];
			int index = 0;
			for( String codeStr : codeList )
				opcodeList[index++] = Integer.parseInt(codeStr, 16);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public MachineLayoutType getLayout() {
		return layout;
	}
	public int getProgramStart() {
		return programStart;
	}
	public int [] getCode() {
		return opcodeList;
	}
	@Override
	public String toString() {
		return "Program [layout=" + layout + ", programStart="
				+ programStart + ", size=" + opcodeList.length + "]";
	}

}
