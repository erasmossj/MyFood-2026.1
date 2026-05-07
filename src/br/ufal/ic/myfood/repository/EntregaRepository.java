package br.ufal.ic.myfood.repository;

import br.ufal.ic.myfood.models.Entrega;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaRepository {
    public static void save(List<Entrega> entregasList) {
        try {
            String path = "data/entrega_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path))
            );
            encoder.writeObject(entregasList);
            encoder.close();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar as entregas");
        }
    }

    public static List<Entrega> load() {
        try {
            String path = "data/entrega_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path))
            );
            List<Entrega> entregasList = (List<Entrega>) decoder.readObject();
            decoder.close();
            if (!entregasList.isEmpty()) {
                int maxId = entregasList.stream().mapToInt(Entrega::getId).max().getAsInt();
                Entrega.updateNextId(maxId);
            }
            return entregasList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        save(new ArrayList<>());
    }

    public static Entrega find(int id) {
        List<Entrega> entregasList = load();
        for (Entrega entrega : entregasList) {
            if (id == entrega.getId()) {
                return entrega;
            }
        }
        return null;
    }
}