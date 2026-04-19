package br.ufal.ic.myfood.models;

public final class DonoEmpresa extends Usuario {
    private String cpf;

    public DonoEmpresa(String nome, String email, String senha, String endereco, String cpf) {
        setId(Usuario.nextId++);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setEndereco(endereco);
        setCpf(cpf);
    }

    public DonoEmpresa() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
