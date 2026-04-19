package br.ufal.ic.myfood.models;

import java.io.Serializable;

public class Empresa implements Serializable {
    static int nextId = 1;
    private int id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private int dono;

    public Empresa(String nome, String endereco, String tipoCozinha, int dono) {
        setId(Empresa.nextId++);
        setNome(nome);
        setEndereco(endereco);
        setTipoCozinha(tipoCozinha);
        setDono(dono);
    }

    public Empresa() {
    }

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    public int getDono() {
        return dono;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }
}
