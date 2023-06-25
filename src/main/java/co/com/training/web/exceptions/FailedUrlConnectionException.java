package co.com.training.web.exceptions;

public class FailedUrlConnectionException extends AssertionError {

    public static final String FAILED_URL_CALLING = "Failed url calling: %s";

    public FailedUrlConnectionException(String message) {
        super(message);
    }
}
