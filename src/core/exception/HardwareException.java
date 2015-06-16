package core.exception;

public class HardwareException extends Exception {

	private static final long serialVersionUID = 7498969308492358456L;

	public HardwareException(String message, Throwable cause) {
		super(message, cause);
	}

	public HardwareException(String message) {
		super(message);
	}

}
