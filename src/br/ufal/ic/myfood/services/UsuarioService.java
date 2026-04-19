package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.repository.UsuarioRepository;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.Cliente;
import br.ufal.ic.myfood.models.DonoEmpresa;
import br.ufal.ic.myfood.utils.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {
    private List<Usuario> usuariosList;

    public UsuarioService(List<Usuario> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public List<Usuario> getUsuariosList() {
        return usuariosList;
    }

    public void addUsuario(Usuario usuario) {
        this.usuariosList.add(usuario);
        UsuarioRepository.save(usuariosList);
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoExisteException {
        Usuario usuario = usuariosList.stream().filter(u -> u.getId() == id).findFirst().orElseThrow(UsuarioNaoExisteException::new);
        switch (atributo) {
            case "nome":
                return usuario.getNome();
            case "email":
                return usuario.getEmail();
            case "endereco":
                return usuario.getEndereco();
            case "senha":
                return usuario.getSenha();
            case "cpf":
                if (usuario instanceof DonoEmpresa) {
                    return ((DonoEmpresa) usuario).getCpf();
                }
                throw new UsuarioNaoExisteException();
            default:
                throw new UsuarioNaoExisteException();
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws UsuarioJaExisteException, NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException {
        if (!Validador.nomeValido(nome)) throw new NomeInvalidoException();
        if (!Validador.emailValido(email)) throw new EmailInvalidoException();
        if (!Validador.senhaValida(senha)) throw new SenhaInvalidoException();
        if (!Validador.enderecoValido(endereco)) throw new EnderecoInvalidoException();
        if (usuariosList.stream().anyMatch(u -> u.getEmail().equals(email))) throw new UsuarioJaExisteException();

        Cliente cliente = new Cliente(nome, email, senha, endereco);
        addUsuario(cliente);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws UsuarioJaExisteException, NomeInvalidoException, EmailInvalidoException, SenhaInvalidoException, EnderecoInvalidoException, CpfInvalidoException {
        if (!Validador.nomeValido(nome)) throw new NomeInvalidoException();
        if (!Validador.emailValido(email)) throw new EmailInvalidoException();
        if (!Validador.senhaValida(senha)) throw new SenhaInvalidoException();
        if (!Validador.enderecoValido(endereco)) throw new EnderecoInvalidoException();
        if (!Validador.cpfValido(cpf)) throw new CpfInvalidoException();
        if (usuariosList.stream().anyMatch(u -> u.getEmail().equals(email))) throw new UsuarioJaExisteException();

        DonoEmpresa dono = new DonoEmpresa(nome, email, senha, endereco, cpf);
        addUsuario(dono);
    }

    public int login(String email, String senha) throws InvalidLoginOuSenha {
        if (!Validador.emailValido(email)) throw new InvalidLoginOuSenha();
        if (!Validador.senhaValida(senha)) throw new InvalidLoginOuSenha();
        Usuario usuario = usuariosList.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
        if (usuario == null || !senha.equals(usuario.getSenha())) {
            throw new InvalidLoginOuSenha();
        }
        return usuario.getId();
    }

    public void clear() {
        this.usuariosList = new ArrayList<>();
    }
}
