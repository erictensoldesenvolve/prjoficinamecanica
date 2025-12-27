package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Vendas {
    private int vId;
    private Double vTotal;
    private int vVendedorId;
    private String vTipo;
    private int cliId;
    private Double troco;
    private String vData;
    private Double vAtendimentos;
    private Double vTicket;
    private int vQtd;
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    SimpleDateFormat banco = new SimpleDateFormat("yyyy-MM-dd");
    

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public Double getvTotal() {
        return vTotal;
    }

    public void setvTotal(Double vTotal) {
        this.vTotal = vTotal;
    }

    public int getvVendedorId() {
        return vVendedorId;
    }

    public void setvVendedorId(int vVendedorId) {
        this.vVendedorId = vVendedorId;
    }

    public String getvTipo() {
        return vTipo;
    }

    public void setvTipo(String vTipo) {
        this.vTipo = vTipo;
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public int getCliId() {
        return cliId;
    }

    public void setCliId(int cliId) {
        this.cliId = cliId;
    }

    public Double getTroco() {
        return troco;
    }

    public void setTroco(Double troco) {
        this.troco = troco;
    }

    public String getvData() {
        return vData;
    }

    public void setvData(String vData) {
        this.vData = vData;
    }

    public Double getvAtendimentos() {
        return vAtendimentos;
    }

    public void setvAtendimentos(Double vAtendimentos) {
        this.vAtendimentos = vAtendimentos;
    }

    public Double getvTicket() {
        return vTicket;
    }

    public void setvTicket(Double vTicket) {
        this.vTicket = vTicket;
    }

    public int getvQtd() {
        return vQtd;
    }

    public void setvQtd(int vQtd) {
        this.vQtd = vQtd;
    }
    
    public boolean localizaVendaZero(){
        boolean retorno = false;
        String sql = "SELECT * FROM TBLVENDAS WHERE VTOTAL = 0.00";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                setvId(rs.getInt("VID"));
                retorno = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public void excluiVendaZero(int vId){
        String sql = "DELETE FROM TBLVENDAS WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }

   public boolean verificarTabela() {
       boolean retorno = false;
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLVENDAS'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLVENDAS (" +
                        "VID INT PRIMARY KEY AUTO_INCREMENT," +
                        "VDATA timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "VVENDEDORID INT NOT NULL," + 
                        "VTIPO INT (11) NOT NULL, " +
                        "CLIID INT (11), " +                        
                        "TROCO DECIMAL(20,2), " +                       
                        "VTOTAL DECIMAL(20,2) " +                       
                        ")";
                retorno = true;
                stmt.executeUpdate(sqlCria);  
               
            }

            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
            e.printStackTrace();
        }
      return retorno;  
    }
   
    //INSERIR VENDA
    public boolean inserir(int usuId){
        boolean retorno = false;
        String sql = "INSERT INTO TBLVENDAS (VVENDEDORID, VTOTAL, VTIPO) VALUES (?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, usuId);
            pst.setDouble(2, 0.00);
            pst.setInt(3, 0);
            
                int adicionado = pst.executeUpdate();
                if(adicionado > 0){ 
                  retorno = true;  
                }
           
        } catch (Exception e) {
            System.out.println("Erro "+e.getMessage());;
        }
        return retorno;
    }
   
    public void buscaUltimaVenda(){
        String sql = "SELECT * FROM TBLVENDAS ORDER BY VID DESC LIMIT 1";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            
            rs = pst.executeQuery();
            if(rs.next()){
                setvId(rs.getInt("VID"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
    
    //Altera o total da venda quando o item for excluido
    public boolean alteraTotal(Double vTotal, int vId, int vTipo){
        boolean retorno = false;
        String sql = "UPDATE TBLVENDAS SET VTOTAL = ?, VTIPO = ? WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setDouble(1, vTotal);
            pst.setInt(2, vTipo);
            pst.setInt(3, vId);
            int alterar = pst.executeUpdate();
            if(alterar > 0 ){pst.close(); retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return retorno;
    }
    
    //Altera o total da venda quando o item for excluido
    public boolean alteraTotal(Double vTotal, int vId){
        boolean retorno = false;
        String sql = "UPDATE TBLVENDAS SET VTOTAL = ? WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setDouble(1, vTotal);            
            pst.setInt(2, vId);
            int alterar = pst.executeUpdate();
            if(alterar > 0 ){pst.close(); retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return retorno;
    }
    
    //ALTERA O TROCO ATRAVÉS DO CODIGO DA VENDA
    public void alteraTroco(int vId, Double troco){
        String sql = "UPDATE TBLVENDAS SET TROCO = ? WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);            
            pst.setDouble(1, troco);
            pst.setInt(2, vId);
            int alterar = pst.executeUpdate();
            if(alterar > 0 ){pst.close();}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void alteraCliente(int vId, int cliId){
        String sql = "UPDATE TBLVENDAS SET CLIID = ? WHERE VID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);            
            pst.setDouble(1, cliId);
            pst.setInt(2, vId);
            int alterar = pst.executeUpdate();
            if(alterar > 0 ){pst.close();}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
 
    public void excluiVenda(int idVenda){
        String sql = "DELETE FROM TBLVENDAS WHERE VID = "+idVenda;
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    //    PROCURA DADOS DA VENDA PARA ENCERRAR E GUARDAR TROC
    public void localizaVenda(int vId){
       String sql = "SELECT * FROM TBLVENDAS WHERE VID = ?"; 
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, vId);
            rs = pst.executeQuery();
            if(rs.next()){
                setvTotal(rs.getDouble("VTOTAL"));
                setvData(banco.format(rs.getDate("VDATA")));
                setvVendedorId(rs.getInt("VVENDEDORID"));
                setCliId(rs.getInt("CLIID"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }   
    
    //SELECIONA VENDAS DE ACORDO COM A DATA PARA O FLUXO DE CAIXA
    public Double pesquisaData(String dataInicial, String dataFinal){
        Double total = 0.0;
        String sql = "SELECT * FROM TBLVENDAS WHERE VDATA BETWEEN ? AND ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataInicial);
            pst.setString(2, dataFinal);
            rs = pst.executeQuery();
            while(rs.next()){
               total += rs.getDouble("VTOTAL");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR VENDAS: "+e.getMessage());
        }
        return total;
    }
    
    public ArrayList<Vendas> somar(String dataIn, String dataFim){
        ArrayList<Vendas> lista = new ArrayList();
        String sql = "SELECT " +
                        "    VDATA AS data, " +
                        "    SUM(VTOTAL) AS total, " +
                        "    COUNT(VID) AS atendimentos, " +
                        "    SUM(VTOTAL) / COUNT(VID) AS ticket " +
                        "FROM TBLVENDAS " +
                        "WHERE VDATA BETWEEN ? AND ? " +
                        "GROUP BY VDATA " +
                        "ORDER BY VDATA";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataIn);
            pst.setString(2, dataFim);
            rs = pst.executeQuery();
            while(rs.next()){
                Vendas venda = new Vendas();
                venda.setvData(rs.getString("data"));
                venda.setvTotal(rs.getDouble("total"));
                venda.setvAtendimentos(rs.getDouble("atendimentos"));
                venda.setvTicket(rs.getDouble("ticket"));
                lista.add(venda);
            }
        } catch (Exception e) {
        }
        return lista;
    }
    
    public ArrayList<Vendas> somarPorDia(String dataIn, String dataFim) {
        ArrayList<Vendas> lista = new ArrayList<>();

        String sql =
            "SELECT U.USUID AS usuario, V.VDATA AS dia, " +
            "       SUM(V.VTOTAL) AS total, " +
            "       COUNT(V.VID) AS atendimentos, " +
            "       SUM(V.VTOTAL) / COUNT(V.VID) AS ticket,"
                + "SUM(IT.QUANTIDADE) AS itens " +
            "FROM TBLVENDAS V "
                + "INNER JOIN TBLITENSVENDA IT "
                + "ON V.VID = IT.VID "
                + "INNER JOIN USUARIOS U "
                + "ON U.USUID = V.VVENDEDORID " +
            "WHERE V.VDATA BETWEEN ? AND ? " +
            "GROUP BY V.VDATA " +
            "ORDER BY V.VDATA";

        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataIn);
            pst.setString(2, dataFim);
            rs = pst.executeQuery();

            while (rs.next()) {
                Vendas venda = new Vendas();

                // Aqui data é String yyyy-MM-dd (ideal para substring)
                venda.setvData(rs.getString("dia"));
                venda.setvTotal(rs.getDouble("total"));
                venda.setvAtendimentos(rs.getDouble("atendimentos"));
                venda.setvTicket(rs.getDouble("ticket"));
                venda.setvQtd(rs.getInt("itens"));
                venda.setvVendedorId(rs.getInt("usuario"));
                lista.add(venda);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao somar por dia: " + e.getMessage());
        }

    return lista;
}
    
    public ArrayList<Vendas> somarMes(String mes) {
        ArrayList<Vendas> lista = new ArrayList<>();

        String sql =
            "SELECT V.VDATA AS dia, " +
            "       SUM(V.VTOTAL) AS total, " +
            "       COUNT(V.VID) AS atendimentos, " +
            "       SUM(V.VTOTAL) / COUNT(V.VID) AS ticket,"
                + "SUM(IT.QUANTIDADE) AS itens " +
            "FROM TBLVENDAS V "
                + "INNER JOIN TBLITENSVENDA IT "
                + "ON V.VID = IT.VID " +
            "WHERE V.VDATA LIKE ? " +
            "GROUP BY V.VDATA " +
            "ORDER BY V.VDATA";

        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, mes+"%");
            
            rs = pst.executeQuery();

            while (rs.next()) {
                Vendas venda = new Vendas();

                // Aqui data é String yyyy-MM-dd (ideal para substring)
                venda.setvData(rs.getString("dia"));
                venda.setvTotal(rs.getDouble("total"));
                venda.setvAtendimentos(rs.getDouble("atendimentos"));
                venda.setvTicket(rs.getDouble("ticket"));
                venda.setvQtd(rs.getInt("itens"));
                lista.add(venda);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao somar por dia: " + e.getMessage());
        }

    return lista;
}
    
    public ArrayList<Vendas> calculaVendedor(String mes){
        ArrayList<Vendas> lista = new ArrayList();
        String sql = "SELECT U.USUID AS vendedor, SUM(V.VTOTAL) AS total, COUNT(V.VID) AS atendimentos, "
                + "SUM(V.VTOTAL)/count(V.VID) AS ticket FROM TBLVENDAS V "
                + "INNER JOIN USUARIOS U ON U.USUID = V.VVENDEDORID "
                + "WHERE V.VDATA LIKE ? "
                + "GROUP BY V.VVENDEDORID"; 
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, mes+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                Vendas vendas = new Vendas();
                vendas.setvVendedorId(rs.getInt("vendedor"));
                vendas.setvTotal(rs.getDouble("total"));
                vendas.setvAtendimentos(rs.getDouble("atendimentos"));
                vendas.setvTicket(rs.getDouble("ticket"));
                lista.add(vendas);
            }
        } catch (Exception e) {
        }
        return lista;
    }
    
    public ArrayList<Vendas> buscaIntervalo(String dataIn, String dataFim){
        ArrayList<Vendas> lista = new ArrayList();
        String sql = "SELECT * FROM TBLVENDAS WHERE VDATA BETWEEN ? AND ? "
                + "ORDER BY VDATA";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataIn);
            pst.setString(2, dataFim);
            rs = pst.executeQuery();
            while(rs.next()){
                Vendas venda = new Vendas();
                venda.setvId(rs.getInt("VID"));
                venda.setvData(rs.getString("VDATA"));
                venda.setvTipo(rs.getString("VTIPO"));
                venda.setvVendedorId(rs.getInt("VVENDEDORID"));
                venda.setCliId(rs.getInt("CLIID"));
                venda.setvTotal(rs.getDouble("VTOTAL"));
                venda.setTroco(rs.getDouble("TROCO"));
                lista.add(venda);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas: "+e.getMessage());
        }
        return lista;
    }
    
    public ArrayList<Vendas> buscaTipo(String dataIn, String dataFim, int vTipo){
        ArrayList<Vendas> lista = new ArrayList();
        String sql = "SELECT * FROM TBLVENDAS WHERE VDATA BETWEEN ? AND ? AND VTIPO = ? "
                + "ORDER BY VDATA";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataIn);
            pst.setString(2, dataFim);
            pst.setInt(3, vTipo);
            rs = pst.executeQuery();
            while(rs.next()){
                Vendas venda = new Vendas();
                venda.setvId(rs.getInt("VID"));
                venda.setvData(rs.getString("VDATA"));
                venda.setvTipo(rs.getString("VTIPO"));
                venda.setvVendedorId(rs.getInt("VVENDEDORID"));
                venda.setCliId(rs.getInt("CLIID"));
                venda.setvTotal(rs.getDouble("VTOTAL"));
                venda.setTroco(rs.getDouble("TROCO"));
                lista.add(venda);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar vendas: "+e.getMessage());
        }
        return lista;
    }
}
