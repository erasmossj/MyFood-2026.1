package br.ufal.ic.myfood.exceptions;

public class UsuarioNaoEEntregadorException extends Exception {
    public UsuarioNaoEEntregadorException() {
        super("Usuario nao e um entregador");
    }
}