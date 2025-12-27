
package br.com.digimaxx.utilitarios;
import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Estados {
    private int estaId;
    private String estaSigla;
    private String estaNome;

    static Connection conexao = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;

    public int getEstaId() {
        return estaId;
    }

    public void setEstaId(int estaId) {
        this.estaId = estaId;
    }

    public String getEstaSigla() {
        return estaSigla;
    }

    public void setEstaSigla(String estaSigla) {
        this.estaSigla = estaSigla;
    }

    public String getEstaNome() {
        return estaNome;
    }

    public void setEstaNome(String estaNome) {
        this.estaNome = estaNome;
    }
  
        
    public void pesquisaEstado(int estaId){
        String sql = "SELECT * FROM estados WHERE estaId = "+estaId;
        try{
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                this.setEstaNome(rs.getString("estaNome"));
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void pesquisaId(String estaNome){
        String sql = "select * from estados where estaNome = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, estaNome);
            rs = pst.executeQuery();
            if(rs.next()){
              this.setEstaId(rs.getInt("estaId"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public ArrayList<Estados> pesquisar(){
        ArrayList<Estados> lista = new ArrayList<>();
        String sql = "SELECT * FROM estados ORDER BY ESTANOME ASC";
    
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);            
            rs = pst.executeQuery();
        
            while (rs.next()) {
                Estados estado = new Estados();
                estado.setEstaId(rs.getInt("ESTAID"));
                estado.setEstaNome(rs.getString("ESTANOME"));
                lista.add(estado);
            }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao pesquisar clientes: " + e.getMessage());
    }
    return lista;
}
}
