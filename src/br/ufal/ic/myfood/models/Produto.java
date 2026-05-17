package br.ufal.ic.myfood.models;

import java.io.Serializable;

public class Produto implements Serializable {
    static final int INITIAL_ID = 1;
    static int nextId = INITIAL_ID;
    private int id;
    private String nome;
    private float valor;
    private String categoria;
    private int empresa;

    public Produto(String nome, float valor, String categoria, int empresa) {
        setId(Produto.nextId++);
        setNome(nome);
        setValor(valor);
        setCategoria(categoria);
        setEmpresa(empresa);
    }

    public Produto() {
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

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }
}
