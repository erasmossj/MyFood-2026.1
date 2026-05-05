package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repository.PedidoRepository;
import br.ufal.ic.myfood.repository.ProdutoRepository;
import br.ufal.ic.myfood.repository.UsuarioRepository;
import br.ufal.ic.myfood.repository.EmpresaRepository;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.DonoEmpresa;
import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.utils.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoService {
    private List<Pedido> pedidosList;
    private UsuarioService usuarioService;
    private List<Empresa> empresasList;
    private ProdutoService produtoService;

    public PedidoService(List<Pedido> pedidosList, UsuarioService usuarioService, List<Empresa> empresasList, ProdutoService produtoService) {
        this.pedidosList = pedidosList;
        this.usuarioService = usuarioService;
        this.empresasList = empresasList;
        this.produtoService = produtoService;
    }

    public List<Pedido> getPedidosList() {
        return pedidosList;
    }

    public int criarPedido(int cliente, int empresa) throws UsuarioNaoPodeFazerPedidoException, NaoPermitidoDoisPedidosException {
        Usuario usuario = usuarioService.getUsuariosList().stream().filter(u -> u.getId() == cliente).findFirst().orElse(null);
        if (usuario == null || usuario instanceof DonoEmpresa) {
            throw new UsuarioNaoPodeFazerPedidoException();
        }
        if (pedidosList.stream().anyMatch(p -> p.getCliente() == cliente && p.getEmpresa() == empresa && "aberto".equals(p.getEstado()))) throw new NaoPermitidoDoisPedidosException();
        Pedido pedido = new Pedido(cliente, empresa);
        pedidosList.add(pedido);
        PedidoRepository.save(pedidosList);
        return pedido.getNumero();
    }

    public void adicionarProduto(int numero, int produto) throws NaoExistePedidoEmAbertoException, ProdutoNaoPertenceException, NaoPossivelAdicionarProdutosException {
        Pedido pedido = pedidosList.stream().filter(p -> p.getNumero() == numero).findFirst().orElse(null);
        if (pedido == null) throw new NaoExistePedidoEmAbertoException();
        if (!"aberto".equals(pedido.getEstado())) throw new NaoPossivelAdicionarProdutosException();
        Produto prod = produtoService.getProdutosList().stream().filter(p -> p.getId() == produto).findFirst().orElse(null);
        if (prod == null || prod.getEmpresa() != pedido.getEmpresa()) throw new ProdutoNaoPertenceException();
        pedido.getProdutos().add(produto);
        pedido.setValor(pedido.getValor() + prod.getValor());
        PedidoRepository.save(pedidosList);
    }

    public String getPedidos(int pedido, String atributo) throws PedidoNaoEncontradoException, AtributoInvalidoException, AtributoNaoExisteException {
        if (atributo == null || atributo.isEmpty()) throw new AtributoInvalidoException();
        Pedido ped = pedidosList.stream().filter(p -> p.getNumero() == pedido).findFirst().orElse(null);
        if (ped == null) throw new PedidoNaoEncontradoException();
        switch (atributo) {
            case "cliente":
                Usuario usuario = usuarioService.getUsuariosList().stream().filter(u -> u.getId() == ped.getCliente()).findFirst().orElseThrow(PedidoNaoEncontradoException::new);
                return usuario.getNome();
            case "empresa":
                Empresa emp = empresasList.stream().filter(e -> e.getId() == ped.getEmpresa()).findFirst().orElseThrow(PedidoNaoEncontradoException::new);
                return emp.getNome();
            case "estado":
                return ped.getEstado();
            case "produtos":
                String conteudo = ped.getProdutos().stream().map(p -> produtoService.getProdutosList().stream().filter(pr -> pr.getId() == p).findFirst().map(Produto::getNome).orElse("")).collect(Collectors.joining(", "));
                return "{[" + conteudo + "]}";
            case "valor":
                return String.format("%.2f", ped.getValor()).replace(",", ".");
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public void fecharPedido(int numero) throws PedidoNaoEncontradoException {
        Pedido pedido = pedidosList.stream().filter(p -> p.getNumero() == numero).findFirst().orElse(null);
        if (pedido == null) throw new PedidoNaoEncontradoException();
        pedido.setEstado("preparando");
        PedidoRepository.save(pedidosList);
    }

    public void removerProduto(int pedido, String produto) throws ProdutoInvalidoException, ProdutoNaoEncontradoException, NaoPossivelRemoverProdutosException, PedidoNaoEncontradoException {
        if (produto == null || produto.isEmpty()) throw new ProdutoInvalidoException();
        Pedido ped = pedidosList.stream().filter(p -> p.getNumero() == pedido).findFirst().orElse(null);
        if (ped == null) throw new PedidoNaoEncontradoException();
        if (!"aberto".equals(ped.getEstado())) throw new NaoPossivelRemoverProdutosException();
        Produto prod = produtoService.getProdutosList().stream().filter(p -> p.getNome().equals(produto) && p.getEmpresa() == ped.getEmpresa()).findFirst().orElse(null);
        if (prod == null) throw new ProdutoNaoEncontradoException();
        if (!ped.getProdutos().remove((Integer) prod.getId())) throw new ProdutoNaoEncontradoException();
        ped.setValor(ped.getValor() - prod.getValor());
        PedidoRepository.save(pedidosList);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws IndiceInvalidoException, EmpresaNaoExisteException {
        if (!Validador.indiceValido(indice)) throw new IndiceInvalidoException();
        List<Pedido> pedidos = pedidosList.stream().filter(p -> p.getCliente() == cliente && p.getEmpresa() == empresa).collect(Collectors.toList());
        if (indice >= pedidos.size()) throw new EmpresaNaoExisteException();
        return pedidos.get(indice).getNumero();
    }

    public void clear() {
        this.pedidosList = new ArrayList<>();
    }
}
