package br.ufal.ic.myfood.exceptions;

public class FalhaAoSalvarException extends RuntimeException {
    public FalhaAoSalvarException(String message) {
        super(message);
    }
}
