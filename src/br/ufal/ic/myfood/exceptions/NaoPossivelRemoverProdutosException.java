package br.ufal.ic.myfood.exceptions;

public class NaoPossivelRemoverProdutosException extends Exception {
    public NaoPossivelRemoverProdutosException() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
}
