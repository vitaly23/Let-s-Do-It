package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class RoleViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

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
