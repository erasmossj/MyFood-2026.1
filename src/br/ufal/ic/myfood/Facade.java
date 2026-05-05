package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.services.*;
import br.ufal.ic.myfood.repository.*;
import br.ufal.ic.myfood.models.*;
import br.ufal.ic.myfood.utils.Validador;

import java.util.List;
import java.util.stream.Collectors;

public class Facade {
    private UsuarioService usuarioService;
    private RestauranteService restauranteService;
    private MercadoService mercadoService;
    private FarmaciaService farmaciaService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;
    private List<Empresa> empresasList;

    public Facade() {
        this.empresasList = EmpresaRepository.load();
        this.usuarioService = new UsuarioService(UsuarioRepository.load());
        this.restauranteService = new RestauranteService(empresasList, usuarioService);
        this.mercadoService = new MercadoService(empresasList, usuarioService);
        this.farmaciaService = new FarmaciaService(empresasList, usuarioService);
        this.produtoService = new ProdutoService(ProdutoRepository.load(), empresasList);
        this.pedidoService = new PedidoService(PedidoRepository.load(), usuarioService, empresasList, produtoService);
    }

    public void zerarSistema(){
        UsuarioRepository.clear();
        EmpresaRepository.clear();
        ProdutoRepository.clear();
        PedidoRepository.clear();
        usuarioService.clear();
        restauranteService.clear();
        mercadoService.clear();
        farmaciaService.clear();
        produtoService.clear();
        pedidoService.clear();
        this.empresasList.clear();
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

    public int criarRestaurante(int dono, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException {
        return this.restauranteService.criarRestaurante(dono, nome, endereco, tipoCozinha);
    }

    public int criarMercado(int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException, FormatoHoraInvalidoException, HorarioInvalidoException, TipoMercadoInvalidoException {
        return this.mercadoService.criarMercado(dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarFarmacia(int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException {
        return this.farmaciaService.criarFarmacia(dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) throw new TipoEmpresaInvalidoException();
        if (!tipoEmpresa.equals("restaurante")) throw new TipoEmpresaInvalidoException();
        return restauranteService.criarRestaurante(dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException, FormatoHoraInvalidoException, HorarioInvalidoException, TipoMercadoInvalidoException {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) throw new TipoEmpresaInvalidoException();
        if (!tipoEmpresa.equals("mercado")) throw new TipoEmpresaInvalidoException();
        return mercadoService.criarMercado(dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, TipoEmpresaInvalidoException {
        if (tipoEmpresa == null || tipoEmpresa.isEmpty()) throw new TipoEmpresaInvalidoException();
        if (!tipoEmpresa.equals("farmacia")) throw new TipoEmpresaInvalidoException();
        return farmaciaService.criarFarmacia(dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoPodeCriarException {
        Usuario usuario = usuarioService.getUsuariosList().stream().filter(u -> u.getId() == idDono).findFirst().orElse(null);
        if (usuario == null || !(usuario instanceof DonoEmpresa)) {
            throw new UsuarioNaoPodeCriarException();
        }
        List<Empresa> empresasProprietario = empresasList.stream().filter(e -> e.getDono() == idDono).collect(Collectors.toList());
        if (empresasProprietario.isEmpty()) {
            return "{[]}";
        }
        String conteudo = empresasProprietario.stream().map(e -> "[" + e.getNome() + ", " + e.getEndereco() + "]").collect(Collectors.joining(", "));
        return "{[" + conteudo + "]}";
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        Empresa emp = empresasList.stream().filter(e -> e.getId() == empresa).findFirst().orElse(null);
        if (emp == null) {
            throw new EmpresaNaoCadastradaException();
        }
        if (atributo == null || atributo.isEmpty()) {
            throw new AtributoInvalidoException();
        }
        switch (atributo) {
            case "nome":
                return emp.getNome();
            case "endereco":
                return emp.getEndereco();
            case "tipoCozinha":
                if (emp instanceof Restaurante) {
                    return ((Restaurante) emp).getTipoCozinha();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "abre":
                if (emp instanceof Mercado) {
                    return ((Mercado) emp).getAbre();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "fecha":
                if (emp instanceof Mercado) {
                    return ((Mercado) emp).getFecha();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "tipoMercado":
                if (emp instanceof Mercado) {
                    return ((Mercado) emp).getTipoMercado();
                } else {
                    throw new AtributoInvalidoException();
                }
            case "aberto24Horas":
                if (emp instanceof Farmacia) {
                    return String.valueOf(((Farmacia) emp).isAberto24Horas());
                } else {
                    throw new AtributoInvalidoException();
                }
            case "numeroFuncionarios":
                if (emp instanceof Farmacia) {
                    return String.valueOf(((Farmacia) emp).getNumeroFuncionarios());
                } else {
                    throw new AtributoInvalidoException();
                }
            case "dono":
                Usuario usuario = usuarioService.getUsuariosList().stream().filter(u -> u.getId() == emp.getDono()).findFirst().orElseThrow(EmpresaNaoCadastradaException::new);
                return usuario.getNome();
            default:
                throw new AtributoInvalidoException();
        }
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws NomeInvalidoException, IndiceInvalidoException, EmpresaNaoExisteException, IndiceMaiorException {
        if (nome == null || nome.isEmpty()) throw new NomeInvalidoException();
        if (!Validador.indiceValido(indice)) throw new IndiceInvalidoException();
        List<Empresa> empresasProprietario = empresasList.stream().filter(e -> e.getDono() == idDono && nome.equals(e.getNome())).collect(Collectors.toList());
        if (empresasProprietario.isEmpty()) throw new EmpresaNaoExisteException();
        if (indice >= empresasProprietario.size()) throw new IndiceMaiorException();
        return empresasProprietario.get(indice).getId();
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
        this.mercadoService.alterarFuncionamento(mercado, abre, fecha);
    }

    public void encerrarSistema() {
        UsuarioRepository.save(usuarioService.getUsuariosList());
        EmpresaRepository.save(restauranteService.getEmpresasList());
        EmpresaRepository.save(mercadoService.getEmpresasList());
        EmpresaRepository.save(farmaciaService.getEmpresasList());
        ProdutoRepository.save(produtoService.getProdutosList());
        PedidoRepository.save(pedidoService.getPedidosList());
    }

}