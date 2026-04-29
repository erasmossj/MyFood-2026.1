package br.ufal.ic.myfood.models;

import java.io.Serializable;

public abstract class Empresa implements Serializable {
    static int nextId = 1;
    private int id;
    private String nome;
    private String endereco;
    private int dono;
    private String tipoEmpresa;

    public Empresa(String nome, String endereco, int dono, String tipoEmpresa) {
        setId(Empresa.nextId++);
        setNome(nome);
        setEndereco(endereco);
        setDono(dono);
        setTipoEmpresa(tipoEmpresa);
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

    public int getDono() {
        return dono;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public abstract String getTipo();

    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }
}
