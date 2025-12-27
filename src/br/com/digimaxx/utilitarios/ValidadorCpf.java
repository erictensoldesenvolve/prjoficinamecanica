/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.digimaxx.utilitarios;

/**
 *
 * @author eric
 */
public class ValidadorCpf {

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;

        // Remove pontos e traços
        cpf = cpf.replaceAll("[^\\d]", "");

        // CPF tem que ter 11 dígitos
        if (cpf.length() != 11) return false;

        // Elimina CPFs com todos os dígitos iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int soma1 = 0, soma2 = 0;
            for (int i = 0; i < 9; i++) {
                int digito = Character.getNumericValue(cpf.charAt(i));
                soma1 += digito * (10 - i);
                soma2 += digito * (11 - i);
            }

            // Primeiro dígito verificador
            int primeiroDigito = calcularDigito(soma1);
            soma2 += primeiroDigito * 2;

            // Segundo dígito verificador
            int segundoDigito = calcularDigito(soma2);

            // Compara com os dígitos fornecidos
            return cpf.charAt(9) == (char) (primeiroDigito + '0') &&
                   cpf.charAt(10) == (char) (segundoDigito + '0');

        } catch (Exception e) {
            return false;
        }
    }

    private static int calcularDigito(int soma) {
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

}
