package core.cpu.cpu8;

import core.cpu.cpu8.Cpu65c02.AddressMode;
import core.cpu.cpu8.Cpu65c02.OpcodeMnemonic;

public class Opcode
{
	private Integer machineCode;
	private OpcodeMnemonic mnemonic;
	private AddressMode addressMode;
	private byte instrSize;
	private byte cycleTime;
	public Opcode(Integer machineCode, OpcodeMnemonic mnemonic, AddressMode addressMode, int instrSize, int cycleTime) {
		super();
		this.machineCode = machineCode;
		this.mnemonic = mnemonic;
		this.addressMode = addressMode;
		this.instrSize = (byte) instrSize;
		this.cycleTime = (byte) cycleTime;
	}
	public Integer getMachineCode() {
		return machineCode;
	}
	public OpcodeMnemonic getMnemonic() {
		return mnemonic;
	}
	public AddressMode getAddressMode() {
		return addressMode;
	}
	public byte getInstrSize() {
		return instrSize;
	}
	public byte getCycleTime() {
		return cycleTime;
	}
	@Override
	public String toString() {
		return "Opcode [machineCode=" + machineCode + ", mnemonic=" + mnemonic
				+ ", addressMode=" + addressMode + ", instrSize=" + instrSize
				+ ", cycleTime=" + cycleTime + "]";
	}
};

