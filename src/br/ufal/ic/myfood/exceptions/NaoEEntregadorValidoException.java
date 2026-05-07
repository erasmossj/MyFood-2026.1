package br.ufal.ic.myfood.exceptions;

public class NaoEEntregadorValidoException extends Exception {
    public NaoEEntregadorValidoException() {
        super("Nao e um entregador valido");
    }
}