package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidOperationTypeException extends RuntimeException {
	public InvalidOperationTypeException() {
	}

	public InvalidOperationTypeException(String message) {
		super(message);
	}

	public InvalidOperationTypeException(Throwable cause) {
		super(cause);
	}

	public InvalidOperationTypeException(String message, Throwable cause) {
		super(message, cause);
	}
}
