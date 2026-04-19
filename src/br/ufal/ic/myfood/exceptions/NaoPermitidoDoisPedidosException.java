package br.ufal.ic.myfood.exceptions;

public class NaoPermitidoDoisPedidosException extends Exception {
    public NaoPermitidoDoisPedidosException() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
}
