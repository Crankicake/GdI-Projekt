package de.tu_darmstadt.gdi1.gorillas.main;

public class GorillasException extends Throwable {

	private static final long serialVersionUID = 1L;
	private final String message;

	private final ExceptionReason exceptionReason;
	private final Throwable innerException;

	public GorillasException(Throwable innerException, String message,
			int exceptionReason) {

		this.innerException = innerException;
		this.message = message;
		ExceptionReason[] gründe = ExceptionReason.values();
		this.exceptionReason = (exceptionReason < 0 || exceptionReason > gründe.length - 1) ? ExceptionReason.Undefined
				: gründe[exceptionReason];
	}

	public GorillasException(Throwable innerException, String message,
			ExceptionReason exceptionReason) {

		this.innerException = innerException;
		this.message = message;
		this.exceptionReason = exceptionReason;
	}

	public String getMessage() {
		return message;
	}

	public ExceptionReason getReason() {
		return exceptionReason;
	}

	public Throwable getInnerException() {
		return innerException;
	}
}