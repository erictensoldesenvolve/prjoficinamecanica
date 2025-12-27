package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class NotasFiscais {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    private int notaId;
    private String numero;
    private int serie;
    private int cfop;
    private Date dataEmissao;
    private String chaveAcesso;
    private Date dataEntrada;
    private int fornId;
    private Double desconto;
    private Double valorTotal;

    public int getNotaId() {
        return notaId;
    }

    public void setNotaId(int notaId) {
        this.notaId = notaId;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getCfop() {
        return cfop;
    }

    public void setCfop(int cfop) {
        this.cfop = cfop;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public int getFornId() {
        return fornId;
    }

    public void setFornId(int fornId) {
        this.fornId = fornId;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    //    VERIFICA SE EXISTE A TABELA NO BANCO DE DADOS
    public void criaTabela() {
       
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'notasFiscais'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLNOTASFISCAIS (" +
                        "NOTAID int(11) PRIMARY KEY AUTO_INCREMENT," +
                        "NUMERO varchar(255) NOT NULL, "+
                        "SERIE varchar(255) NOT NULL," +
                        "CFOP varchar(255)," +
                        "DATAEMISSAO DATE," +
                        "CHAVE VARCHAR(255), " +
                        "DATAENTRADA DATE," +
                        "FORNID INT NOT NULL, " +
                        "DESCONTO DECIMAL(20, 2), " +
                        "VALORTOTAL DECIMAL(20,2) NOT NULL " +                       
                        ")";                
                stmt.executeUpdate(sqlCria);  
            }
            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        }
        
    }
 
    public boolean cadastrarNota(String numeroNota, int serie, int cfop, String dataEmissao, String chave, String dataEntrada, 
            int fornId, Double desconto, Double total){
        boolean retorno = false;
        String sql = "INSERT INTO TBLNOTASFISCAIS (NUMERO, SERIE, CFOP, DATAEMISSAO, CHAVE, DATAENTRADA, FORNID, DESCONTO, VALORTOTAL) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, numeroNota);
            pst.setInt(2, serie);
            pst.setInt(3, cfop);
            pst.setString(4, dataEmissao);
            pst.setString(5, chave);
            pst.setString(6, dataEntrada);
            pst.setInt(7, fornId);
            pst.setDouble(8, desconto);
            pst.setDouble(9, total);
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){ retorno = true; }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR NOTA FISCAL: " + e.getMessage());
        }
        return retorno;
    }
    
    public void buscaUltimaNota(){
        String sql = "SELECT * FROM TBLNOTASFISCAIS ORDER BY NOTAID DESC LIMIT 1";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                setNotaId(rs.getInt("NOTAID"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LOCALIZAR NOTA FISCAL: " + e.getMessage());
        }
    }
   
    //PESQUISA NOTA FISCAL PELO CÓDIGO DA NOTA    
    public void pesquisaId(int notaId){
        String sql = "SELECT * FROM TBLNOTASFISCAIS WHERE NOTAID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, notaId);
            rs = pst.executeQuery();
            if(rs.next()){
                setValorTotal(rs.getDouble("VALORTOTAL"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LOCALIZAR NOTA FISCAL: " + e.getMessage());
        }
    }
    
    //PESQUISA NOTA FISCAL PELO CÓDIGO DA NOTA    
    public void pesquisar(int notaId){
        String sql = "SELECT * FROM TBLNOTASFISCAIS WHERE NOTAID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, notaId);
            rs = pst.executeQuery();
            if(rs.next()){
                setNumero(rs.getString("NUMERO"));
                setSerie(rs.getInt("SERIE"));
                setCfop(rs.getInt("CFOP"));
                setDataEmissao(rs.getDate("DATAEMISSAO"));
                setChaveAcesso(rs.getString("CHAVE"));
                setDataEntrada(rs.getDate("DATAENTRADA"));
                setFornId(rs.getInt("FORNID"));
                setDesconto(rs.getDouble("DESCONTO"));
                setValorTotal(rs.getDouble("VALORTOTAL"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LOCALIZAR NOTA FISCAL: " + e.getMessage());
        }
    }
    
    public void excluirNota(int notaId){
        String sql = "DELETE FROM TBLNOTASFISCAIS WHERE NOTAID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, notaId);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO LOCALIZAR NOTA FISCAL: " + e.getMessage());
        }
    }
    
    public boolean alterarNota(int notaId, String numero, String serie, String cfop, String dataEmissao, String chave, 
            String dataEntrada, int fornId, Double desconto, Double valorTotal){
        boolean retorno = false;
        String sql = "UPDATE TBLNOTASFISCAIS SET NUMERO = ?, SERIE = ?, CFOP = ?, DATAEMISSAO = ?, CHAVE = ?, "
                + "DATAENTRADA = ?, FORNID = ?, DESCONTO = ?, VALORTOTAL = ? WHERE NOTAID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, numero);
            pst.setString(2, serie);
            pst.setString(3, cfop);
            pst.setString(4, dataEmissao);
            pst.setString(5, chave);
            pst.setString(6, dataEntrada);
            pst.setInt(7, fornId);
            pst.setDouble(8, desconto);
            pst.setDouble(9, valorTotal);
            pst.setInt(10, notaId);
            int alterado = pst.executeUpdate();
            if(alterado > 0 ){ retorno = true; }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO ALTERAR TABELA: " + e.getMessage());
        }
        return retorno;
    }
    
    public ArrayList<NotasFiscais> pesquisaMensal(String mes){
        ArrayList<NotasFiscais> lista = new ArrayList<>();
        String sql = "SELECT * FROM TBLNOTASFISCAIS "
                + "WHERE DATAENTRADA LIKE ? "
                + "ORDER BY DATAENTRADA ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, mes+"%");
            rs = pst.executeQuery();
            
            while(rs.next()){
               NotasFiscais nota = new NotasFiscais();
               nota.setNotaId(rs.getInt("NOTAID"));
               nota.setDataEntrada(rs.getDate("DATAENTRADA"));
               nota.setFornId(rs.getInt("FORNID"));
               nota.setNumero(rs.getString("NUMERO"));      
               nota.setValorTotal(rs.getDouble("VALORTOTAL"));                             
               lista.add(nota);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<NotasFiscais> pesquisaIntervalosData(String dataInicio, String dataFim){
        ArrayList<NotasFiscais> lista = new ArrayList<>();
        String sql = "SELECT N.*, F.FORNNOME FROM TBLNOTASFISCAIS N "
                + "INNER JOIN OF_CLIENTE_JURIDICO F ON N.FORNID = F.ID "
                + "WHERE N.DATAENTRADA BETWEEN ? AND ? "
                + "ORDER BY N.DATAENTRADA ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataInicio);
            pst.setString(2, dataFim);
            rs = pst.executeQuery();
            
            while(rs.next()){
               NotasFiscais nota = new NotasFiscais();
               nota.setNotaId(rs.getInt("NOTAID"));
               nota.setDataEntrada(rs.getDate("DATAENTRADA"));
               nota.setFornId(rs.getInt("FORNID"));
               nota.setNumero(rs.getString("NUMERO"));      
               nota.setValorTotal(rs.getDouble("VALORTOTAL"));                             
               lista.add(nota);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<NotasFiscais> pesqusisaNumeroNota(String numero){
        ArrayList<NotasFiscais> lista = new ArrayList<>();
        String sql = "SELECT N.*, F.FORNNOME FROM notasFiscais N "
                + "INNER JOIN OF_CLIENTE_JURIDICO F ON N.FORNID = F.ID "
                + "WHERE N.NUMERO = ? "
                + "ORDER BY N.DATAENTRADA ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, numero);
            rs = pst.executeQuery();            
            while(rs.next()){
               NotasFiscais nota = new NotasFiscais();
               nota.setNotaId(rs.getInt("NOTAID"));
               nota.setDataEntrada(rs.getDate("DATAENTRADA"));
               nota.setFornId(rs.getInt("FORNID"));
               nota.setNumero(rs.getString("NUMERO"));      
               nota.setValorTotal(rs.getDouble("VALORTOTAL"));                             
               lista.add(nota);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<NotasFiscais> pesquisaFabricante(String dataInicio, String dataFim, int fornId){
        ArrayList<NotasFiscais> lista = new ArrayList<>();
        String sql = "SELECT N.*, F.FORNNOME FROM notasFiscais N "
                + "INNER JOIN OF_CLIENTE_JURIDICO F ON F.ID = N.FORNID "
                + "WHERE N.DATAENTRADA BETWEEN ? AND ? AND N.FORNID = ? "
                + "ORDER BY N.DATAENTRADA ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataInicio);
            pst.setString(2, dataFim);
            pst.setInt(3, fornId);
            rs = pst.executeQuery();            
            while(rs.next()){
               NotasFiscais nota = new NotasFiscais();
               nota.setNotaId(rs.getInt("NOTAID"));
               nota.setDataEntrada(rs.getDate("DATAENTRADA"));
               nota.setFornId(rs.getInt("FORNID"));
               nota.setNumero(rs.getString("NUMERO"));      
               nota.setValorTotal(rs.getDouble("VALORTOTAL"));                             
               lista.add(nota);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<NotasFiscais> somarMes(String mes){
        ArrayList<NotasFiscais> lista = new ArrayList();
        String sql = "SELECT VALORTOTAL AS total, DESCONTO AS desconto "
                + "FROM TBLNOTASFISCAIS WHERE DATAENTRADA LIKE ? "
                + "ORDER BY DATAENTRADA";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, mes+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                NotasFiscais nota = new NotasFiscais();
                nota.setValorTotal(rs.getDouble("total"));
                nota.setDesconto(rs.getDouble("desconto"));
                lista.add(nota);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao somar Notas Fiscais: "+e.getMessage());
        }
        return lista;
    }
    
    public ArrayList<NotasFiscais> somarIntervalo(String dataIn, String dataFim){
        ArrayList<NotasFiscais> lista = new ArrayList();
        String sql = "SELECT VALORTOTAL AS total, DESCONTO AS desconto "
                + "FROM TBLNOTASFISCAIS WHERE DATAENTRADA BETWEEN ? AND ?"
                + "ORDER BY DATAENTRADA";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataIn);
            pst.setString(2, dataFim);
            rs = pst.executeQuery();
            while(rs.next()){
                NotasFiscais nota = new NotasFiscais();
                nota.setValorTotal(rs.getDouble("total"));
                nota.setDesconto(rs.getDouble("desconto"));
                lista.add(nota);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao somar Notas Fiscais: "+e.getMessage());
        }
        return lista;
    }
}
