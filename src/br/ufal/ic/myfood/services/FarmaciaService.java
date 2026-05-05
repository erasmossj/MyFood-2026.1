package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repository.EmpresaRepository;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.DonoEmpresa;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Farmacia;
import br.ufal.ic.myfood.utils.Validador;

import java.util.List;

public class FarmaciaService {
    private List<Empresa> empresasList;
    private UsuarioService usuarioService;

    public FarmaciaService(List<Empresa> empresasList, UsuarioService usuarioService) {
        this.empresasList = empresasList;
        this.usuarioService = usuarioService;
    }

    public int criarFarmacia(int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException {
        if (!Validador.nomeValido(nome)) throw new NomeInvalidoException();
        if (!Validador.enderecoValido(endereco)) throw new EnderecoEmpresaInvalidoException();
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
        Empresa empresa = new Farmacia(nome, endereco, dono, aberto24Horas, numeroFuncionarios);
        empresasList.add(empresa);
        EmpresaRepository.save(empresasList);
        return empresa.getId();
    }

    public List<Empresa> getEmpresasList() {
        return empresasList;
    }

    public void clear() {
    }
}
