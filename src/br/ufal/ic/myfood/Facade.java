package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.services.UsuarioService;
import br.ufal.ic.myfood.services.EmpresaService;
import br.ufal.ic.myfood.services.ProdutoService;
import br.ufal.ic.myfood.services.PedidoService;
import br.ufal.ic.myfood.repository.UsuarioRepository;
import br.ufal.ic.myfood.repository.EmpresaRepository;
import br.ufal.ic.myfood.repository.ProdutoRepository;
import br.ufal.ic.myfood.repository.PedidoRepository;

public class Facade {
    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    public Facade() {
        this.usuarioService = new UsuarioService(UsuarioRepository.load());
        this.empresaService = new EmpresaService(EmpresaRepository.load(), usuarioService);
        this.produtoService = new ProdutoService(ProdutoRepository.load(), empresaService);
        this.pedidoService = new PedidoService(PedidoRepository.load(), usuarioService, empresaService, produtoService);
    }

    public void zerarSistema(){
        UsuarioRepository.clear();
        EmpresaRepository.clear();
        ProdutoRepository.clear();
        PedidoRepository.clear();
        usuarioService.clear();
        empresaService.clear();
        produtoService.clear();
        pedidoService.clear();
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoExisteException {
        return this.usuarioService.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws UsuarioJaExisteException, NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException {
        this.usuarioService.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UsuarioJaExisteException, NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, CpfInvalidoException {
        this.usuarioService.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws LoginOuSenhaInvalidoException {
        return this.usuarioService.login(email, senha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException {
        return this.empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException, FormatoHoraInvalidoException, HorarioInvalidoException, TipoMercadoInvalidoException {
        return this.empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoPodeCriarException {
        return this.empresaService.getEmpresasDoUsuario(idDono);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        return this.empresaService.getAtributoEmpresa(empresa, atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws NomeInvalidoException, IndiceInvalidoException, EmpresaNaoExisteException, IndiceMaiorException {
        return this.empresaService.getIdEmpresa(idDono, nome, indice);
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws EmpresaNaoEncontradaException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException, ProdutoJaExisteException {
        return this.produtoService.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws ProdutoNaoCadastradoException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException {
        this.produtoService.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws ProdutoNaoEncontradoException, AtributoNaoExisteException, EmpresaNaoEncontradaException {
        return this.produtoService.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws EmpresaNaoEncontradaException {
        return this.produtoService.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws UsuarioNaoPodeFazerPedidoException, NaoPermitidoDoisPedidosException {
        return this.pedidoService.criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto) throws NaoExistePedidoEmAbertoException, ProdutoNaoPertenceException, NaoPossivelAdicionarProdutosException {
        this.pedidoService.adicionarProduto(numero, produto);
    }

    public String getPedidos(int pedido, String atributo) throws PedidoNaoEncontradoException, AtributoInvalidoException, AtributoNaoExisteException {
        return this.pedidoService.getPedidos(pedido, atributo);
    }

    public void fecharPedido(int numero) throws PedidoNaoEncontradoException {
        this.pedidoService.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws ProdutoInvalidoException, ProdutoNaoEncontradoException, NaoPossivelRemoverProdutosException, PedidoNaoEncontradoException {
        this.pedidoService.removerProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws IndiceInvalidoException, EmpresaNaoExisteException {
        return this.pedidoService.getNumeroPedido(cliente, empresa, indice);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EmpresaNaoCadastradaException, FormatoHoraInvalidoException, HorarioInvalidoException {
        this.empresaService.alterarFuncionamento(mercado, abre, fecha);
    }

    public void encerrarSistema() {
        UsuarioRepository.save(usuarioService.getUsuariosList());
        EmpresaRepository.save(empresaService.getEmpresasList());
        ProdutoRepository.save(produtoService.getProdutosList());
        PedidoRepository.save(pedidoService.getPedidosList());
    }

}