package br.ufal.ic.myfood.exceptions;

public class EntregadorAindaEmEntregaException extends Exception {
    public EntregadorAindaEmEntregaException() {
        super("Entregador ainda em entrega");
    }
}