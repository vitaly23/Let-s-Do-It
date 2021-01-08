package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidItemException() {
	}

	public InvalidItemException(String message) {
		super(message);
	}

	public InvalidItemException(Throwable cause) {
		super(cause);
	}

	public InvalidItemException(String message, Throwable cause) {
		super(message, cause);
	}

}
