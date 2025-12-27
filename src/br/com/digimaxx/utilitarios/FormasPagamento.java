/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author eric
 */
public class FormasPagamento {
    private int fId;
    private String fNome;

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getfNome() {
        return fNome;
    }

    public void setfNome(String fNome) {
        this.fNome = fNome;
    }
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public boolean criaTabela() {
        boolean retorno = false;
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLFORMASPAGAMENTO'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
               String sqlCria = 
                        "CREATE TABLE IF NOT EXISTS TBLFORMASPAGAMENTO ("+
                        "FID INT(11) AUTO_INCREMENT PRIMARY KEY,"+
                        "FNOME varchar(255) NOT NULL "+
                        ")";                
                stmt.executeUpdate(sqlCria); 
                stmt.close();
                retorno = true;
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());         
        }
       return retorno; 
    }
    
    public boolean buscaFormaPagamento(){
        boolean retorno = false;
        String sql = "SELECT * FROM TBLFORMASPAGAMENTO ORDER BY FNOME";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                retorno = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Formas de Pagamento."+e.getMessage());
        }
        return retorno;
    }
    
    public void insereDados(){
        String sql = "INSERT INTO TBLFORMASPAGAMENTO (FNOME) values "
                        + "('DINHEIRO'), " +
                          "('CARTÃO'), " +
                          "('CREDIÁRIO'), " +
                          "('PIX'), " +
                          "('CHEQUE'), " +
                          "('CHEQUE_PRE'),"
                        + "('ORÇAMENTO') ";
        try {
            conexao = ConexaoBanco.conectar();   
            pst = conexao.prepareStatement(sql);
            int adicionado = pst.executeUpdate();
            if(adicionado > 0 ){pst.close();}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        
                
    }
    
    public boolean inserir(String fNome){
        boolean retorno = false;
        String sql = "INSERT INTO TBLFORMASPAGAMENTO (FNOME) VALUES (?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, fNome);
            int inserir = pst.executeUpdate();
            if(inserir > 0 ){pst.close(); retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar item na tabela: " + e.getMessage());
        }
        return retorno;
    }
    
    public boolean editar(int fId, String fNome){
        boolean retorno = false;
        String sql = "UPDATE TBLFORMASPAGAMENTO SET FNOME = ? WHERE FID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, fNome);
            pst.setInt(2, fId);
            int editar = pst.executeUpdate();
            if(editar > 0){pst.close(); retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar item na tabela: " + e.getMessage());
        }
        return retorno;
    }
    
    public boolean excluir(int fId){
       boolean retorno = false;
       String sql = "DELETE FROM TBLFORMASPAGAMENTO WHERE FID = ?";
        try {
           conexao = ConexaoBanco.conectar();
           pst = conexao.prepareStatement(sql);
           pst.setInt(1, fId);
           int excluir = pst.executeUpdate();
           if(excluir > 0 ){pst.close(); retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir item na tabela: " + e.getMessage());
        }
       return retorno;
    }
    
    public ArrayList<FormasPagamento> listar(){
        ArrayList<FormasPagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM TBLFORMASPAGAMENTO ORDER BY FID ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);             
            rs = pst.executeQuery();
            while(rs.next()){
                FormasPagamento formasPagamento = new FormasPagamento();
                formasPagamento.setfId(rs.getInt("FID"));
                formasPagamento.setfNome(rs.getString("FNOME").toUpperCase());
                lista.add(formasPagamento);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public void localizaPagamento(String nome){
        String sql = "SELECT * FROM TBLFORMASPAGAMENTO WHERE FNOME = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nome.toUpperCase());
            rs = pst.executeQuery();
            if(rs.next()){
                setfId(rs.getInt("FID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void localizaId(int fId){
        String sql = "SELECT * FROM TBLFORMASPAGAMENTO WHERE FID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, fId);
            rs = pst.executeQuery();
            if(rs.next()){
                setfNome(rs.getString("FNOME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}
