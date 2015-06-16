package test;

import emulator.io.display.DisplayConsoleAppleIIe;

import org.junit.Test;

public class IIeDisplayConsoleTest {

	@Test
	public void test() {
		for( int scanline = 0; scanline<24; scanline++ )
			System.out.println(scanline+" "+DisplayConsoleAppleIIe.getAddressLo40(1, scanline, 0)+" "+DisplayConsoleAppleIIe.getAddressLo40(1, scanline, 39));
		for( int scanline = 0; scanline<192; scanline++ )
			System.out.println(scanline+" "+DisplayConsoleAppleIIe.getAddressHi40(1, scanline, 0)+" "+DisplayConsoleAppleIIe.getAddressHi40(1, scanline, 39));
	}
	
}
