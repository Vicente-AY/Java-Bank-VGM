package Exceptions;

public class InputStringException extends java.lang.Exception{
    public InputStringException(){
        super("Expected a string, received something different.");
    }
}
