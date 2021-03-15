package team.univ.magic_conch.team.exception;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException() {
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}
