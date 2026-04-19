package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoPertenceException extends Exception {
    public ProdutoNaoPertenceException() {
        super("O produto nao pertence a essa empresa");
    }
}
