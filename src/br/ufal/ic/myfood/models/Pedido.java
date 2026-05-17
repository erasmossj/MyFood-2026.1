package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    static final int INITIAL_NUMERO = 1;
    static int nextNumero = INITIAL_NUMERO;
    private int numero;
    private int cliente;
    private int empresa;
    private String estado;
    private List<Integer> produtos;
    private float valor;

    public Pedido(int cliente, int empresa) {
        setNumero(Pedido.nextNumero++);
        setCliente(cliente);
        setEmpresa(empresa);
        setEstado("aberto");
        setProdutos(new ArrayList<>());
        setValor(0);
    }

    public Pedido() {
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Integer> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Integer> produtos) {
        this.produtos = produtos;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public static void updateNextNumero(int maxNumero) {
        nextNumero = maxNumero + 1;
    }
}
