package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repository.EmpresaRepository;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.DonoEmpresa;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Mercado;
import br.ufal.ic.myfood.utils.Validador;

import java.util.List;

public class MercadoService {
    private List<Empresa> empresasList;
    private UsuarioService usuarioService;

    public MercadoService(List<Empresa> empresasList, UsuarioService usuarioService) {
        this.empresasList = empresasList;
        this.usuarioService = usuarioService;
    }

    public int criarMercado(int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws UsuarioNaoPodeCriarException, EmpresaJaExisteException, ProibidoCadastrarException, NomeInvalidoException, EnderecoEmpresaInvalidoException, FormatoHoraInvalidoException, HorarioInvalidoException, TipoMercadoInvalidoException {
        if (!Validador.nomeValido(nome)) throw new NomeInvalidoException();
        if (!Validador.enderecoValido(endereco)) throw new EnderecoEmpresaInvalidoException();
        Validador.validarHora(abre);
        Validador.validarHora(fecha);
        Validador.validarHorario(abre, fecha);
        if (!Validador.tipoMercadoValido(tipoMercado)) throw new TipoMercadoInvalidoException();
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
        Empresa empresa = new Mercado(nome, endereco, dono, abre, fecha, tipoMercado);
        empresasList.add(empresa);
        EmpresaRepository.save(empresasList);
        return empresa.getId();
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws EmpresaNaoCadastradaException, FormatoHoraInvalidoException, HorarioInvalidoException {
        Empresa emp = empresasList.stream().filter(e -> e.getId() == mercado).findFirst().orElse(null);
        if (emp == null || !(emp instanceof Mercado)) {
            throw new EmpresaNaoCadastradaException("Nao e um mercado valido");
        }
        Validador.validarHora(abre);
        Validador.validarHora(fecha);
        Validador.validarHorario(abre, fecha);
        Mercado m = (Mercado) emp;
        m.setAbre(abre);
        m.setFecha(fecha);
        EmpresaRepository.save(empresasList);
    }
}

