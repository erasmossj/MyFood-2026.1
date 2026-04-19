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
}
