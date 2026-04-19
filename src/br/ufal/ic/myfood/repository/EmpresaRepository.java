package br.ufal.ic.myfood.repository;

import br.ufal.ic.myfood.models.Empresa;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository {
    public static void save(List<Empresa> empresasList) {
        try {
            String path = "data/empresa_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path))
            );
            encoder.writeObject(empresasList);
            encoder.close();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar as empresas");
        }
    }

    public static List<Empresa> load() {
        try {
            String path = "data/empresa_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path))
            );
            List<Empresa> empresasList = (List<Empresa>) decoder.readObject();
            decoder.close();
            if (!empresasList.isEmpty()) {
                int maxId = empresasList.stream().mapToInt(Empresa::getId).max().getAsInt();
                Empresa.updateNextId(maxId);
            }
            return empresasList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        save(new ArrayList<>());
    }

    public static Empresa find(int id) {
        List<Empresa> empresasList = load();
        for (Empresa empresa : empresasList) {
            if (id == empresa.getId()) {
                return empresa;
            }
        }
        return null;
    }

    public static boolean existsNomeEndereco(String nome, String endereco) {
        List<Empresa> empresasList = load();
        for (Empresa empresa : empresasList) {
            if (nome.equals(empresa.getNome()) && endereco.equals(empresa.getEndereco())) {
                return true;
            }
        }
        return false;
    }

    public static boolean existsNomeDonoDiferente(String nome, int dono) {
        List<Empresa> empresasList = load();
        for (Empresa empresa : empresasList) {
            if (nome.equals(empresa.getNome()) && empresa.getDono() != dono) {
                return true;
            }
        }
        return false;
    }
}
