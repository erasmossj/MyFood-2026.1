package br.ufal.ic.myfood.exceptions;

public class LoginOuSenhaInvalidoException extends RuntimeException {
    public LoginOuSenhaInvalidoException() {
        super("Login ou senha invalidos");
    }
}
