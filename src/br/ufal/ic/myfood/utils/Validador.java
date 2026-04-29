package br.ufal.ic.myfood.utils;

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

    public static boolean horaValida(String hora) {
        if (hora == null || hora.isEmpty()) return false;
        if (!hora.matches("\\d{1,2}:\\d{2}")) return false;
        String[] partes = hora.split(":");
        int h = Integer.parseInt(partes[0]);
        int m = Integer.parseInt(partes[1]);
        if (h < 0 || h > 23 || m < 0 || m > 59) return false;
        return true;
    }

    public static boolean horarioValido(String abre, String fecha) {
        if (abre == null || fecha == null) return false;
        if (!horaValida(abre) || !horaValida(fecha)) return false;
        String[] abrePartes = abre.split(":");
        String[] fechaPartes = fecha.split(":");
        int abreH = Integer.parseInt(abrePartes[0]);
        int abreM = Integer.parseInt(abrePartes[1]);
        int fechaH = Integer.parseInt(fechaPartes[0]);
        int fechaM = Integer.parseInt(fechaPartes[1]);
        if (fechaH < abreH || (fechaH == abreH && fechaM <= abreM)) return false;
        return true;
    }

    public static boolean tipoMercadoValido(String tipoMercado) {
        if (tipoMercado == null || tipoMercado.isEmpty()) return false;
        return tipoMercado.equals("supermercado") || tipoMercado.equals("minimercado") || tipoMercado.equals("atacadista");
    }
}
