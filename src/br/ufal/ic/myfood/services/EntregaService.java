package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repository.EntregaRepository;
import br.ufal.ic.myfood.repository.PedidoRepository;
import br.ufal.ic.myfood.models.Entrega;
import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.Entregador;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class EntregaService {
    private List<Entrega> entregasList;
    private List<Pedido> pedidosList;
    private List<Produto> produtosList;
    private List<Empresa> empresasList;
    private UsuarioService usuarioService;

    public void setPedidosList(List<Pedido> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public EntregaService(List<Entrega> entregasList, List<Pedido> pedidosList, List<Produto> produtosList, List<Empresa> empresasList, UsuarioService usuarioService) {
        this.entregasList = entregasList;
        this.pedidosList = pedidosList;
        this.produtosList = produtosList;
        this.empresasList = empresasList;
        this.usuarioService = usuarioService;
    }

    public List<Entrega> getEntregasList() {
        return entregasList;
    }

    public void addEntrega(Entrega entrega) {
        this.entregasList.add(entrega);
        EntregaRepository.save(entregasList);
    }

    public String getEntrega(int id, String atributo) throws AtributoInvalidoException, AtributoNaoExisteException {
        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }
        Entrega entrega = entregasList.stream().filter(e -> e.getId() == id).findFirst().orElseThrow(() -> new AtributoNaoExisteException());
        switch (atributo) {
            case "cliente":
                return entrega.getCliente();
            case "empresa":
                return entrega.getEmpresa();
            case "pedido":
                return String.valueOf(entrega.getPedido());
            case "entregador":
                return entrega.getEntregador();
            case "destino":
                return entrega.getDestino();
            case "produtos":
                return "{[" + entrega.getProdutos().stream().collect(Collectors.joining(", ")) + "]}";
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public int getIdEntrega(int pedido) throws NaoExisteEntregaException {
        Entrega entrega = entregasList.stream().filter(e -> e.getPedido() == pedido).findFirst().orElseThrow(() -> new NaoExisteEntregaException());
        return entrega.getId();
    }

    public void entregar(int entregaId) throws NaoExisteNadaParaSerEntregueException {
        Entrega entrega = entregasList.stream().filter(e -> e.getId() == entregaId).findFirst().orElseThrow(() -> new NaoExisteNadaParaSerEntregueException());
        Pedido pedido = pedidosList.stream().filter(p -> p.getNumero() == entrega.getPedido()).findFirst().orElseThrow(() -> new NaoExisteNadaParaSerEntregueException());
        pedido.setEstado("entregue");
        PedidoRepository.save(pedidosList);
    }

    public void clear() {
        this.entregasList = new ArrayList<>();
    }
}