/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author eric
 */
public class VendasCartao {
    private int vId;
    private int cartaoId;
    private int autorizacao;
    private int nsu;

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public int getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(int cartaoId) {
        this.cartaoId = cartaoId;
    }

    public int getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(int autorizacao) {
        this.autorizacao = autorizacao;
    }

    public int getNsu() {
        return nsu;
    }

    public void setNsu(int nsu) {
        this.nsu = nsu;
    }
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public void criarTabela() {       
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();
            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLCARTOES'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);
            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLCARTOES (" +
                        "CARTAOID INT(11) PRIMARY KEY AUTO_INCREMENT," +
                        "VID INT(11) NOT NULL," +
                        "AUTORIZACAO VARCHAR(255) NOT NULL," +
                        "NSU INT(11) NOT NULL " +                        
                        ")";
                stmt.executeUpdate(sqlCria);  
            }
            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        }        
    }
    
    public void inserir(int vId, String autorizacao, int nsu){
        String sql = "INSERT INTO TBLCARTOES (VID, AUTORIZACAO, NSU) VALUES (?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            pst.setString(2, autorizacao);
            pst.setInt(3, nsu);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}
