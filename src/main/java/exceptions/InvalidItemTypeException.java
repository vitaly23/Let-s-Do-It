package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidItemTypeException extends RuntimeException {
	public InvalidItemTypeException() {
	}

	public InvalidItemTypeException(String message) {
		super(message);
	}

	public InvalidItemTypeException(Throwable cause) {
		super(cause);
	}

	public InvalidItemTypeException(String message, Throwable cause) {
		super(message, cause);
	}

}
