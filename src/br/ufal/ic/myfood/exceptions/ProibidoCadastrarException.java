package br.ufal.ic.myfood.exceptions;

public class ProibidoCadastrarException extends Exception {
    public ProibidoCadastrarException() {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }
}
