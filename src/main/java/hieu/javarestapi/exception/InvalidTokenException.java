package hieu.javarestapi.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String message) {
        super(message);
    }
}
