package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TraineeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TraineeNotFoundException() {
	}

	public TraineeNotFoundException(String message) {
		super(message);
	}

	public TraineeNotFoundException(Throwable cause) {
		super(cause);
	}

	public TraineeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
