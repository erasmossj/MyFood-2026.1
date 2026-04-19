package br.ufal.ic.myfood.exceptions;

public class NaoPossivelAdicionarProdutosException extends Exception {
    public NaoPossivelAdicionarProdutosException() {
        super("Nao e possivel adcionar produtos a um pedido fechado");
    }
}
