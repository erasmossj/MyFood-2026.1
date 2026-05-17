package br.ufal.ic.myfood.models;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
    static final int INITIAL_ID = 1;
    static int nextId = INITIAL_ID;
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }
}
