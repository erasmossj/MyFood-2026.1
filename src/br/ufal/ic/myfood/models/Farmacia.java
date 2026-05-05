package br.ufal.ic.myfood.models;

public final class Farmacia extends Empresa {
    private boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia(String nome, String endereco, int dono, boolean aberto24Horas, int numeroFuncionarios) {
        super(nome, endereco, dono, "farmacia");
        setAberto24Horas(aberto24Horas);
        setNumeroFuncionarios(numeroFuncionarios);
    }

    public Farmacia() {
    }

    public boolean isAberto24Horas() {
        return aberto24Horas;
    }

    public void setAberto24Horas(boolean aberto24Horas) {
        this.aberto24Horas = aberto24Horas;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(int numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

    @Override
    public String getTipo() {
        return "farmacia";
    }
}
