package Exceptions;

public class InputNumberException extends java.lang.Exception{
    public InputNumberException(){
        super("Expected a number, received something different.");
    }
}
