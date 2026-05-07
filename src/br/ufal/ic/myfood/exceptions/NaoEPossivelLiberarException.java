package br.ufal.ic.myfood.exceptions;

public class NaoEPossivelLiberarException extends Exception {
    public NaoEPossivelLiberarException() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }
}