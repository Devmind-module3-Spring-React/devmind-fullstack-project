package ro.devmind.devmind_fullstack_project.exception;


public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

