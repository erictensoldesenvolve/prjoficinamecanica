package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Configuracoes {
    private int id;
    private String nomeEmpresa;
    private String endereco;
    private int numero;
    private String bairro;
    private int estaId;
    private int cidaId;
    private String telefone;
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getEstaId() {
        return estaId;
    }

    public void setEstaId(int estaId) {
        this.estaId = estaId;
    }

    public int getCidaId() {
        return cidaId;
    }

    public void setCidaId(int cidaId) {
        this.cidaId = cidaId;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
          
    public boolean selecionaBanco(){
        boolean retorno = false;
        String sql = "SELECT * FROM TBLCONFIGURACOES LIMIT 1";
        try {
            conexao = ConexaoConfig.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                setId(rs.getInt("id"));
                setNomeEmpresa(rs.getString("NOMEEMPRESA"));
                setEndereco(rs.getString("ENDERECO"));
                setNumero(rs.getInt("NUMERO"));
                setBairro(rs.getString("BAIRRO"));
                setEstaId(rs.getInt("ESTAID"));
                setCidaId(rs.getInt("CIDAID"));
                setTelefone(rs.getString("TELEFONE"));
                retorno = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO "+e.getMessage());
        }
        return retorno;
    }
    
    public boolean editar(int id, String nomeEmpresa, String endereco, int numero, String bairro, int estaId, int cidaId, String telefone){
        boolean retorno = false;
        String sql = "update TBLCONFIGURACOES set nomeEmpresa = ?, endereco = ?, numero = ?, "
                + "bairro = ?, estaId = ?, cidaId = ?, telefone = ? where id = ?";
        try {
            conexao = ConexaoConfig.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nomeEmpresa);
            pst.setString(2, endereco);
            pst.setInt(3, numero);
            pst.setString(4, bairro);
            pst.setInt(5, estaId);
            pst.setInt(6, cidaId);
            pst.setString(7, telefone);
            pst.setInt(8, id);
            int alterado = pst.executeUpdate();
            if(alterado > 0){retorno=true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
}
