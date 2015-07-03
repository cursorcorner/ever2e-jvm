package test;

import org.junit.Before;
import org.junit.Test;

import emulator.io.display.DisplayIIe;
import emulator.io.display.display8.ScanlineTracer8;

public class IIeDisplayConsoleTest {

	@Before
	public void setUp() {

	}

	@Test
	public void test() {

		for( int scanline = 0; scanline<24; scanline++ )
			System.out.println(scanline+1+" "+DisplayIIe.getAddressLo40(1, scanline, 0)+" "+DisplayIIe.getAddressLo40(1, scanline, 39));
		for( int scanline = 0; scanline<192; scanline++ )
			System.out.println(scanline+1+" "+DisplayIIe.getAddressHi40(1, scanline, 0)+" "+DisplayIIe.getAddressHi40(1, scanline, 39));

		ScanlineTracer8 tracer = new ScanlineTracer8();

		tracer.setScanStart(25, 70);
		tracer.setScanSize(65, 262);

		System.out.println("\nLores Trace Addresses");
		tracer.setTraceMap(DisplayIIe.LO40_TRACE);
		displayTest(tracer);

		System.out.println("\nHires Trace Addresses");
		tracer.setTraceMap(DisplayIIe.HI40_TRACE);
		displayTest(tracer);

	}

	private void displayTest( ScanlineTracer8 tracer ) {
		for( int y = 0; y<262; y++ ) {
			for( int x = 0; x<65; x++ ) {
				tracer.cycle();
				if( x==0 )
					System.out.print(y+"-"+tracer.getAddress()+": ");
				if( !tracer.isBlank() )
					System.out.print(" "+tracer.getAddress());
			}
			System.out.println();
		}
	}

}
