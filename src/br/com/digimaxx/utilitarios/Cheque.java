/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.*;

/**
 *
 * @author eric
 */
public class Cheque {
    private int chequeId;
    private int vId;
    private Double chequeValor;
    private String chequeCmc;
    private int chequeNumero;
    private int chequeBanco;
    private int chequeAgencia;
    private String chequeConta;
    private Date chequeData;
    private Date chequeDataVencimento;
    private int chequePagId;

    public int getChequeId() {
        return chequeId;
    }

    public void setChequeId(int chequeId) {
        this.chequeId = chequeId;
    }

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public Double getChequeValor() {
        return chequeValor;
    }

    public void setChequeValor(Double chequeValor) {
        this.chequeValor = chequeValor;
    }

    public String getChequeCmc() {
        return chequeCmc;
    }

    public void setChequeCmc(String chequeCmc) {
        this.chequeCmc = chequeCmc;
    }

    public int getChequeNumero() {
        return chequeNumero;
    }

    public void setChequeNumero(int chequeNumero) {
        this.chequeNumero = chequeNumero;
    }

    public int getChequeBanco() {
        return chequeBanco;
    }

    public void setChequeBanco(int chequeBanco) {
        this.chequeBanco = chequeBanco;
    }

    public int getChequeAgencia() {
        return chequeAgencia;
    }

    public void setChequeAgencia(int chequeAgencia) {
        this.chequeAgencia = chequeAgencia;
    }

    public String getChequeConta() {
        return chequeConta;
    }

    public void setChequeConta(String chequeConta) {
        this.chequeConta = chequeConta;
    }

    public Date getChequeData() {
        return chequeData;
    }

    public void setChequeData(Date chequeData) {
        this.chequeData = chequeData;
    }

    public Date getChequeDataVencimento() {
        return chequeDataVencimento;
    }

    public void setChequeDataVencimento(Date chequeDataVencimento) {
        this.chequeDataVencimento = chequeDataVencimento;
    }

    public int getChequePagId() {
        return chequePagId;
    }

    public void setChequePagId(int chequePagId) {
        this.chequePagId = chequePagId;
    }
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
     public boolean criarTabela() {
       boolean retorno = false;
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLCHEQUE'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);
            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLCHEQUE (" +
                        "CHEQUEID INT PRIMARY KEY AUTO_INCREMENT," +
                        "VID INT NOT NULL," +
                        "CHEQUEVALOR DECIMAL(20,2) NOT NULL," + 
                        "CHEQUECMC VARCHAR (255), " +
                        "CHEQUENUMERO INT (11), " +                        
                        "CHEQUEBANCO INT (11), " +                       
                        "CHEQUEAGENCIA INT(11), " +                       
                        "CHEQUECONTA VARCHAR(255), " +                       
                        "CHEQUEDATA Date NOT NULL,"
                        + "CHEQUEDATAVENCIMENTO DATE " +                       
                        ")";
                retorno = true;
                stmt.executeUpdate(sqlCria);  
               
            }

            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
      return retorno;  
    }
     
     public boolean cadastrar(int vId, Double chequeValor, String chequeCmc, int chequeNumero, int chequeBanco, int chequeAgencia, String chequeConta, String dataVencimento, String data, int pagId){
         boolean retorno = false;
         String sql = "INSERT INTO TBLCHEQUE (VID, CHEQUEVALOR, CHEQUECMC, CHEQUENUMERO, CHEQUEBANCO, CHEQUEAGENCIA, CHEQUECONTA, CHEQUEDATAVENCIMENTO, CHEQUEDATA, CHEQUEPAGID) "
                 + "VALUES (?,?,?,?,?,?,?,?,?,?)";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, vId);
             pst.setDouble(2, chequeValor);
             pst.setString(3, chequeCmc);
             pst.setInt(4, chequeNumero);
             pst.setInt(5, chequeBanco);
             pst.setInt(6, chequeAgencia);
             pst.setString(7, chequeConta);
             pst.setString(8, dataVencimento);
             pst.setString(9, data);
             pst.setInt(10, pagId);
             int adicionado = pst.executeUpdate();
             if(adicionado > 0){ retorno = true; }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return retorno;
     }
     
     public boolean pesquisaCheque(int pagId){
         boolean retorno = false;
         String sql = "SELECT * FROM TBLCHEQUE WHERE CHEQUEPAGID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, pagId);
             rs = pst.executeQuery();
             if(rs.next()){
                 setChequeAgencia(rs.getInt("CHEQUEAGENCIA"));
                 setChequeBanco(rs.getInt("CHEQUEBANCO"));
                 setChequeCmc(rs.getString("CHEQUECMC"));
                 setChequeConta(rs.getString("CHEQUECONTA"));
                 setChequeData(rs.getDate("CHEQUEDATA"));
                 setChequeId(rs.getInt("CHEQUEID"));
                 setChequeNumero(rs.getInt("CHEQUENUMERO"));
                 setChequeValor(rs.getDouble("CHEQUEVALOR"));
                 setvId(rs.getInt("VID"));
                 setChequeDataVencimento(rs.getDate("CHEQUEDATAVENCIMENTO"));
                 setChequePagId(rs.getInt("CHEQUEPAGID"));
                 retorno = true;
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Erro ao buscar cheque: "+e.getMessage());
         }
         return retorno;
     }
     
     public boolean alterar(int vId, double chequeValor, String chequeCmc, int chequeNumero, int chequeBanco, 
             int chequeAgencia, String chequeConta, String chequeData, String chequeDataVencimento, int chequePagId, int chequeId){
         boolean retorno = false;
         String sql = "UPDATE TBLCHEQUE SET VID = ?, CHEQUEVALOR = ?, CHEQUECMC = ?, CHEQUENUMERO = ?, "
                 + "CHEQUEBANCO = ?, CHEQUEAGENCIA = ?, CHEQUECONTA = ?, CHEQUEDATA = ?, CHEQUEDATAVENCIMENTO = ?, CHEQUEPAGID = ? "
                 + "WHERE CHEQUEID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, vId);
             pst.setDouble(2, chequeValor);
             pst.setString(3, chequeCmc);
             pst.setInt(4, chequeNumero);
             pst.setInt(5, chequeBanco);
             pst.setInt(6, chequeAgencia);
             pst.setString(7, chequeConta);
             pst.setString(8, chequeData);
             pst.setString(9, chequeDataVencimento);
             pst.setInt(10, chequePagId);
             pst.setInt(11, chequeId);
             int alterado = pst.executeUpdate();
             if(alterado > 0){ retorno = true; }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Erro ao alterar Cheque!"+e.getMessage());
         }
         return retorno;
     }
     
     public boolean excluir(int chequeId){
         boolean retorno = false;
         String sql = "DELETE FROM TBLCHEQUE WHERE CHEQUEID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, chequeId);
             int excluido = pst.executeUpdate();
             if(excluido > 0 ){ retorno = true; }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Erro ao excluir Cheque!"+e.getMessage());
         }
         return retorno;
     }
    
}
