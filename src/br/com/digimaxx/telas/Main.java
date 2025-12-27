/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.digimaxx.telas;

import br.com.digimaxx.utilitarios.UsuarioSistema;
import java.sql.SQLException;


public class Main {
        
    public static void main(String[] args) throws SQLException {
        
        UsuarioSistema usuario = new UsuarioSistema();
        
        if (usuario.lerConfig()) {
            // Config já existe: vai direto pro sistema
            Principal principal = new Principal();
            principal.setVisible(true);
        } else {
            // Config não existe: mostra a tela inicial pra configurar
            VerificaCliente cliente = new VerificaCliente();
            cliente.setVisible(true);  
        }
       
    }
}
