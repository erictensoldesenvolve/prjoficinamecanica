package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TipoDespezas {
    private int idDesp;
    private String nomeDesp;

    public int getIdDesp() {
        return idDesp;
    }

    public void setIdDesp(int idDesp) {
        this.idDesp = idDesp;
    }

    public String getNomeDesp() {
        return nomeDesp;
    }

    public void setNomeDesp(String nomeDesp) {
        this.nomeDesp = nomeDesp;
    }
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    //    CRIA TABELA NO BANCO
    public void criarTabela() {       
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela no banco atual
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                             "WHERE TABLE_SCHEMA = 'athos-x' AND TABLE_NAME = 'TBLTIPODESPEZAS'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLTIPODESPEZAS (" +
                        "IDDESP int PRIMARY KEY AUTO_INCREMENT," +
                        "NOMEDESP VARCHAR(255) "+                                                                  
                        ")";
                stmt.executeUpdate(sqlCria);  
            }
            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        }
        
    }
    
    public ArrayList<TipoDespezas> ordenar(){
       ArrayList<TipoDespezas> lista = new ArrayList();
       String sql = "SELECT * FROM TBLTIPODESPEZAS ORDER BY NOMEDESP ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                TipoDespezas tipo = new TipoDespezas();
                tipo.setIdDesp(rs.getInt("IDDESP"));
                tipo.setNomeDesp(rs.getString("NOMEDESP").toUpperCase());
                lista.add(tipo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao ordenar despezas: "+e.getMessage());
        }
       return lista;
    }
    
    public ArrayList<TipoDespezas> pesquisar(String nomeDesp){
       ArrayList<TipoDespezas> lista = new ArrayList();
       String sql = "SELECT * FROM TBLTIPODESPEZAS WHERE NOMEDESP LIKE ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomeDesp.toUpperCase()+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                TipoDespezas tipo = new TipoDespezas();
                tipo.setIdDesp(rs.getInt("IDDESP"));
                tipo.setNomeDesp(rs.getString("NOMEDESP").toUpperCase());
                lista.add(tipo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao ordenar despezas: "+e.getMessage());
        }
       return lista;
    }
    
    public int cadastrar(String nomeDesp){
        int cadastrado = 0;
        String sql = "INSERT INTO TBLTIPODESPEZAS (NOMEDESP) VALUES(?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomeDesp.toUpperCase());
            cadastrado = pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar despezas: "+e.getMessage());
        }
        return cadastrado;
    }
    
    public int alterar(int idDesp, String nomeDesp){
        int alterado = 0;
        String sql = "UPDATE TBLTIPODESPEZAS SET NOMEDESP = ? WHERE IDDESP = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomeDesp.toUpperCase());
            pst.setInt(2, idDesp);
            alterado = pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar despezas: "+e.getMessage());
        }
        return alterado;
    }
    
    public int excluir(int idDesp){
        int excluido = 0;
        String sql = "DELETE FROM TBLTIPODESPEZAS WHERE IDDESP = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, idDesp);
            excluido = pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir despeza: "+e.getMessage());
        }
        return excluido;
    }
    
    public void selecionarNome(int idDesp){
        String sql = "SELECT * FROM TBLTIPODESPEZAS WHERE IDDESP = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, idDesp);
            rs = pst.executeQuery();
            if(rs.next()){
                setNomeDesp(rs.getString("NOMEDESP").toUpperCase());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO SELECIONAR DESPEZA: "+e.getMessage());
        }
        
    }
    
    public void selecionarId(String nomeDesp){
        String sql = "SELECT * FROM TBLTIPODESPEZAS WHERE NOMEDESP LIKE ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomeDesp.toUpperCase()+"%");
            rs = pst.executeQuery();
            if(rs.next()){
                setIdDesp(rs.getInt("IDDESP"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO SELECIONAR DESPEZA: "+e.getMessage());
        }
        
    }
}
