package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoleViolationException extends RuntimeException {
	public RoleViolationException() {
	}

	public RoleViolationException(String message) {
		super(message);
	}

	public RoleViolationException(Throwable cause) {
		super(cause);
	}

	public RoleViolationException(String message, Throwable cause) {
		super(message, cause);
	}
}
