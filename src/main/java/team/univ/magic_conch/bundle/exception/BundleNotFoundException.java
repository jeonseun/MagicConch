package team.univ.magic_conch.bundle.exception;

public class BundleNotFoundException extends RuntimeException{
    public BundleNotFoundException() {
    }

    public BundleNotFoundException(String message) {
        super(message);
    }
}
