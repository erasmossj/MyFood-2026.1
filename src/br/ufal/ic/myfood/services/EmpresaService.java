package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repository.EmpresaRepository;
import br.ufal.ic.myfood.repository.UsuarioRepository;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.DonoEmpresa;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.utils.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmpresaService {
    private List<Empresa> empresasList;
    private UsuarioService usuarioService;

    public EmpresaService(List<Empresa> empresasList, UsuarioService usuarioService) {
        this.empresasList = empresasList;
        this.usuarioService = usuarioService;
    }

    public List<Empresa> getEmpresasList() {
        return empresasList;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException {
        Usuario usuario = usuarioService.getUsuariosList().stream().filter(u -> u.getId() == dono).findFirst().orElse(null);
        if (usuario == null || !(usuario instanceof DonoEmpresa)) {
            throw new UsuarioNaoPodeCriarException();
        }
        if (empresasList.stream().anyMatch(e -> e.getNome().equals(nome) && e.getDono() != dono)) {
            throw new EmpresaJaExisteException();
        }
        if (empresasList.stream().anyMatch(e -> e.getNome().equals(nome) && e.getEndereco().equals(endereco))) {
            throw new ProibidoCadastrarException();
        }
        Empresa empresa = new Empresa(nome, endereco, tipoCozinha, dono);
        empresasList.add(empresa);
        EmpresaRepository.save(empresasList);
        return empresa.getId();
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
                return emp.getTipoCozinha();
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

    public void clear() {
        this.empresasList = new ArrayList<>();
    }
}
