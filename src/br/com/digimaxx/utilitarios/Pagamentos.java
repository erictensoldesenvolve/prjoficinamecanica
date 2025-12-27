package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Pagamentos {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    private int pagId;
    private int fornId;
    private Date data_vencimento;
    private Double pagValor;
    private Date data_entrada;
    private String Pag;
    private String pagDocumento;
    private Date data_pagamento;
    private Double pagJuros;
    private String cheque;

    public int getPagId() {
        return pagId;
    }

    public void setPagId(int pagId) {
        this.pagId = pagId;
    }

    public int getFornId() {
        return fornId;
    }

    public void setFornId(int fornId) {
        this.fornId = fornId;
    }

    public Date getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public Double getPagValor() {
        return pagValor;
    }

    public void setPagValor(Double pagValor) {
        this.pagValor = pagValor;
    }

    public Date getData_entrada() {
        return data_entrada;
    }

    public void setData_entrada(Date data_entrada) {
        this.data_entrada = data_entrada;
    }

    public String getPag() {
        return Pag;
    }

    public void setPag(String Pag) {
        this.Pag = Pag;
    }

    public String getPagDocumento() {
        return pagDocumento;
    }

    public void setPagDocumento(String pagDocumento) {
        this.pagDocumento = pagDocumento;
    }

    public Date getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(Date data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public Double getPagJuros() {
        return pagJuros;
    }

    public void setPagJuros(Double pagJuros) {
        this.pagJuros = pagJuros;
    }

    public String getCheque() {
        return cheque;
    }

    public void setCheque(String cheque) {
        this.cheque = cheque;
    }

    public boolean verificarTabela() {
       boolean retorno = false;
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLPAGAMENTOS'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLPAGAMENTOS (" +
                        "PAGID INT PRIMARY KEY AUTO_INCREMENT," +
                        "FORNID INT NOT NULL," +
                        "DATA_VENCIMENTO DATE NOT NULL," +
                        "PAGVALOR DECIMAL(20,2) NOT NULL," +
                        "DATA_ENTRADA DATE NOT NULL, "
                        + "PAG VARCHAR(2), "
                        + "PAGDOCUMENTO VARCHAR(255), "
                        + "DATA_PAGAMENTO DATE, "
                        + "PAGJUROS DECIMAL(20,2),"
                        + "CHEQUE VARCHAR(2) " +
                        ")";
                retorno = true;
                stmt.executeUpdate(sqlCria);  
               
            }

            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());           
        }
      return retorno;  
    }
    
    //CADASTRAR PAGAMENTO NOVO
    public boolean cadastrar(String data_entrada, String data_vencimento, int fornId, String pagDocumento, String Pag, 
            String data_pagamento, double pagValor, double pagJuros, String cheque){
        boolean r = false;
        String sql = "INSERT INTO TBLPAGAMENTOS (DATA_ENTRADA, DATA_VENCIMENTO, FORNID, PAGDOCUMENTO, PAG, "
                + "DATA_PAGAMENTO, PAGVALOR, PAGJUROS, CHEQUE) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, data_entrada);
            pst.setString(2, data_vencimento);
            pst.setInt(3, fornId);
            pst.setString(4, pagDocumento);
            pst.setString(5, Pag);
            
           // Define data_pagamento conforme valor de Pag
            if (Pag.equals("S") && data_pagamento != null && !data_pagamento.isEmpty()) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(data_pagamento); 
                pst.setDate(6, sqlDate);
            } else {
                pst.setNull(6, java.sql.Types.DATE);
            }
           
            pst.setDouble(7, pagValor);           
            pst.setDouble(8, pagJuros);          
            pst.setString(9, cheque);
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){r = true;}
        } catch (Exception e) {
            e.printStackTrace(); // imprime no console
            JOptionPane.showMessageDialog(null, "ERRO AO CADASTRAR PAGAMENTO:\n" + e.getClass().getName() + "\n" + e.getMessage());

        }
        return r;
    }
    
    //    CADASTRAR PAGAMENTOS PARCELADOS NO BANCO
    public void cadastrarParcelas(String data_entrada, String data_venc, String pagDocumento, int fornId, double valor, double valorPago, String Pag, String cheque){
        
        String sql = "INSERT INTO TBLPAGAMENTOS (DATA_ENTRADA, DATA_VENCIMENTO, FORNID, PAGDOCUMENTO, PAG,DATA_PAGAMENTO, PAGVALOR, PAGJUROS, CHEQUE) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, data_entrada);
            pst.setString(2, data_venc);
            pst.setInt(3, fornId);
            pst.setString(4, pagDocumento);             
            pst.setString(5, Pag);
            pst.setString(6, null);
            pst.setDouble(7, valor);
            pst.setDouble(8, valorPago - valor);         
            pst.setString(9, cheque);        
            pst.executeUpdate();
                     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        
    }
    
    
     //PESQUISA OS DADOS DO PAGAMENTO PELO CÓDIGO
    public void pesquisar(int pagId){
        String sql = "SELECT * FROM TBLPAGAMENTOS WHERE PAGID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, pagId);
            rs = pst.executeQuery();
            if(rs.next()){                
                 setFornId(rs.getInt("FORNID"));
                 setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                 setPagValor(rs.getDouble("PAGVALOR"));
                 setData_entrada(rs.getDate("DATA_ENTRADA"));
                 setPag(rs.getString("PAG"));
                 setPagDocumento(rs.getString("PAGDOCUMENTO"));
                 setData_pagamento(rs.getDate("DATA_PAGAMENTO"));
                 setPagJuros(rs.getDouble("PAGJUROS"));
                 setCheque(rs.getString("CHEQUE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }
    
//    FAZ ALTERAÇÃO DOS DADOS
    public boolean alterar(String data_entrada, String data_vencimento, int fornId, String pagDocumento, 
            String Pag, String data_pagamento, double pagValor, double pagJuros, String cheque, int pagId){
        boolean retorno = false;
        String sql = "UPDATE TBLPAGAMENTOS SET DATA_ENTRADA = ?, DATA_VENCIMENTO = ?, FORNID = ?, PAGDOCUMENTO = ?, "
                + "PAG = ?, DATA_PAGAMENTO = ?, PAGVALOR = ?, PAGJUROS = ?, CHEQUE = ? WHERE PAGID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, data_entrada);
            pst.setString(2, data_vencimento);
            pst.setInt(3, fornId);
            pst.setString(4, pagDocumento);
            pst.setString(5, Pag);
            pst.setString(6, data_pagamento);
            pst.setDouble(7, pagValor);
            pst.setDouble(8, pagJuros);
            pst.setString(9, cheque);
            pst.setInt(10, pagId);
            int alterado = pst.executeUpdate();
            if(alterado > 0){
                retorno = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    //QUITAR PAGAMENTO
    public boolean pagar(int pagId){
        boolean retorno = false;
        java.util.Date data = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        String sql = "UPDATE TBLPAGAMENTOS SET PAG = ?, DATA_PAGAMENTO = ? WHERE PAGID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "S");
            pst.setDate(2, sqlDate);
            pst.setInt(3, pagId);
            
            int alterado = pst.executeUpdate();
            if(alterado > 0){                           
                
                retorno = true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        } 
        return retorno;
    }
    
    //EXCLUI UM PAGAMENTO
        public boolean excluir(int pagId){  
            boolean retorno = false;
            String sql = "DELETE FROM TBLPAGAMENTOS WHERE PAGID = ?";
            try {
                conexao = ConexaoBanco.conectar();
                pst = conexao.prepareStatement(sql);
                pst.setInt(1, pagId);
                int excluido = pst.executeUpdate();
                if(excluido > 0 ){ retorno = true; }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
            }
        return retorno;
    }
        
    // BUSCA PAGAMENTOS ATRAVÉS DE DATAS PARA O FLUXO DE CAIXA   
    public Double pesquisaData(String dataInicial, String dataFinal){
      Double total = 0.0;
      String sql = "SELECT * FROM TBLPAGAMENTOS WHERE DATA_PAGAMENTO BETWEEN ? AND ? AND PAG = 'S'";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataInicial);
            pst.setString(2, dataFinal);
            rs = pst.executeQuery();
            while(rs.next()){
                total += rs.getDouble("PAGVALOR");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR PAGAMENTOS"+e.getMessage());
        }
      return total;
    }
    
    // BUSCA JUROS GASTOS ATRAVÉS DE DATAS PARA O FLUXO DE CAIXA   
    public Double pesquisaJuros(String dataInicial, String dataFinal){
      Double total = 0.0;
      String sql = "SELECT * FROM TBLPAGAMENTOS WHERE DATA_PAGAMENTO BETWEEN ? AND ? AND PAG = 'S'";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataInicial);
            pst.setString(2, dataFinal);
            rs = pst.executeQuery();
            while(rs.next()){
                total += rs.getDouble("PAGJUROS");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR PAGAMENTOS"+e.getMessage());
        }
      return total;
    }
    
    // BUSCA JUROS GASTOS ATRAVÉS DE DATAS PARA O FLUXO DE CAIXA   
    public Double pesquisaAPagar(String dataInicial, String dataFinal){
      Double total = 0.0;
      String sql = "SELECT * FROM TBLPAGAMENTOS WHERE DATA_VENCIMENTO BETWEEN ? AND ? AND PAG = 'N'";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataInicial);
            pst.setString(2, dataFinal);
            rs = pst.executeQuery();
            while(rs.next()){
                total += rs.getDouble("PAGVALOR");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO AO BUSCAR PAGAMENTOS"+e.getMessage());
        }
      return total;
    }
    
    public ArrayList<Pagamentos> selecionarDebitosMensais(String mes){
        ArrayList<Pagamentos> lista = new ArrayList<>();
        String sql = "SELECT PAGID, DATA_VENCIMENTO, PAGVALOR, DATA_ENTRADA, FORNID, PAG FROM TBLPAGAMENTOS "
                + "WHERE DATA_VENCIMENTO LIKE ? AND PAG = 'N' "
                + "ORDER BY DATA_VENCIMENTO ASC ";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql); 
            pst.setString(1, mes+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagId(rs.getInt("PAGID"));
                pagamento.setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                pagamento.setPagValor(rs.getDouble("PAGVALOR"));
                pagamento.setData_entrada(rs.getDate("DATA_ENTRADA"));
                pagamento.setFornId(rs.getInt("FORNID"));
                pagamento.setPag(rs.getString("PAG"));
                lista.add(pagamento);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<Pagamentos> selecionarDebitosIntervalo(String dataInicial, String dataFinal, String Pag){
        ArrayList<Pagamentos> lista = new ArrayList<>();
        String sql = "SELECT PAGID, DATA_VENCIMENTO, PAGVALOR, DATA_ENTRADA, FORNID, PAG FROM TBLPAGAMENTOS "
                + "WHERE DATA_VENCIMENTO BETWEEN ? AND ? AND PAG = ? "
                + "ORDER BY DATA_VENCIMENTO ASC ";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql); 
            pst.setString(1, dataInicial);
            pst.setString(2, dataFinal);
            pst.setString(3, Pag);
            rs = pst.executeQuery();
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagId(rs.getInt("PAGID"));
                pagamento.setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                pagamento.setPagValor(rs.getDouble("PAGVALOR"));
                pagamento.setData_entrada(rs.getDate("DATA_ENTRADA"));
                pagamento.setFornId(rs.getInt("FORNID"));
                pagamento.setPag(rs.getString("PAG"));
                lista.add(pagamento);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<Pagamentos> selecionarDebitosFornecedor(int fornId, String Pag, String dataInicio, String dataFim){
        ArrayList<Pagamentos> lista = new ArrayList<>();
        String sql = null;
        if(Pag.equals("N")){
            sql = "SELECT PAGID, DATA_VENCIMENTO, PAGVALOR, DATA_ENTRADA, PAGDOCUMENTO, FORNID, PAGJUROS FROM TBLPAGAMENTOS "
                    + "WHERE DATA_VENCIMENTO BETWEEN ? AND ? AND FORNID = ? AND PAG = ? "
                    + "ORDER BY DATA_VENCIMENTO ASC";                   
        }else{
            sql = "SELECT PAGID, DATA_VENCIMENTO, PAGVALOR, DATA_PAGAMENTO, PAGDOCUMENTO, FORNID, PAGJUROS FROM TBLPAGAMENTOS "
                    + "WHERE DATA_PAGAMENTO BETWEEN ? AND ? AND FORNID = ? AND PAG = ? "
                    + "ORDER BY DATA_PAGAMENTO ASC";
        }      
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql); 
            pst.setString(1, dataInicio);
            pst.setString(2, dataFim);
            pst.setInt(3, fornId);
            pst.setString(4, Pag);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagId(rs.getInt("PAGID"));
                pagamento.setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                pagamento.setPagValor(rs.getDouble("PAGVALOR"));
                if(Pag.equals("N")){
                    pagamento.setData_entrada(rs.getDate("DATA_ENTRADA"));
                }
                pagamento.setFornId(rs.getInt("FORNID"));
                if(Pag.equals("S")){
                    pagamento.setData_pagamento(rs.getDate("DATA_PAGAMENTO"));
                }
                pagamento.setPagDocumento(rs.getString("PAGDOCUMENTO"));
                pagamento.setPagJuros(rs.getDouble("PAGJUROS"));
                lista.add(pagamento);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<Pagamentos> selecionarDebitosAPagar(String Pag, String dataInicio, String dataFim){
        ArrayList<Pagamentos> lista = new ArrayList<>();
        String sql = "SELECT * FROM TBLPAGAMENTOS "
                + "WHERE DATA_VENCIMENTO BETWEEN ? AND ? AND PAG = ? "
                + "ORDER BY DATA_VENCIMENTO ASC";
          
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql); 
            pst.setString(1, dataInicio);
            pst.setString(2, dataFim);           
            pst.setString(3, Pag);
            rs = pst.executeQuery();            
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagId(rs.getInt("PAGID"));
                pagamento.setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                pagamento.setPagValor(rs.getDouble("PAGVALOR"));                
                pagamento.setData_entrada(rs.getDate("DATA_ENTRADA"));               
                pagamento.setFornId(rs.getInt("FORNID"));                
                pagamento.setPagDocumento(rs.getString("PAGDOCUMENTO"));                
                lista.add(pagamento);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<Pagamentos> selecionarDebitosPagos(String Pag, String dataInicio, String dataFim){
        ArrayList<Pagamentos> lista = new ArrayList<>();
        String sql = "SELECT * FROM TBLPAGAMENTOS "
                + "WHERE DATA_PAGAMENTO BETWEEN ? AND ? AND PAG = ? "
                + "ORDER BY DATA_PAGAMENTO ASC";
          
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql); 
            pst.setString(1, dataInicio);
            pst.setString(2, dataFim);           
            pst.setString(3, Pag);
            rs = pst.executeQuery();            
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagId(rs.getInt("PAGID"));
                pagamento.setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                pagamento.setPagValor(rs.getDouble("PAGVALOR"));                
                pagamento.setData_pagamento(rs.getDate("DATA_PAGAMENTO"));               
                pagamento.setFornId(rs.getInt("FORNID"));                
                pagamento.setPagDocumento(rs.getString("PAGDOCUMENTO"));
                pagamento.setPagJuros(rs.getDouble("PAGJUROS"));
                lista.add(pagamento);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
     return lista;   
    }
    
    public ArrayList<Pagamentos> somaContasAPagar(String mes){
        ArrayList<Pagamentos> lista = new ArrayList();
        String sql = "SELECT SUM(PAGVALOR) AS valor "
                + "FROM TBLPAGAMENTOS "
                + "WHERE DATA_VENCIMENTO LIKE ? AND PAG = 'N' "
                + "GROUP BY DATA_VENCIMENTO "
                + "ORDER BY DATA_VENCIMENTO";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, mes+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagValor(rs.getDouble("valor"));
                lista.add(pagamento);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Contas A Pagar: "+e.getMessage());
        }
        return lista;
    }
    
    public ArrayList<Pagamentos> somaSaidas(String mes){
        ArrayList<Pagamentos> lista = new ArrayList();
        String sql = "SELECT SUM(PAGVALOR) AS valor, SUM(PAGJUROS) AS juros "
                + "FROM TBLPAGAMENTOS "
                + "WHERE DATA_PAGAMENTO LIKE ? AND PAG = 'S' "
                + "GROUP BY DATA_PAGAMENTO "
                + "ORDER BY DATA_PAGAMENTO";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, mes+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagValor(rs.getDouble("valor"));
                pagamento.setPagJuros(rs.getDouble("juros"));
                lista.add(pagamento);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Contas A Pagar: "+e.getMessage());
        }
        return lista;
    }
    
//    CONSULTA CHEQUES PELA DATA DE PAGAMENTO
    public ArrayList<Pagamentos> pesquisaChequesNPagos(){
        ArrayList<Pagamentos> lista = new ArrayList();
        String sql = "SELECT PAGID, FORNID, DATA_ENTRADA, DATA_VENCIMENTO, PAG, PAGVALOR FROM TBLPAGAMENTOS "
                + "WHERE CHEQUE = 'S' AND PAG = 'N' "
                + "ORDER BY DATA_VENCIMENTO";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagId(rs.getInt("PAGID"));
                pagamento.setFornId(rs.getInt("FORNID"));
                pagamento.setData_entrada(rs.getDate("DATA_ENTRADA"));
                pagamento.setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                pagamento.setPag(rs.getString("PAG"));
                pagamento.setPagValor(rs.getDouble("PAGVALOR"));
                lista.add(pagamento);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Contas A Pagar: "+e.getMessage());
        }
        return lista;
    }
    
    public ArrayList<Pagamentos> pesquisaChequesIntervalo(String dataIn, String dataFim, String Pag){
        ArrayList<Pagamentos> lista = new ArrayList();
        String sql = "SELECT * FROM TBLPAGAMENTOS WHERE DATA_VENCIMENTO BETWEEN ? AND ? AND PAG = ? AND CHEQUE = 'S' "
                + "ORDER BY DATA_PAGAMENTO";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, dataIn);
            pst.setString(2, dataFim);
            pst.setString(3, Pag);
            rs = pst.executeQuery();
            while(rs.next()){
                Pagamentos pagamento = new Pagamentos();
                pagamento.setPagId(rs.getInt("PAGID"));
                pagamento.setData_entrada(rs.getDate("DATA_ENTRADA"));
                pagamento.setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                pagamento.setData_pagamento(rs.getDate("DATA_PAGAMENTO"));
                pagamento.setFornId(rs.getInt("FORNID"));
                pagamento.setPagValor(rs.getDouble("PAGVALOR"));
                pagamento.setPagJuros(rs.getDouble("PAGJUROS"));
                lista.add(pagamento);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar pagamentos! "+e.getMessage());
        }
        return lista;
    }
    
    public void pesquisaDatas(int pagId){
        String sql = "SELECT * FROM TBLPAGAMENTOS "
                + "WHERE PAGID = ? AND CHEQUE = 'S'";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);           
            pst.setInt(1, pagId);
            rs = pst.executeQuery();
            if(rs.next()){
                setPagId(rs.getInt("PAGID"));
                setData_entrada(rs.getDate("DATA_ENTRADA"));
                setData_vencimento(rs.getDate("DATA_VENCIMENTO"));
                setPagValor(rs.getDouble("PAGVALOR"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar pagamentos! "+e.getMessage());
        }
    }
}
