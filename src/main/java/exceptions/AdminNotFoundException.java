package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AdminNotFoundException extends RuntimeException {
	public AdminNotFoundException() {
	}

	public AdminNotFoundException(String message) {
		super(message);
	}

	public AdminNotFoundException(Throwable cause) {
		super(cause);
	}

	public AdminNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
