package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Usuarios {
    private int usuId;
    private String usuNome;
    private String usuEmail;
    private String usuSenha;
    private int usuLevel;
    private String usuAtivo;

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
                
    public int getUsuId() {
        return usuId;
    }

    public void setUsuId(int usuId) {
        this.usuId = usuId;
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }

    public String getUsuEmail() {
        return usuEmail;
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    public int getUsuLevel() {
        return usuLevel;
    }

    public void setUsuLevel(int usuLevel) {
        this.usuLevel = usuLevel;
    }

    public String getUsuAtivo() {
        return usuAtivo;
    }

    public void setUsuAtivo(String usuAtivo) {
        this.usuAtivo = usuAtivo;
    }
    
    public void verificarECriarTabela() {
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'USUARIOS'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS USUARIOS (" +
                        "USUID INT AUTO_INCREMENT PRIMARY KEY," +
                        "USUNOME VARCHAR(255) NOT NULL," +
                        "USUEMAIL VARCHAR(255)," +
                        "USUSENHA VARCHAR(255) NOT NULL," +
                        "USULEVEL INT," +
                        "USUATIVO VARCHAR(10)" +
                        ")";
                stmt.executeUpdate(sqlCria); 
               
            }

            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        }
    }
    
    public boolean buscaUsuario(){
        boolean retorno = false;
        String sql = "SELECT * FROM USUARIOS ORDER BY USUNOME";
        try {
           conexao = ConexaoBanco.conectar();
           pst = conexao.prepareStatement(sql);
           rs = pst.executeQuery();
           while(rs.next()){
               retorno = true;
           }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Usuário."+e.getMessage());
        }
        return retorno;
    }
    
    public void inserirUsuarioPadrao(){
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();
            String inserir = 
                        "INSERT INTO USUARIOS (USUNOME, USUEMAIL, USUSENHA, USULEVEL, USUATIVO) "
                        + "VALUES ('admin', 'usuariopadrao@eitech.com.br', 'admin', 1, 'S')";
            stmt.executeUpdate(inserir);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir dados na tabela: " + e.getMessage());
        }
    }
    
    //CADASTRAR USUÁRIO NOVO
    public boolean cadastrar(String usuNome, String usuEmail, String usuSenha, int usuLevel, String usuAtivo){
        boolean retorno = false;
        String sql = "INSERT INTO USUARIOS (USUNOME, USUEMAIL, USUSENHA, USULEVEL, USUATIVO) values (?,?,?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usuNome);
            pst.setString(2, usuEmail);
            pst.setString(3, usuSenha);
            pst.setInt(4, usuLevel);
            pst.setString(5, usuAtivo);
            
                int adicionado = pst.executeUpdate();
                if(adicionado > 0){ 
                  retorno = true;  
                }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
    
    //ALTERAR USUÁRIO
    public boolean alterar(String usuNome, String usuEmail, int usuLevel, String usuAtivo, int usuId){
        boolean retorno = false;
        String sql = "UPDATE USUARIOS SET USUNOME = ?, USUEMAIL = ?, USULEVEL = ?, "
                + "USUATIVO = ? WHERE USUID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usuNome.toUpperCase());
            pst.setString(2, usuEmail.toUpperCase());            
            pst.setInt(3, usuLevel);
            pst.setString(4, usuAtivo.toUpperCase());            
            pst.setInt(5, usuId);
            
                int adicionado = pst.executeUpdate();
                if(adicionado > 0){  
                   retorno = true;     
                }
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    //EXCLUIR USUÁRIO
    public boolean excluir(int usuId){
        boolean retorno = false;
        String sql = "DELETE FROM USUARIOS WHERE USUID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, usuId);
            int excluido = pst.executeUpdate();
            if(excluido > 0){ 
                retorno = true;      
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
    
    public boolean logar(String senha){
        boolean retorna = false;
        String sql = "SELECT * FROM USUARIOS WHERE USUSENHA = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, senha);
            rs = pst.executeQuery();
            if(rs.next()){
                setUsuId(rs.getInt("USUID"));
                setUsuNome(rs.getString("USUNOME").toUpperCase());
                setUsuLevel(rs.getInt("USULEVEL"));
                retorna = true;
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return retorna;
    }
    
    public void localizarUsuario(int usuId){
        String sql = "SELECT * FROM USUARIOS WHERE USUID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, usuId);
            rs = pst.executeQuery();
            if(rs.next()){setUsuNome(rs.getString("USUNOME").toUpperCase());}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LOCALIZAR USUÁRIO: "+e.getMessage());
        }
    }
    
    public void localizar(int usuId){
        String sql = "SELECT * FROM USUARIOS WHERE USUID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, usuId);
            rs = pst.executeQuery();
            if(rs.next()){
                setUsuNome(rs.getString("USUNOME").toUpperCase());
                setUsuEmail(rs.getString("USUEMAIL").toUpperCase());
                setUsuAtivo(rs.getString("USUATIVO"));
                setUsuLevel(rs.getInt("USULEVEL"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LOCALIZAR USUÁRIO: "+e.getMessage());
        }
    }
    
    public ArrayList<Usuarios> listar(){
        ArrayList<Usuarios> lista = new ArrayList();
        String sql = "SELECT USUID, USUNOME, USUEMAIL, "
                + "USUATIVO FROM USUARIOS ORDER BY USUNOME ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Usuarios usuario = new Usuarios();
                usuario.setUsuId(rs.getInt("USUID"));
                usuario.setUsuNome(rs.getString("USUNOME").toUpperCase());
                usuario.setUsuEmail(rs.getString("USUEMAIL"));
                usuario.setUsuAtivo(rs.getString("USUATIVO"));
                lista.add(usuario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar Usuários!"+e.getMessage());
        }
        return lista;   
    }
    
    public ArrayList<Usuarios> pesquisar(String usuNome){
        ArrayList<Usuarios> lista = new ArrayList();
        String sql = "SELECT USUID, USUNOME, USUEMAIL, "
                + "USUATIVO FROM USUARIOS "
                + "WHERE USUNOME LIKE ? "
                + "ORDER BY USUNOME ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usuNome.toUpperCase()+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                Usuarios usuario = new Usuarios();
                usuario.setUsuId(rs.getInt("USUID"));
                usuario.setUsuNome(rs.getString("USUNOME").toUpperCase());
                usuario.setUsuEmail(rs.getString("USUEMAIL"));
                usuario.setUsuAtivo(rs.getString("USUATIVO"));
                lista.add(usuario);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar Usuários!"+e.getMessage());
        }
        return lista;   
    }
    
    public int inativar(int usuId){
        int alterado = 0;
        String sql = "UPDATE USUARIOS SET USUATIVO = ? "
                + "WHERE USUID = ? ";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "N");
            pst.setInt(2, usuId);
            alterado = pst.executeUpdate();           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inativar o usuário!"+e.getMessage());
        }
       return alterado;
    }
    
    public int ativar(int usuId){
        int alterado = 0;
        String sql = "UPDATE USUARIOS SET USUATIVO = ? "
                + "WHERE USUID = ? ";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "S");
            pst.setInt(2, usuId);
            alterado = pst.executeUpdate();           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inativar o usuário!"+e.getMessage());
        }
       return alterado;
    }
}
