package br.ufal.ic.myfood.exceptions;

public class EnderecoInvalidoException extends Exception {
    public EnderecoInvalidoException() {
        super("Endereco invalido");
    }

    public EnderecoInvalidoException(String message) {
        super(message);
    }
}
