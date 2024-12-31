package com.saudemosaico.especialistas.util;

import java.util.regex.Pattern;

public class ValidadorUtil {
    private static final Pattern PADRAO_CRM = Pattern.compile("^\\d{5,6}[-/]?[A-Z]{2}$");
    private static final Pattern PADRAO_TELEFONE = Pattern.compile("^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$");
    private static final Pattern PADRAO_NOME = Pattern.compile("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]{2,90}$");
    private static final int MAX_ESPECIALIDADES = 4;

    public static boolean validarCRM(String crm) {
        return crm != null && PADRAO_CRM.matcher(crm).matches();
    }

    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        int[] numeros = cpf.chars().map(Character::getNumericValue).toArray();

        // Validação primeiro dígito
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += numeros[i] * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) primeiroDigito = 0;
        if (numeros[9] != primeiroDigito) return false;

        // Validação segundo dígito
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += numeros[i] * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito > 9) segundoDigito = 0;
        return numeros[10] == segundoDigito;
    }

    public static boolean validarTelefone(String telefone) {
        return telefone != null && PADRAO_TELEFONE.matcher(telefone).matches();
    }

    public static boolean validarNome(String nome) {
        return nome != null && PADRAO_NOME.matcher(nome).matches();
    }

    public static boolean validarQuantidadeEspecialidades(int quantidade) {
        return quantidade > 0 && quantidade <= MAX_ESPECIALIDADES;
    }
}