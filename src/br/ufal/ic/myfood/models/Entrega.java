package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.List;

public class Entrega implements Serializable {
    static int nextId = 1;
    private int id;
    private String cliente;
    private String empresa;
    private int pedido;
    private String entregador;
    private String destino;
    private List<String> produtos;

    public Entrega(String cliente, String empresa, int pedido, String entregador, String destino, List<String> produtos) {
        setId(Entrega.nextId++);
        setCliente(cliente);
        setEmpresa(empresa);
        setPedido(pedido);
        setEntregador(entregador);
        setDestino(destino);
        setProdutos(produtos);
    }

    public Entrega() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public int getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido = pedido;
    }

    public String getEntregador() {
        return entregador;
    }

    public void setEntregador(String entregador) {
        this.entregador = entregador;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<String> produtos) {
        this.produtos = produtos;
    }

    public static void updateNextId(int maxId) {
        nextId = maxId + 1;
    }
}