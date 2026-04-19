package br.ufal.ic.myfood.models;

public final class Cliente extends Usuario {
    public Cliente(String nome, String email, String senha, String endereco) {
        setId(Usuario.nextId++);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setEndereco(endereco);
    }

    public Cliente() {
    }
}
