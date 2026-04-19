package br.ufal.ic.myfood.exceptions;

public class NaoExistePedidoEmAbertoException extends Exception {
    public NaoExistePedidoEmAbertoException() {
        super("Nao existe pedido em aberto");
    }
}
