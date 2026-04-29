package br.ufal.ic.myfood.models;

public final class Restaurante extends Empresa {
    private String tipoCozinha;

    public Restaurante(String nome, String endereco, int dono, String tipoCozinha) {
        super(nome, endereco, dono, "restaurante");
        setTipoCozinha(tipoCozinha);
    }

    public Restaurante() {
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    @Override
    public String getTipo() {
        return tipoCozinha;
    }
}
