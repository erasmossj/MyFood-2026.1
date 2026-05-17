package br.ufal.ic.myfood.utils;

import br.ufal.ic.myfood.exceptions.*;

public final class Validador {
    private Validador() {}

    public static boolean senhaValida(final String senha){
        return senha != null && !senha.isEmpty() && senha.length() >= 7;
    }

    public static boolean emailValido(final String email){
        return email != null && !email.isEmpty() && email.contains("@");
    }

    public static boolean nomeValido(final String nome) {
        return nome != null && !nome.isEmpty();
    }

    public static boolean enderecoValido(final String endereco) {
        return endereco != null && !endereco.isEmpty();
    }

    public static boolean cpfValido(final String cpf) {
        return cpf != null && !cpf.isEmpty() && cpf.length() == 14;
    }

    public static boolean valorValido(final float valor) {
        return valor > 0;
    }

    public static boolean categoriaValida(final String categoria) {
        return categoria != null && !categoria.isEmpty();
    }

    public static boolean indiceValido(final int indice) {
        return indice >= 0;
    }

    public static void validarSenha(final String senha) throws SenhaInvalidoException {
        if (!senhaValida(senha)) throw new SenhaInvalidoException();
    }

    public static void validarEmail(final String email) throws EmailInvalidoException {
        if (!emailValido(email)) throw new EmailInvalidoException();
    }

    public static void validarNome(final String nome) throws NomeInvalidoException {
        if (!nomeValido(nome)) throw new NomeInvalidoException();
    }

    public static void validarEndereco(final String endereco) throws EnderecoInvalidoException {
        if (!enderecoValido(endereco)) throw new EnderecoInvalidoException();
    }

    public static void validarCpf(final String cpf) throws CpfInvalidoException {
        if (!cpfValido(cpf)) throw new CpfInvalidoException();
    }

    public static void validarValor(final float valor) throws ValorInvalidoException {
        if (!valorValido(valor)) throw new ValorInvalidoException();
    }

    public static void validarCategoria(final String categoria) throws CategoriaInvalidoException {
        if (!categoriaValida(categoria)) throw new CategoriaInvalidoException();
    }

    public static void validarHora(final String hora) throws FormatoHoraInvalidoException, HorarioInvalidoException {
        if (hora == null) throw new HorarioInvalidoException();
        if (hora.isEmpty()) throw new FormatoHoraInvalidoException();
        if (!hora.matches("\\d{2}:\\d{2}")) throw new FormatoHoraInvalidoException();
        final String[] partes = hora.split(":");
        final int h = Integer.parseInt(partes[0]);
        final int m = Integer.parseInt(partes[1]);
        if (h < 0 || h > 23 || m < 0 || m > 59) throw new HorarioInvalidoException();
    }

    public static void validarHorario(final String abre, final String fecha) throws HorarioInvalidoException {
        if (abre == null || fecha == null) throw new HorarioInvalidoException();
        final String[] abrePartes = abre.split(":");
        final String[] fechaPartes = fecha.split(":");
        final int abreH = Integer.parseInt(abrePartes[0]);
        final int abreM = Integer.parseInt(abrePartes[1]);
        final int fechaH = Integer.parseInt(fechaPartes[0]);
        final int fechaM = Integer.parseInt(fechaPartes[1]);
        if (fechaH < abreH || (fechaH == abreH && fechaM <= abreM)) throw new HorarioInvalidoException();
    }

    public static boolean tipoMercadoValido(final String tipoMercado) {
        return tipoMercado != null && !tipoMercado.isEmpty() &&
               (tipoMercado.equals("supermercado") || tipoMercado.equals("minimercado") || tipoMercado.equals("atacadista"));
    }

    public static void validarTipoMercado(final String tipoMercado) throws TipoMercadoInvalidoException {
        if (!tipoMercadoValido(tipoMercado)) throw new TipoMercadoInvalidoException();
    }

    public static boolean veiculoValido(final String veiculo) {
        return veiculo != null && !veiculo.isEmpty() &&
               (veiculo.equals("moto") || veiculo.equals("carro") || veiculo.equals("bicicleta"));
    }

    public static void validarVeiculo(final String veiculo) throws VeiculoInvalidoException {
        if (!veiculoValido(veiculo)) throw new VeiculoInvalidoException();
    }

    public static boolean placaValida(final String placa) {
        return placa != null && !placa.isEmpty() && placa.matches("[A-Z]{3}[- ]\\d{4}");
    }

    public static void validarPlaca(final String placa) throws PlacaInvalidoException {
        if (!placaValida(placa)) throw new PlacaInvalidoException();
    }
}