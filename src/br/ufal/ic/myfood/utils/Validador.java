package br.ufal.ic.myfood.utils;

import br.ufal.ic.myfood.exceptions.FormatoHoraInvalidoException;
import br.ufal.ic.myfood.exceptions.HorarioInvalidoException;

public class Validador {
    public static boolean senhaValida(String senha){
        if (senha == null || senha.isEmpty()) return false;
        if (senha.length() < 7) return false;
        return true;
    }
    
    public static boolean emailValido(String email){
        if (email == null || email.isEmpty()) return false;
        if (!email.contains("@")) return false;
        return true;
    }
    
    public static boolean nomeValido(String nome) {
        if (nome == null || nome.isEmpty()) return false;
        return true;
    }
    
    public static boolean enderecoValido(String endereco) {
        if (endereco == null || endereco.isEmpty()) return false;
        return true;
    }
    
    public static boolean cpfValido(String cpf) {
        if (cpf == null || cpf.isEmpty()) return false;
        if (cpf.length() != 14) return false;
        return true;
    }
    
    public static boolean valorValido(float valor) {
        if (valor <= 0) return false;
        return true;
    }
    
    public static boolean categoriaValida(String categoria) {
        if (categoria == null || categoria.isEmpty()) return false;
        return true;
    }
    
    public static boolean indiceValido(int indice) {
        if (indice < 0) return false;
        return true;
    }

    public static void validarHora(String hora) throws FormatoHoraInvalidoException, HorarioInvalidoException {
        if (hora == null) throw new HorarioInvalidoException();
        if (hora.isEmpty()) throw new FormatoHoraInvalidoException();
        if (!hora.matches("\\d{2}:\\d{2}")) throw new FormatoHoraInvalidoException();
        String[] partes = hora.split(":");
        int h = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]);
        if (h < 0 || h > 23 || m < 0 || m > 59) throw new HorarioInvalidoException();
    }

    public static void validarHorario(String abre, String fecha) throws HorarioInvalidoException {
        if (abre == null || fecha == null) throw new HorarioInvalidoException();
        String[] abrePartes = abre.split(":");
        String[] fechaPartes = fecha.split(":");
        int abreH = Integer.parseInt(abrePartes[0]);
        int abreM = Integer.parseInt(abrePartes[1]);
        int fechaH = Integer.parseInt(fechaPartes[0]);
        int fechaM = Integer.parseInt(fechaPartes[1]);
        if (fechaH < abreH || (fechaH == abreH && fechaM <= abreM)) throw new HorarioInvalidoException();
    }

    public static boolean tipoMercadoValido(String tipoMercado) {
        if (tipoMercado == null || tipoMercado.isEmpty()) return false;
        return tipoMercado.equals("supermercado") || tipoMercado.equals("minimercado") || tipoMercado.equals("atacadista");
    }
}