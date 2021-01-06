package exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidUserException() {
	}

	public InvalidUserException(String message) {
		super(message);
	}

	public InvalidUserException(Throwable cause) {
		super(cause);
	}

	public InvalidUserException(String message, Throwable cause) {
		super(message, cause);
	}
}
