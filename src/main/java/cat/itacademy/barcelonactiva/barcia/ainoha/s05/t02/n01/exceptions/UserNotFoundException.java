package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public UserNotFoundException(String message) {
        super(message);
    }
}