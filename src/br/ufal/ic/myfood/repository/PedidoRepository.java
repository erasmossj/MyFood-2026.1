package br.ufal.ic.myfood.repository;

import br.ufal.ic.myfood.models.Pedido;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    public static void save(List<Pedido> pedidosList) {
        try {
            String path = "data/pedido_data.xml";
            XMLEncoder encoder = new XMLEncoder(
                    new BufferedOutputStream(new FileOutputStream(path))
            );
            encoder.writeObject(pedidosList);
            encoder.close();
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar os pedidos");
        }
    }

    public static List<Pedido> load() {
        try {
            String path = "data/pedido_data.xml";
            XMLDecoder decoder = new XMLDecoder(
                    new BufferedInputStream(new FileInputStream(path))
            );
            List<Pedido> pedidosList = (List<Pedido>) decoder.readObject();
            decoder.close();
            if (!pedidosList.isEmpty()) {
                int maxNumero = pedidosList.stream().mapToInt(Pedido::getNumero).max().getAsInt();
                Pedido.updateNextNumero(maxNumero);
            }
            return pedidosList;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void clear() {
        save(new ArrayList<>());
    }

    public static Pedido find(int numero) {
        List<Pedido> pedidosList = load();
        for (Pedido pedido : pedidosList) {
            if (numero == pedido.getNumero()) {
                return pedido;
            }
        }
        return null;
    }

    public static boolean hasOpenPedido(int cliente, int empresa) {
        List<Pedido> pedidosList = load();
        for (Pedido pedido : pedidosList) {
            if (pedido.getCliente() == cliente && pedido.getEmpresa() == empresa && "aberto".equals(pedido.getEstado())) {
                return true;
            }
        }
        return false;
    }

    public static List<Pedido> getPedidosClienteEmpresa(int cliente, int empresa) {
        List<Pedido> pedidosList = load();
        return pedidosList.stream().filter(p -> p.getCliente() == cliente && p.getEmpresa() == empresa).sorted((p1, p2) -> Integer.compare(p1.getNumero(), p2.getNumero())).collect(java.util.stream.Collectors.toList());
    }
}
