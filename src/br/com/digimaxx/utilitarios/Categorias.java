
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Categorias {
    private int catId;
    private String catNome;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatNome() {
        return catNome;
    }

    public void setCatNome(String catNome) {
        this.catNome = catNome;
    }
    
    public void verificarECriarTabela() {
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLCATEGORIAS'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLCATEGORIAS (" +
                        "CATID INT AUTO_INCREMENT PRIMARY KEY," +
                        "CATNOME VARCHAR(255) NOT NULL "  +
                        ")";
                stmt.executeUpdate(sqlCria);    
            }

            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public boolean excluir(int catId){
        boolean retorno = false;
        String sql = "DELETE FROM TBLCATEGORIAS WHERE CATID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);            
            pst.setInt(1, catId);
            int atualiza = pst.executeUpdate();
            if(atualiza > 0 ){retorno = true; }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public boolean editar(String catNome, int catId){
       boolean retorno = false;
       String sql = "UPDATE TBLCATEGORIAS SET CATNOME = ? WHERE CATID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, catNome);
            pst.setInt(2, catId);
            int atualiza = pst.executeUpdate();
            if(atualiza > 0 ){retorno = true; }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }      
       return retorno;       
    }
    
    public boolean cadastrar(String catNome){
        boolean retorno = false;
        String sql = "INSERT INTO TBLCATEGORIAS (CATNOME) VALUES (?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, catNome);
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
               retorno = true; 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public void pesquisaNome(String catNome){
        String sql = "SELECT * FROM TBLCATEGORIAS WHERE CATNOME LIKE ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, catNome+"%");
            rs = pst.executeQuery();
            if(rs.next()){
                setCatId(rs.getInt("CATID"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    //LOCALIZA O NOME DA CATEGORIA PELO CÓDIGO
    public void selecionaNome(int codigo){
        String sql = "SELECT * FROM TBLCATEGORIAS WHERE CATID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, codigo);
            rs = pst.executeQuery();            
            if(rs.next()){
                this.setCatNome(rs.getString("CATNOME"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public ArrayList<Categorias> ordenaNome(){
        ArrayList<Categorias> lista = new ArrayList<>();
        String sql = "SELECT * FROM TBLCATEGORIAS ORDER BY CATNOME ASC";
    
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);            
            rs = pst.executeQuery();
        
            while (rs.next()) {
                Categorias categoria = new Categorias();
                categoria.setCatId(rs.getInt("CATID"));
                categoria.setCatNome(rs.getString("CATNOME"));
                lista.add(categoria);
            }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + e.getMessage());
    }
    return lista;
}
}
