/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author eric
 */
public class ItensNotaFiscal {
    private int itenId;
    private int notaId;
    private int produto_id;
    private Double quantidade;
    private Double desconto;
    private String cfop;
    private Double valorUnitario;
    private Double subtotal;
    private int quantidadeItens;
    private Double totalItens;

    public int getItenId() {
        return itenId;
    }

    public void setItenId(int itenId) {
        this.itenId = itenId;
    }

    public int getNotaId() {
        return notaId;
    }

    public void setNotaId(int notaId) {
        this.notaId = notaId;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }

    public void setQuantidadeItens(int quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public Double getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(Double totalItens) {
        this.totalItens = totalItens;
    }
    
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    //    VERIFICA SE EXISTE A TABELA NO BANCO DE DADOS
    public void criaTabela() {       
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLITENSNOTAFISCAL'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLITENSNOTAFISCAL (" +
                        "ITEMID int(11) AUTO_INCREMENT PRIMARY KEY," +
                        "NOTAID INT NOT NULL, "+
                        "PRODUTO_ID INT NOT NULL," +
                        "QUANTIDADE DECIMAL(15, 2) NOT NULL, " +
                        "DESCONTO DECIMAL(15, 2) NOT NULL, " +                      
                        "CFOP VARCHAR(255), " +                      
                        "VALORUNITARIO DECIMAL(15, 2) NOT NULL, " +
                        "SUBTOTAL DECIMAL(15,2) NOT NULL " +                       
                        ")";                
                stmt.executeUpdate(sqlCria);  
            }
            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        } 
    }
    
    public boolean cadastrar(int notaId, int produto_id, Double quantidade, Double desconto, String cfop, Double valorUnitario, Double subtotal){
        boolean retorno = false;
        String sql = "INSERT INTO TBLITENSNOTAFISCAL (NOTAID, PRODUTO_ID, QUANTIDADE, DESCONTO, CFOP, VALORUNITARIO, SUBTOTAL) "
                + "VALUES (?,?,?,?,?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, notaId);
            pst.setInt(2, produto_id);
            pst.setDouble(3, quantidade);
            pst.setDouble(4, desconto);
            pst.setString(5, cfop);
            pst.setDouble(6, valorUnitario);
            pst.setDouble(7, subtotal);
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){ retorno = true; }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO INSERIR ITEM NA TABELA: " + e.getMessage());
        }
        return retorno;
    }
    
    public ArrayList<ItensNotaFiscal> localizar(int notaId){
        ArrayList<ItensNotaFiscal> lista = new ArrayList<>();
        String sql = "SELECT * FROM TBLITENSNOTAFISCAL WHERE NOTAID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, notaId);
            rs = pst.executeQuery();
            
            while(rs.next()){
               ItensNotaFiscal itemNota = new ItensNotaFiscal();
               itemNota.setItenId(rs.getInt("ITEMID"));
               itemNota.setProduto_id(rs.getInt("PRODUTO_ID"));
               itemNota.setQuantidade(rs.getDouble("QUANTIDADE"));
               itemNota.setDesconto(rs.getDouble("DESCONTO"));      
               itemNota.setCfop(rs.getString("CFOP"));
               itemNota.setValorUnitario(rs.getDouble("VALORUNITARIO"));
               itemNota.setSubtotal(rs.getDouble("SUBTOTAL"));               
               lista.add(itemNota);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public boolean buscaItemNota(int notaId){
        boolean retorno = false;
        String sql = "SELECT * FROM TBLITENSNOTAFISCAL WHERE NOTAID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, notaId);
            rs = pst.executeQuery();
            if(rs.next()){
               retorno = true;
               setItenId(rs.getInt("ITEMID"));
               setProduto_id(rs.getInt("PRODUTO_ID"));
               setQuantidade(rs.getDouble("QUANTIDADE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO PROCURAR ITEM NA NOTA FISCAL: " + e.getMessage());
        }
        return retorno;
    }
    
    public void excluirItemNota(int itenId){
        String sql = "DELETE FROM TBLITENSNOTAFISCAL WHERE ITEMID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, itenId);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO EXCLUIR ITEM NA NOTA FISCAL: " + e.getMessage());
        }
    }
    
    public void contarItensNota(int notaId){
       String sql = "SELECT * FROM TBLITENSNOTAFISCAL WHERE NOTAID = ?"; 
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, notaId);
            rs = pst.executeQuery();
            int qtd = 0;
            Double total = 0.0;
            while(rs.next()){
                qtd++;
                setQuantidadeItens(qtd);
                total += rs.getDouble("SUBTOTAL");
                setTotalItens(total);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO CONTAR ITENS DA NOTA FISCAL: " + e.getMessage());
        }
    }
    
    //METODO PARA BUSCA DE CÓDIGO E QUANTIDADE DE ITEM NA NOTA PARA ALTERAR O ESTOQUE    
    public void pesquisaItem(int itenId){
        String sql = "SELECT * FROM TBLITENSNOTAFISCAL WHERE ITEMID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, itenId);
            rs = pst.executeQuery();
            if(rs.next()){
                setProduto_id(rs.getInt("PRODUTO_ID"));
                setQuantidade(rs.getDouble("QUANTIDADE")); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO PESQUISAR ITEM DA NOTA FISCAL: " + e.getMessage());
        }
    }
}