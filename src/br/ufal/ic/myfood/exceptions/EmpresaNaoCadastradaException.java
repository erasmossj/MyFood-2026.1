package br.ufal.ic.myfood.exceptions;

public class EmpresaNaoCadastradaException extends Exception {
    public EmpresaNaoCadastradaException() {
        super("Empresa nao cadastrada");
    }

    public EmpresaNaoCadastradaException(String message) {
        super(message);
    }
}
