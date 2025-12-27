/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author eric
 */
public class ItensVenda {
    private int itemid;
    private int produto_id;
    private int quantidade;
    private Double desconto;
    private Double prod_prvenda;
    private int vId;
    private Double prod_prvendido;
    private String produto;
    private Double totalItens;
    
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getProd_prvenda() {
        return prod_prvenda;
    }

    public void setProd_prvenda(Double prod_prvenda) {
        this.prod_prvenda = prod_prvenda;
    }

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public Double getProd_prvendido() {
        return prod_prvendido;
    }

    public void setProd_prvendido(Double prod_prvendido) {
        this.prod_prvendido = prod_prvendido;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }  

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public Double getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(Double totalItens) {
        this.totalItens = totalItens;
    }

    public void excluiItensVendaZero(int vId){
        String sql = "DELETE FROM TBLITENSVENDA WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            pst.executeUpdate();            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }
    
    public void buscaProduto(int itemVenda){
        String sql = "SELECT * FROM TBLITENSVENDA WHERE ITEMID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, itemVenda);
            rs = pst.executeQuery();
            if(rs.next()){
                setProduto_id(rs.getInt("PRODUTO_ID"));
                setQuantidade(rs.getInt("QUANTIDADE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }
    
    public boolean excluirItem(int itemId){
        boolean retorno = false;
       String sql = "DELETE FROM TBLITENSVENDA WHERE ITEMID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, itemId);
            int excluido = pst.executeUpdate();
            if(excluido > 0 ){retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public void criarTabelaBanco() {       
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();
            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLITENSVENDA'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);
            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLITENSVENDA (" +
                        "ITEMID INT PRIMARY KEY AUTO_INCREMENT, " +
                        "PRODUTO_ID INT NOT NULL, " +
                        "QUANTIDADE INT NOT NULL, " +
                        "DESCONTO DECIMAL(20,2), " +
                        "PROD_PRVENDA DECIMAL(20,2) NOT NULL, " +
                        "VID INT NOT NULL, " +
                        "PROD_PRVENDIDO DECIMAL(20,2) NOT NULL " +                        
                        ")";
                stmt.executeUpdate(sqlCria);                 
            }
            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        }
        
    }
    
    public boolean insereItem(int produto_id, int quantidade, Double desconto, Double prod_prvenda, int vId, Double prod_prvendido){
        boolean retorno = false;
        String sql = "INSERT INTO TBLITENSVENDA(PRODUTO_ID, QUANTIDADE, DESCONTO, PROD_PRVENDA, VID, PROD_PRVENDIDO) "
                + "values(?,?,?,?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, produto_id);
            pst.setInt(2, quantidade);
            pst.setDouble(3, desconto);
            pst.setDouble(4, prod_prvenda);
            pst.setInt(5, vId);
            pst.setDouble(6, prod_prvendido);
           
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return retorno;
    } 
    
    public ArrayList<ItensVenda> localizar(int vId)
    {
        ArrayList<ItensVenda> lista = new ArrayList<>();
        String sql = "SELECT IT.*, P.PRODUTO FROM TBLITENSVENDA IT "
                + "INNER JOIN TBLPRODUTOS P ON P.PRODUTO_ID = IT.PRODUTO_ID "
                + "INNER JOIN TBLVENDAS V ON V.VID = IT.VID "
                + "WHERE IT.VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            rs = pst.executeQuery();
            
            while(rs.next()){
               ItensVenda itensVenda = new ItensVenda();
               itensVenda.setItemid(rs.getInt("ITEMID"));
               itensVenda.setProduto(rs.getString("PRODUTO"));
               itensVenda.setQuantidade(rs.getInt("QUANTIDADE"));
               itensVenda.setProd_prvendido(rs.getDouble("PROD_PRVENDIDO"));
               itensVenda.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
               itensVenda.setTotalItens(rs.getInt("QUANTIDADE") * rs.getDouble("PROD_PRVENDIDO"));
               lista.add(itensVenda);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<ItensVenda> calculaItens(int vid){
        ArrayList<ItensVenda> lista = new ArrayList<>();
        String sql = "SELECT * FROM TBLITENSVENDA WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vid);
            rs = pst.executeQuery();
            while(rs.next()){
               ItensVenda itensVenda = new ItensVenda(); 
               itensVenda.setItemid(rs.getInt("ITEMID"));
               itensVenda.setProduto_id(rs.getInt("PRODUTO_ID"));
               itensVenda.setQuantidade(rs.getInt("QUANTIDADE"));
               itensVenda.setProd_prvendido(rs.getDouble("PROD_PRVENDIDO"));
               itensVenda.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
               lista.add(itensVenda);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public ArrayList<ItensVenda> selecionaItem(int vId){
        ArrayList<ItensVenda> lista = new ArrayList();
        String sql = "SELECT * FROM TBLITENSVENDA WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            rs = pst.executeQuery();
            while(rs.next()){
                ItensVenda item = new ItensVenda();
                item.setItemid(rs.getInt("ITEMID"));
                item.setProduto_id(rs.getInt("PRODUTO_ID"));
                item.setQuantidade(rs.getInt("QUANTIDADE"));
                lista.add(item);
            }
        } catch (Exception e) {
        }
        return lista;
    }

    //CRIA UM LAÇO PARA VERIFICAR SE EXISTE ITENS DA VENDA
    public boolean procuraItens(int vId){
        String sql = "SELECT * FROM TBLITENSVENDA WHERE VID = ?";
        boolean retorno = false;
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            rs = pst.executeQuery();
            if(rs.next()){
                setItemid(rs.getInt("ITEMID"));
                setProduto_id(rs.getInt("PRODUTO_ID"));
                setQuantidade(rs.getInt("QUANTIDADE"));
                retorno = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
