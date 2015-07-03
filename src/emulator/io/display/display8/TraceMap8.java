package emulator.io.display.display8;

public class TraceMap8 {

	private int wordHighStartBit;
	private int pageBit;
	private short [] scanlineTraceWord;
	private int index;

	public TraceMap8( int wordHighStartBit, int scanCount ) {
		this.wordHighStartBit = wordHighStartBit;
		index = 0;
		scanlineTraceWord = new short[scanCount];
	}

	public void addScanlineTrace( int wordHigh, int wordLow ) {
		scanlineTraceWord[index++] = (short) (wordHigh<<wordHighStartBit|wordLow);
	}

	public void addScanlineTrace( int wordHigh, int wordLow, int repeat ) {
		for( int i = 0; i<repeat; i++ )
			addScanlineTrace(wordHigh, wordLow);
	}

	public void setPageBit( int bit ) {
		pageBit = bit;
	}

	public int getScan( int scanline, int page ) {
		return (page<<pageBit)|Short.toUnsignedInt(scanlineTraceWord[scanline]);
	}

	public int getWordHighStartBit() {
		return wordHighStartBit;
	}

}
