package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Produtos {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    private int produto_id;
    private String codigo_barras;
    private String produto;   
    private String apresentacao;
    private int fornId;
    private int catId;
    private Double prod_prcompra;
    private Double prod_prvenda;
    private Double prod_prpromocao;
    private Double estoque;
    private Double estoque_minimo;
    private Double margem_lucro;
    private Double desconto_maximo;
    private String unidade;
    private String status;

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public int getFornId() {
        return fornId;
    }

    public void setFornId(int fornId) {
        this.fornId = fornId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public Double getProd_prcompra() {
        return prod_prcompra;
    }

    public void setProd_prcompra(Double prod_prcompra) {
        this.prod_prcompra = prod_prcompra;
    }

    public Double getProd_prvenda() {
        return prod_prvenda;
    }

    public void setProd_prvenda(Double prod_prvenda) {
        this.prod_prvenda = prod_prvenda;
    }

    public Double getProd_prpromocao() {
        return prod_prpromocao;
    }

    public void setProd_prpromocao(Double prod_prpromocao) {
        this.prod_prpromocao = prod_prpromocao;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }

    public Double getEstoque_minimo() {
        return estoque_minimo;
    }

    public void setEstoque_minimo(Double estoque_minimo) {
        this.estoque_minimo = estoque_minimo;
    }

    public Double getMargem_lucro() {
        return margem_lucro;
    }

    public void setMargem_lucro(Double margem_lucro) {
        this.margem_lucro = margem_lucro;
    }

    public Double getDesconto_maximo() {
        return desconto_maximo;
    }

    public void setDesconto_maximo(Double desconto_maximo) {
        this.desconto_maximo = desconto_maximo;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    //    VERIFICA SE EXISTE A TABELA NO BANCO DE DADOS
    public void criaTabela() {
       
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLPRODUTOS'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = "CREATE TABLE IF NOT EXISTS TBLPRODUTOS (" +
                        "PRODUTO_ID INT(11) PRIMARY KEY AUTO_INCREMENT," +
                        "CODIGO_BARRAS VARCHAR(255), "+
                        "PRODUTO VARCHAR(255) NOT NULL," +
                        "APRESENTACAO VARCHAR(255)," +
                        "FORNID INT(11)," +
                        "CATID INT(11), " +
                        "PROD_PRCOMPRA DECIMAL(20,2)," +
                        "PROD_PRVENDA DECIMAL(20, 2) NOT NULL, " +
                        "PROD_PRPROMOCAO DECIMAL(20, 2) NOT NULL, " +
                        "ESTOQUE DECIMAL(20,2) NOT NULL, " +                        
                        "ESTOQUE_MINIMO DECIMAL(20,2) NOT NULL, " +                        
                        "MARGEM_LUCRO DECIMAL(20, 2), " +
                        "DESCONTO_MAXIMO DECIMAL(20, 2), " +
                        "UNIDADE VARCHAR(255), " +
                        "STATUS VARCHAR(255) " +
                        ")";
                stmt.executeUpdate(sqlCria);  
            }

            rsVerifica.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
        }
        
    }
    
    public boolean cadastrar(String codigo_barras, String produto, String apresentacao, int fornId, int catId, 
            Double prod_prcompra, Double prod_prvenda, Double prod_prpromocao, Double estoque, 
            Double estoque_minimo, Double margem_lucro, Double desconto_maximo, String unidade, String status){
        boolean retorno = false;
        String sql = "INSERT INTO TBLPRODUTOS (CODIGO_BARRAS, PRODUTO, APRESENTACAO, FORNID, CATID, PROD_PRCOMPRA, "
                + "PROD_PRVENDA, PROD_PRPROMOCAO, ESTOQUE, ESTOQUE_MINIMO, MARGEM_LUCRO, DESCONTO_MAXIMO, UNIDADE, STATUS) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codigo_barras);
            pst.setString(2, produto);
            pst.setString(3, apresentacao);
            pst.setInt(4, fornId);
            pst.setInt(5, catId);
            pst.setDouble(6, prod_prcompra);
            pst.setDouble(7, prod_prvenda);
            pst.setDouble(8, prod_prpromocao);
            pst.setDouble(9, estoque);
            pst.setDouble(10, estoque_minimo);
            pst.setDouble(11, margem_lucro);
            pst.setDouble(12, desconto_maximo);
            pst.setString(13, unidade);
            pst.setString(14, status);
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public boolean editar(String codigo_barras, String produto, String apresentacao, int fornId, int catId, 
            Double prod_prcompra, Double prod_prvenda, Double prod_prpromocao, Double estoque, 
            Double estoque_minimo, Double margem_lucro, Double desconto_maximo, String unidade, String status, int produto_id){
      boolean retorno = false;
      String sql = "UPDATE TBLPRODUTOS SET CODIGO_BARRAS = ?, PRODUTO = ?, APRESENTACAO = ?, FORNID = ?, "
              + "CATID = ?, PROD_PRCOMPRA = ?, PROD_PRVENDA = ?, PROD_PRPROMOCAO = ?, ESTOQUE = ?, "
              + "ESTOQUE_MINIMO = ?, MARGEM_LUCRO = ?, DESCONTO_MAXIMO = ?, UNIDADE = ?, STATUS = ? "
              + "WHERE PRODUTO_ID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codigo_barras);
            pst.setString(2, produto);
            pst.setString(3, apresentacao);
            pst.setInt(4, fornId);
            pst.setInt(5, catId);
            pst.setDouble(6, prod_prcompra);
            pst.setDouble(7, prod_prvenda);
            pst.setDouble(8, prod_prpromocao);
            pst.setDouble(9, estoque);
            pst.setDouble(10, estoque_minimo);
            pst.setDouble(11, margem_lucro);
            pst.setDouble(12, desconto_maximo);
            pst.setString(13, unidade);
            pst.setString(14, status);
            pst.setInt(15, produto_id);
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){retorno = true; }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
      return retorno;
    }
    
    public boolean excluir(int produto_id){
        boolean retorno = false;
        String sql = "DELETE FROM TBLPRODUTOS WHERE PRODUTO_ID = "+produto_id;
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            int excluir = pst.executeUpdate();
            if(excluir > 0){retorno = true;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
        return retorno;
    }
    
    public void pesquisaId(int produto_id){
        String sql = "SELECT * FROM TBLPRODUTOS WHERE PRODUTO_ID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, produto_id);
            rs = pst.executeQuery();
            if(rs.next()){
                setProduto_id(rs.getInt("PRODUTO_ID"));
                setCodigo_barras(rs.getString("CODIGO_BARRAS"));
                setProduto(rs.getString("PRODUTO"));   
                setApresentacao(rs.getString("APRESENTACAO"));
                setFornId(rs.getInt("FORNID"));
                setCatId(rs.getInt("CATID"));
                setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                setEstoque(rs.getDouble("ESTOQUE"));
                setEstoque_minimo(rs.getDouble("ESTOQUE_MINIMO"));
                setMargem_lucro(rs.getDouble("MARGEM_LUCRO"));
                setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));
                setUnidade(rs.getString("UNIDADE"));
                setStatus(rs.getString("STATUS"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }
    
    public void pesquisaCodigoBarras(String codigoBarras){
        String sql = "SELECT * FROM TBLPRODUTOS WHERE CODIGO_BARRAS = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, codigoBarras);
            rs = pst.executeQuery();
            if(rs.next()){
                setProduto_id(rs.getInt("PRODUTO_ID"));
                setCodigo_barras(rs.getString("CODIGO_BARRAS"));
                setProduto(rs.getString("PRODUTO"));   
                setApresentacao(rs.getString("APRESENTACAO"));
                setFornId(rs.getInt("FORNID"));
                setCatId(rs.getInt("CATID"));
                setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                setEstoque(rs.getDouble("ESTOQUE"));
                setEstoque_minimo(rs.getDouble("ESTOQUE_MINIMO"));
                setMargem_lucro(rs.getDouble("MARGEM_LUCRO"));
                setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));
                setUnidade(rs.getString("UNIDADE"));
                setStatus(rs.getString("STATUS"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
        }
    }
    
    public void selecionaEstoque(int prodId){
         String sql = "SELECT * FROM TBLPRODUTOS WHERE PRODUTO_ID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, prodId);
            rs = pst.executeQuery();
            if(rs.next()){
                setEstoque(rs.getDouble("ESTOQUE"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    } 
    
    public void alteraEstoque(int prodId, Double qtd){
        String sql = "UPDATE TBLPRODUTOS SET ESTOQUE = ? WHERE PRODUTO_ID = ?";
        selecionaEstoque(prodId);
        Double total = this.getEstoque() - qtd;
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setDouble(1, total);
            pst.setInt(2, prodId);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar estoque: "+e.getMessage());
        }
    }
    
    public void retornaEstoque(int produto_id, int qtd){
        String sql = "UPDATE TBLPRODUTOS SET ESTOQUE = ? WHERE PRODUTO_ID = ?";
        selecionaEstoque(produto_id);
        Double total = this.getEstoque() + qtd;
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setDouble(1, total);
            pst.setInt(2, produto_id);
            pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar estoque: "+e.getMessage());
        }
    }
    
    //  VERIFICA SE O VALOR DO ITEM INSERIDO É MAIOR QUE O CADASTRO.
    public void pegarPreco(int produto_id){
        String sql = "SELECT * FROM TBLPRODUTOS WHERE PRODUTO_ID = "+produto_id;
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                this.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //VERIFICA O ESTOQUE DO PRODUTO
     public boolean verificaEstoque(int produto_id, int qtd){
         boolean retorno = false;
         String sql = "SELECT * FROM TBLPRODUTOS WHERE PRODUTO_ID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, produto_id);
             rs = pst.executeQuery();
             if(rs.next()){
                 if(rs.getInt("ESTOQUE") <= 0 || rs.getInt("ESTOQUE") < qtd){retorno = true;}
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "ERRO: "+e.getMessage());
         }
         return retorno;
     }
     
     public ArrayList<Produtos> pesquisaNome(String produto){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT * FROM TBLPRODUTOS WHERE PRODUTO LIKE ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, produto + "%");
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 lista.add(produtos);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> pesquisaCodigo(int produto_id){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT * FROM TBLPRODUTOS WHERE PRODUTO_ID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, produto_id);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> listaCodigoBarras(String codigo_barras){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT * FROM TBLPRODUTOS WHERE CODIGO_BARRAS = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, codigo_barras);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> verificaSaldo(){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT * FROM TBLPRODUTOS WHERE ESTOQUE > 0 ORDER BY PRODUTO ASC";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> ordenarNome(){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT * FROM TBLPRODUTOS ORDER BY PRODUTO ASC";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> listarProdutos(){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT PRODUTO_ID, PRODUTO, APRESENTACAO, "
                 + "FORNID, ESTOQUE, PROD_PRVENDA, PROD_PRPROMOCAO, "
                 + "DESCONTO_MAXIMO, PROD_PRCOMPRA  "
                 + "FROM TBLPRODUTOS "
                 + "ORDER BY PRODUTO ASC";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> pesquisaFornecedor(){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT PRODUTO_ID, P.PRODUTO, P.APRESENTACAO, "
                + "F.FORNNOME, P.ESTOQUE, P.PROD_PRVENDA, "
                + "P.PROD_PRPROMOCAO, P.DESCONTO_MAXIMO "
                + "FROM TBLPRODUTOS P INNER JOIN of_cliente_juridico F ON F.ID = P.FORNID "
                + "ORDER BY PRODUTO ASC"; 
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));           
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> localizaCodigo(int produto_id){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT PRODUTO_ID, PRODUTO, APRESENTACAO, "
                + "FORNID, ESTOQUE, PROD_PRVENDA, "
                + "PROD_PRPROMOCAO, DESCONTO_MAXIMO, PROD_PRCOMPRA "
                + "FROM TBLPRODUTOS "
                + "WHERE PRODUTO_ID = ?"; 
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, produto_id);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));           
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     
     public ArrayList<Produtos> localizaNome(String produto){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT PRODUTO_ID, PRODUTO, APRESENTACAO, "
                + "FORNID, ESTOQUE, PROD_PRVENDA, "
                + "PROD_PRPROMOCAO, DESCONTO_MAXIMO, PROD_PRCOMPRA "
                + "FROM TBLPRODUTOS "
                + "WHERE PRODUTO LIKE ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, produto + "%");
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));           
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> localizaCodigoBarras(String codigo_barras){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT PRODUTO_ID, PRODUTO, APRESENTACAO, "
                + "FORNID, ESTOQUE, PROD_PRVENDA, "
                + "PROD_PRPROMOCAO, DESCONTO_MAXIMO, PROD_PRCOMPRA "
                + "FROM TBLPRODUTOS "
                + "WHERE CODIGO_BARRAS LIKE ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, codigo_barras);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));           
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> buscaSaldo(String produto){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT PRODUTO_ID, P.PRODUTO, P.APRESENTACAO, "
                + "F.CLIID, P.ESTOQUE, P.PROD_PRVENDA, "
                + "P.PROD_PRPROMOCAO, P.DESCONTO_MAXIMO "
                + "FROM TBLPRODUTOS P INNER JOIN TBLCLIENTES F ON F.CLIID = P.FORNID "
                + "WHERE P.PRODUTO LIKE ? AND P.ESTOQUE > 0";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, produto);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));           
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
     public ArrayList<Produtos> comSaldo(){
         ArrayList<Produtos> lista = new ArrayList<>();
         String sql = "SELECT PRODUTO_ID, PRODUTO, APRESENTACAO, "
                + "FORNID, ESTOQUE, PROD_PRVENDA, "
                + "PROD_PRPROMOCAO, DESCONTO_MAXIMO, PROD_PRCOMPRA "
                + "FROM TBLPRODUTOS "
                + "WHERE ESTOQUE > 0 "
                 + "ORDER BY PRODUTO ASC";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             rs = pst.executeQuery();
             while(rs.next()){
                 Produtos produtos = new Produtos();
                 produtos.setProduto_id(rs.getInt("PRODUTO_ID"));
                 produtos.setProduto(rs.getString("PRODUTO").toUpperCase());
                 produtos.setApresentacao(rs.getString("APRESENTACAO").toUpperCase());
                 produtos.setFornId(rs.getInt("FORNID"));
                 produtos.setEstoque(rs.getDouble("ESTOQUE"));
                 produtos.setDesconto_maximo(rs.getDouble("DESCONTO_MAXIMO"));           
                 produtos.setProd_prvenda(rs.getDouble("PROD_PRVENDA"));
                 produtos.setProd_prpromocao(rs.getDouble("PROD_PRPROMOCAO"));
                 produtos.setProd_prcompra(rs.getDouble("PROD_PRCOMPRA"));
                 lista.add(produtos);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }      
         return lista;
     }
     
}
