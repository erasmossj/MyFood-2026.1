package br.ufal.ic.myfood.exceptions;

public class EmpresaNaoExisteException extends Exception {
    public EmpresaNaoExisteException() {
        super("Nao existe empresa com esse nome");
    }
}
