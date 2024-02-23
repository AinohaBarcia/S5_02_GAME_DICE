package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.exceptions;

public class NoGamesSavedException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public NoGamesSavedException(String message){super(message);}
}