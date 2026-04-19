package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repository.EmpresaRepository;
import br.ufal.ic.myfood.repository.ProdutoRepository;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.utils.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoService {
    private List<Produto> produtosList;
    private EmpresaService empresaService;

    public ProdutoService(List<Produto> produtosList, EmpresaService empresaService) {
        this.produtosList = produtosList;
        this.empresaService = empresaService;
    }

    public List<Produto> getProdutosList() {
        return produtosList;
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws EmpresaNaoEncontradaException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException, ProdutoJaExisteException {
        if (empresaService.getEmpresasList().stream().noneMatch(e -> e.getId() == empresa)) throw new EmpresaNaoEncontradaException();
        if (!Validador.nomeValido(nome)) throw new NomeInvalidoException();
        if (!Validador.valorValido(valor)) throw new ValorInvalidoException();
        if (!Validador.categoriaValida(categoria)) throw new CategoriaInvalidoException();
        if (produtosList.stream().anyMatch(p -> p.getNome().equals(nome) && p.getEmpresa() == empresa)) throw new ProdutoJaExisteException();
        Produto produto = new Produto(nome, valor, categoria, empresa);
        produtosList.add(produto);
        ProdutoRepository.save(produtosList);
        return produto.getId();
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws ProdutoNaoCadastradoException, NomeInvalidoException, ValorInvalidoException, CategoriaInvalidoException {
        Produto prod = produtosList.stream().filter(p -> p.getId() == produto).findFirst().orElse(null);
        if (prod == null) throw new ProdutoNaoCadastradoException();
        if (!Validador.nomeValido(nome)) throw new NomeInvalidoException();
        if (!Validador.valorValido(valor)) throw new ValorInvalidoException();
        if (!Validador.categoriaValida(categoria)) throw new CategoriaInvalidoException();
        prod.setNome(nome);
        prod.setValor(valor);
        prod.setCategoria(categoria);
        ProdutoRepository.save(produtosList);
    }

    public String getProduto(String nome, int empresa, String atributo) throws ProdutoNaoEncontradoException, AtributoNaoExisteException, EmpresaNaoEncontradaException {
        if (empresaService.getEmpresasList().stream().noneMatch(e -> e.getId() == empresa)) throw new EmpresaNaoEncontradaException();
        Produto prod = produtosList.stream().filter(p -> p.getNome().equals(nome) && p.getEmpresa() == empresa).findFirst().orElse(null);
        if (prod == null) throw new ProdutoNaoEncontradoException();
        switch (atributo) {
            case "valor":
                return String.format("%.2f", prod.getValor()).replace(",", ".");
            case "categoria":
                return prod.getCategoria();
            case "empresa":
                Empresa emp = empresaService.getEmpresasList().stream().filter(e -> e.getId() == prod.getEmpresa()).findFirst().orElseThrow(ProdutoNaoEncontradoException::new);
                return emp.getNome();
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String listarProdutos(int empresa) throws EmpresaNaoEncontradaException {
        if (empresaService.getEmpresasList().stream().noneMatch(e -> e.getId() == empresa)) throw new EmpresaNaoEncontradaException();
        List<Produto> produtosEmpresa = produtosList.stream().filter(p -> p.getEmpresa() == empresa).collect(Collectors.toList());
        String conteudo = produtosEmpresa.stream().map(Produto::getNome).collect(Collectors.joining(", "));
        return "{[" + conteudo + "]}";
    }

    public void clear() {
        this.produtosList = new ArrayList<>();
    }
}
