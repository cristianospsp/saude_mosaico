package com.saudemosaico.agendamento.security;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class SanitizadorUtil {
    private static final Pattern SCRIPT_PATTERN = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern SPECIAL_CHARS = Pattern.compile("[<>&'\"]");
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern RG_PATTERN = Pattern.compile("^[0-9]{7,9}$");
    private static final int MAX_FIELD_LENGTH = 255;

    public String sanitizarInput(String input) {
        if (input == null) {
            return null;
        }

        // Limitar tamanho do campo
        if (input.length() > MAX_FIELD_LENGTH) {
            input = input.substring(0, MAX_FIELD_LENGTH);
        }

        // Remover scripts
        input = SCRIPT_PATTERN.matcher(input).replaceAll("");

        // Escapar HTML e caracteres especiais
        input = StringEscapeUtils.escapeHtml4(input);
        input = SPECIAL_CHARS.matcher(input).replaceAll("");

        return input.trim();
    }

    public boolean validarCPF(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        // Validação do algoritmo do CPF
        int[] numeros = cpf.chars().map(Character::getNumericValue).toArray();

        // Primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += numeros[i] * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito > 9) primeiroDigito = 0;
        if (numeros[9] != primeiroDigito) return false;

        // Segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += numeros[i] * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito > 9) segundoDigito = 0;
        return numeros[10] == segundoDigito;
    }

    public boolean validarRG(String rg) {
        return rg != null && RG_PATTERN.matcher(rg).matches();
    }

    public String mascararDadosSensiveis(String dado) {
        if (dado == null || dado.length() < 4) {
            return "****";
        }
        int visibleChars = 4;
        int length = dado.length();
        return "*".repeat(length - visibleChars) + dado.substring(length - visibleChars);
    }
}