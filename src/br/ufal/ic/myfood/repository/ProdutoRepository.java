package br.ufal.ic.myfood.repository;

import br.ufal.ic.myfood.models.Produto;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    public static void save(List<Produto> produtosList) {
        try {
            String path = "data/produto_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path))
            );
            encoder.writeObject(produtosList);
            encoder.close();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar os produtos");
        }
    }

    public static List<Produto> load() {
        try {
            String path = "data/produto_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path))
            );
            List<Produto> produtosList = (List<Produto>) decoder.readObject();
            decoder.close();
            if (!produtosList.isEmpty()) {
                int maxId = produtosList.stream().mapToInt(Produto::getId).max().getAsInt();
                Produto.updateNextId(maxId);
            }
            return produtosList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        save(new ArrayList<>());
    }

    public static Produto find(int id) {
        List<Produto> produtosList = load();
        for (Produto produto : produtosList) {
            if (id == produto.getId()) {
                return produto;
            }
        }
        return null;
    }

    public static Produto findByNomeEmpresa(String nome, int empresa) {
        List<Produto> produtosList = load();
        for (Produto produto : produtosList) {
            if (nome.equals(produto.getNome()) && empresa == produto.getEmpresa()) {
                return produto;
            }
        }
        return null;
    }

    public static boolean existsNomeEmpresa(String nome, int empresa) {
        return findByNomeEmpresa(nome, empresa) != null;
    }
}
