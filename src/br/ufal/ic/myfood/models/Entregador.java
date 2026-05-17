package br.ufal.ic.myfood.models;

public final class Entregador extends Usuario {
    private String veiculo;
    private String placa;

    public Entregador(String nome, String email, String senha, String endereco, String veiculo, String placa) {
        setId(Usuario.nextId++);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setEndereco(endereco);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public Entregador() {
        this.veiculo = null;
        this.placa = null;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(final String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(final String placa) {
        this.placa = placa;
    }
}
