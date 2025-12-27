/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author eric
 */
public class Crediario {
    private int crediarioId;
    private int vId;
    private int cliId; 
    private Date dataVencimento;
    private Date dataPagamento;
    private String pagamentoEfetuado;
    private Double vTotal;
    private Date vData;

    public int getCrediarioId() {
        return crediarioId;
    }

    public void setCrediarioId(int crediarioId) {
        this.crediarioId = crediarioId;
    }

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public int getCliId() {
        return cliId;
    }

    public void setCliId(int cliId) {
        this.cliId = cliId;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getPagamentoEfetuado() {
        return pagamentoEfetuado;
    }

    public void setPagamentoEfetuado(String pagamentoEfetuado) {
        this.pagamentoEfetuado = pagamentoEfetuado;
    }

    public Double getvTotal() {
        return vTotal;
    }

    public void setvTotal(Double vTotal) {
        this.vTotal = vTotal;
    }

    public Date getvData() {
        return vData;
    }

    public void setvData(Date vData) {
        this.vData = vData;
    }
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    //    VERIFICA SE EXISTE A TABELA NO BANCO DE DADOS
    public void verificaTabela() {       
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLCREDIARIO'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLCREDIARIO (" +
                        "CREDIARIOID INT PRIMARY KEY AUTO_INCREMENT," +
                        "VID INT NOT NULL, "+
                        "CLIID INT NOT NULL," +
                        "DATAVENCIMENTO DATE NOT NULL," +
                        "DATAPAGAMENTO DATE," +
                        "PAGAMENTOEFETUADO VARCHAR(2) " +                        
                        ")";
                stmt.executeUpdate(sqlCria);  
            }
            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        }
        
    }
    
    public void inserir(int vId, int cliId, String dataVencimento, String dataPagamento, String pagamentoEfetuado){
        String sql = "INSERT INTO TBLCREDIARIO (vId, cliId, dataVencimento, dataPagamento, pagamentoEfetuado) "
                + "VALUES(?, ?, ?, ?, ?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            pst.setInt(2, cliId);
            pst.setString(3, dataVencimento);
            pst.setString(4, dataPagamento);
            pst.setString(5, pagamentoEfetuado);
            pst.executeUpdate();            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: " + e.getMessage());
        }
    }
    
    public ArrayList<Crediario> localizar(int cliId){
        ArrayList<Crediario> lista = new ArrayList<>();
        String sql = "SELECT C.*, V.VTOTAL, VDATA FROM TBLCREDIARIO C "
                + "INNER JOIN TBLVENDAS V "
                + "ON V.VID = C.VID "
                + "WHERE C.CLIID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, cliId);
            rs = pst.executeQuery();
            
            while(rs.next()){
               Crediario crediario = new Crediario();
               crediario.setCrediarioId(rs.getInt("CREDIARIOID"));
               crediario.setvId(rs.getInt("VID"));
               crediario.setCliId(rs.getInt("CLIID"));      
               crediario.setDataVencimento(rs.getDate("DATAVENCIMENTO"));
               crediario.setDataPagamento(rs.getDate("DATAPAGAMENTO"));
               crediario.setPagamentoEfetuado(rs.getString("PAGAMENTOEFETUADO"));
               crediario.setvTotal(rs.getDouble("VTOTAL"));
               crediario.setvData(rs.getDate("vData"));
               lista.add(crediario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public Double somaTotalDebito(int cliId){
        Double total = 0.00;
        String sql = "SELECT C.CLIID, C.PAGAMENTOEFETUADO, V.VTOTAL FROM TBLCREDIARIO C "
                + "INNER JOIN TBLVENDAS V ON V.VID = C.VID "
                + "WHERE C.CLIID = ? AND C.PAGAMENTOEFETUADO = 'N'";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, cliId);
            rs = pst.executeQuery();
            while(rs.next()){
                total += rs.getDouble("VTOTAL");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return total;
    }
    
    public void localizarVenda(int vId){
        String sql = "SELECT * FROM TBLCREDIARIO WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            rs = pst.executeQuery();
            if(rs.next()){
               setvId(rs.getInt("VID")); 
               setPagamentoEfetuado(rs.getString("PAGAMENTOEFETUADO"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }
    
    public boolean pagar(String dataPagamento, int vId){
        boolean retorno = false;
        String sql = "UPDATE TBLCREDIARIO SET DATAPAGAMENTO = ?, PAGAMENTOEFETUADO = ? "
                + "WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataPagamento);
            pst.setString(2, "S");
            pst.setInt(3, vId);
            int atualizado = pst.executeUpdate();
            if(atualizado > 0){retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public boolean verificaPagamento(int vId){
        boolean retorno = false;
        String sql = "SELECT * FROM TBLCREDIARIO WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            rs = pst.executeQuery();
            if(rs.next()){
                if(rs.getString("PAGAMENTOEFETUADO").equals("S")){
                    retorno = true;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public void excluir(int vId){
       String sql = "DELETE FROM TBLCREDIARIO WHERE VID = ?"; 
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO EXCLUIR CREDIÁRIO: "+e.getMessage());
        }
    }
}
