package core.memory.memory8;

import java.util.Random;

import device.keyboard.Keyboard;

public class MemoryBusDemo8 extends MemoryBus8 {

	private static Random rand = new Random(System.currentTimeMillis());
	private Keyboard keyboard;
	
	public MemoryBusDemo8(Memory8 memory, Keyboard keyboard) {
		super(memory);
		this.keyboard = keyboard;
	}

	@Override
	public int getByte( int address ) {
		if( address==0xfe )
			return rand.nextInt(256);
		if( address==0xff )
			return 0x80^(keyboard.getTypedKeyCode()&0x80|keyboard.getHeldKeyCode());
		return memory.getByte(address);
	}
	
}
