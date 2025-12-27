package br.com.digimaxx.utilitarios;

public class ValidadorCnpj {

    public static boolean isCnpjValido(String cnpj) {
        if (cnpj == null) return false;

        // Remove pontos, traços e barras
        cnpj = cnpj.replaceAll("[^\\d]", "");

        // CNPJ precisa ter 14 dígitos
        if (cnpj.length() != 14) return false;

        // Evita CNPJs com todos os dígitos iguais (ex: 11111111111111)
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int soma = 0;
            int peso = 5;

            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
                peso = (peso == 2) ? 9 : peso - 1;
            }

            int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            soma = 0;
            peso = 6;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso;
                peso = (peso == 2) ? 9 : peso - 1;
            }

            int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            return cnpj.charAt(12) == Character.forDigit(digito1, 10) &&
                   cnpj.charAt(13) == Character.forDigit(digito2, 10);

        } catch (Exception e) {
            return false;
        }
    }
}
