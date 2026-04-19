package br.ufal.ic.myfood.exceptions;

public class InvalidLoginOuSenha extends RuntimeException {
    public InvalidLoginOuSenha() {
        super("Login ou senha invalidos");
    }
}
