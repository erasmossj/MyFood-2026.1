package br.ufal.ic.myfood.exceptions;

public class UsuarioNaoPodeCriarException extends Exception {
    public UsuarioNaoPodeCriarException() {
        super("Usuario nao pode criar uma empresa");
    }
}
