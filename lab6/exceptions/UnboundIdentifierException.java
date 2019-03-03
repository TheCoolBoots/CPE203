package exceptions;

public class UnboundIdentifierException extends RuntimeException {
	public UnboundIdentifierException(final String msg) {
		super(msg);
	}
}
