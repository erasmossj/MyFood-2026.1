package br.ufal.ic.myfood.repository;

import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.exceptions.FalhaAoSalvarException;
import br.ufal.ic.myfood.models.Usuario;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    public static void save(List<Usuario> usuariosList) throws FalhaAoSalvarException {
        try {
            String path = "data/usuario_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path))
            );
            encoder.writeObject(usuariosList);
            encoder.close();
        } catch (Exception e) {
            throw new FalhaAoSalvarException("Falha ao salvar os usuários");
        }
    }

    public static List<Usuario> load(){
        try {
            String path = "data/usuario_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path))
            );
            List<Usuario> usuariosList = (List<Usuario>) decoder.readObject();
            decoder.close();
            if (!usuariosList.isEmpty()) {
                int maxId = usuariosList.stream().mapToInt(Usuario::getId).max().getAsInt();
                Usuario.updateNextId(maxId);
            }
            return usuariosList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void clear(){
        save(new ArrayList<>());
    }

    public static Usuario find(String email) throws UsuarioNaoExisteException {
        List<Usuario> usuariosList = load();

        for (Usuario usuario : usuariosList){
            if(email.equals(usuario.getEmail()))
                return usuario;
        }

        throw new UsuarioNaoExisteException();
    }

    public static Usuario find(int id) throws UsuarioNaoExisteException {
        List<Usuario> usuariosList = load();

        for (Usuario usuario : usuariosList){
            if(id == usuario.getId())
                return usuario;
        }

        throw new UsuarioNaoExisteException();
    }

    public static boolean exists(String email){
        List<Usuario> usuariosList = load();

        for (Usuario usuario : usuariosList)
            if (email.equals(usuario.getEmail()))
                return true;
        return false;
    }
}
