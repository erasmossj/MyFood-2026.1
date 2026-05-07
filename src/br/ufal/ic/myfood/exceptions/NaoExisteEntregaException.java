package br.ufal.ic.myfood.exceptions;

public class NaoExisteEntregaException extends Exception {
    public NaoExisteEntregaException() {
        super("Nao existe entrega com esse id");
    }
}