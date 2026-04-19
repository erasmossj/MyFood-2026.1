package br.ufal.ic.myfood.exceptions;

public class IndiceMaiorException extends Exception {
    public IndiceMaiorException() {
        super("Indice maior que o esperado");
    }
}
