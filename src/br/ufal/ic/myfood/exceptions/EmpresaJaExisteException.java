package br.ufal.ic.myfood.exceptions;

public class EmpresaJaExisteException extends Exception {
    public EmpresaJaExisteException() {
        super("Empresa com esse nome ja existe");
    }
}
