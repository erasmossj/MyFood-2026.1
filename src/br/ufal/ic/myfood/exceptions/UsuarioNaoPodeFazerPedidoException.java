package br.ufal.ic.myfood.exceptions;

public class UsuarioNaoPodeFazerPedidoException extends Exception {
    public UsuarioNaoPodeFazerPedidoException() {
        super("Dono de empresa nao pode fazer um pedido");
    }
}
