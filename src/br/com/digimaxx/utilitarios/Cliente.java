package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoBanco;
import java.sql.*;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException; 
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int id;
    private String nome;
    private String razao;
    private String endereco;
    private int numero;
    private String bairro;
    private String cep;
    private int estado;
    private int cidade;
    private String sexo;
    private Date dataNascimento;
    private String rg;
    private String cpf;
    private String dataIn;
    private String dataFim;
    private String email;
    private String nomeFantazia;
    private String tipo;
    private String cnpj;
    private String inscricaoEstadual;
    private String telefone;
    private String inscricaoMunicipal;
    private String compl;
    private int licenca;
    private String senha;
    private String login;
    private String usuAtivo;
    
    /*CRIAR CONEXÃO COM O BANCO DE DADOS*/
    static Connection conexao = null;
    static PreparedStatement pst = null;
    static ResultSet rs = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getCidade() {
        return cidade;
    }

    public void setCidade(int cidade) {
        this.cidade = cidade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataIn() {
        return dataIn;
    }

    public void setDataIn(String dataIn) {
        this.dataIn = dataIn;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeFantazia() {
        return nomeFantazia;
    }

    public void setNomeFantazia(String nomeFantazia) {
        this.nomeFantazia = nomeFantazia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getCompl() {
        return compl;
    }

    public void setCompl(String compl) {
        this.compl = compl;
    }

    public int getLicenca() {
        return licenca;
    }

    public void setLicenca(int licenca) {
        this.licenca = licenca;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUsuAtivo() {
        return usuAtivo;
    }

    public void setUsuAtivo(String usuAtivo) {
        this.usuAtivo = usuAtivo;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public void criarTabela(){
        try {
            conexao = ConexaoBanco.conectar();
            Statement stmt = conexao.createStatement();

            // Verificação da existência da tabela
            String sqlVerifica = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TBLCLIENTES'";
            ResultSet rsVerifica = stmt.executeQuery(sqlVerifica);

            if (!rsVerifica.next()) {
                // Criação da tabela se não existir
                String sqlCria = 
                    "CREATE TABLE IF NOT EXISTS TBLCLIENTES (" +
                        "CLIID INT AUTO_INCREMENT PRIMARY KEY," +
                        "CLIENDERECO VARCHAR(255)," +
                        "CLINUMERO INT ," +
                        "CLIBAIRRO VARCHAR(255)," +
                        "CLICEP VARCHAR(255)," +
                        "CLIESTAID INT NOT NULL," +
                        "CLICIDAID INT NOT NULL," +
                        "CLIDATAINICIO timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +                        
                        "CLIEMAIL VARCHAR(255) NOT NULL," +
                        "CLINOME VARCHAR(255) NOT NULL," +
                        "CLIRAZAO VARCHAR(255)," +
                        "CLITIPO CHAR(2)," +
                        "CLICNPJ VARCHAR(255) NOT NULL," +
                        "CLIINSC_ESTADUAL VARCHAR(255)," +
                        "CLITELEFONE VARCHAR(255) NOT NULL," +
                        "CLIINSC_MUNICIPAL VARCHAR(255)," +
                        "CLICOMPL VARCHAR(255), "
                        + "CLIDATANASC DATE, "
                        + "CLISEXO CHAR(1), "
                        + "CLIRG VARCHAR(20) " +
                    ")";

                 stmt.executeUpdate(sqlCria);   
                 stmt.close();
                 // Criando arquivo de log
                 FileWriter log = new FileWriter("log_criacao_tabela.txt");
                 log.write("Tabela tblUsuario criada com sucesso em " + java.time.LocalDateTime.now());
                 log.close();
            } 
               
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar/criar tabela: " + e.getMessage());
            e.printStackTrace();
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar arquivo de log: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public boolean cadastrar(String usuEndereco, int usuNumero, String usuBairro, String usuCep, int usuIdEstado, 
             int usuIdCidade, String usuEmail, String usuNomeFantazia, String usuRazao, String usuTipo, String usuCnpj, 
             String usuInscEstadual, String usuTelefone, String usuInscMunicipal, String usuCompl, String dataNascimento, String clisexo, String rg){
        boolean resposta = false;
         String sql = "INSERT INTO TBLCLIENTES "
                + "(CLIENDERECO, CLINUMERO, CLIBAIRRO, CLICEP, CLIESTAID, " +
                   "CLICIDAID, CLIEMAIL, CLINOME, CLIRAZAO, CLITIPO, " +
                   "CLICNPJ, CLIINSC_ESTADUAL, CLITELEFONE, CLIINSC_MUNICIPAL, CLICOMPL, CLIDATANASC, CLISEXO, CLIRG )"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usuEndereco);
            pst.setInt(2, usuNumero);
            pst.setString(3, usuBairro);
            pst.setString(4, usuCep);
            pst.setInt(5, usuIdEstado);
            pst.setInt(6, usuIdCidade);
            pst.setString(7, usuEmail);
            pst.setString(8, usuNomeFantazia);
            pst.setString(9, usuRazao);
            pst.setString(10, usuTipo);
            pst.setString(11, usuCnpj);
            pst.setString(12, usuInscEstadual);
            pst.setString(13, usuTelefone);
            pst.setString(14, usuInscMunicipal);
            pst.setString(15, usuCompl);
            pst.setString(16, dataNascimento);
            pst.setString(17, clisexo);
            pst.setString(18, rg);
         
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){                
              resposta = true;  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resposta;
    }
     
     public boolean lerConfig(){
        boolean retorna = false;
        String caminhoArquivo = "config.txt"; // caminho relativo ou absoluto

        File arquivo = new File(caminhoArquivo);

        if (arquivo.exists()) {
            retorna = true;

            // Se quiser ler o conteúdo do arquivo:
            try {
                List<String> linhas = Files.readAllLines(Paths.get(caminhoArquivo));
                for (String linha : linhas) {
                    System.out.println(linha);
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            }

        } else {
            System.out.println("Arquivo não encontrado.");
        }
        return retorna;
    }
     
     public boolean verificar(String cnpj){
       boolean retorno = false;
       String sql = "SELECT * FROM TBLUSUARIOS WHERE USUCNPJ = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, cnpj);
             rs = pst.executeQuery();
             if(rs.next()){ retorno = true; }
         } catch (Exception e) {
             e.printStackTrace();
         }
       return retorno;
     }
     
     public ArrayList<Cliente> pesquisar(String nome, String tipo){
        ArrayList<Cliente> lista = new ArrayList();
        String sql = "SELECT * FROM TBLCLIENTES WHERE CLINOME LIKE ? AND CLITIPO = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, "%" + nome + "%");
             pst.setString(2, tipo);
             rs = pst.executeQuery();
             while(rs.next()){
                 Cliente clientes = new Cliente();
                 clientes.setId(rs.getInt("CLIID"));
                 clientes.setNome(rs.getString("CLINOME"));
                 clientes.setCpf(rs.getString("CLICNPJ"));
                 clientes.setTelefone(rs.getString("CLITELEFONE"));
                 clientes.setTipo(rs.getString("CLITIPO"));
                 lista.add(clientes);
             }
         } catch (Exception e) {
             e.printStackTrace();
             
         }
        return lista;
     }
     
     public void pesquisarCodigoPF(int cliId){        
        String sql = "SELECT * FROM TBLCLIENTES WHERE CLIID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, cliId);
             rs = pst.executeQuery();            
             if(rs.next()){                 
                 setId(rs.getInt("CLIID"));
                 setNome(rs.getString("CLINOME").toUpperCase());                 
                 setBairro(rs.getString("CLIBAIRRO").toUpperCase());
                setCep(rs.getString("CLICEP"));
                 setCidade(rs.getInt("CLICIDAID"));
                 setEstado(rs.getInt("CLIESTAID"));
                 setCompl(rs.getString("CLICOMPL"));
                 setDataNascimento(rs.getDate("CLIDATANASC"));
                 setEmail(rs.getString("CLIEMAIL").toUpperCase());
                 setEndereco(rs.getString("CLIENDERECO").toUpperCase());
                 setNumero(rs.getInt("CLINUMERO"));
                 setRg(rs.getString("CLIRG"));
                 setSexo(rs.getString("CLISEXO"));
                 setCpf(rs.getString("CLICNPJ"));
                 setTelefone(rs.getString("CLITELEFONE"));
                 setTipo(rs.getString("CLITIPO"));   
             }
         } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Erro ao buscar cliente: "+e);
         }
       
     }
     
     public void pesquisarCodigoPJ(int cliId){        
        String sql = "SELECT * FROM TBLCLIENTES WHERE CLIID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setInt(1, cliId);
             rs = pst.executeQuery();            
             if(rs.next()){                 
                 setId(rs.getInt("CLIID"));
                 setNome(rs.getString("CLINOME").toUpperCase());
                 setRazao(rs.getString("CLIRAZAO"));
                 setBairro(rs.getString("CLIBAIRRO").toUpperCase());
                setCep(rs.getString("CLICEP"));
                 setCidade(rs.getInt("CLICIDAID"));
                 setEstado(rs.getInt("CLIESTAID"));
                 setCompl(rs.getString("CLICOMPL"));
                 setEmail(rs.getString("CLIEMAIL").toUpperCase());
                 setEndereco(rs.getString("CLIENDERECO").toUpperCase());
                 setInscricaoEstadual(rs.getString("CLIINSC_ESTADUAL"));
                 setInscricaoMunicipal(rs.getString("CLIINSC_MUNICIPAL"));
                 setNumero(rs.getInt("CLINUMERO"));
                 setCpf(rs.getString("CLICNPJ"));
                 setTelefone(rs.getString("CLITELEFONE"));
                 setTipo(rs.getString("CLITIPO"));   
             }
         } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Erro ao buscar cliente: "+e);
         }
       
     }
     
     public boolean editar(String cliEndereco, int cliNumero, String cliBairro, String cliCep, int cliEstaId, int cliCidaId, String cliEmail, 
             String cliNome, String cliRazao, String cliTipo, String cliCnpj, String cliInsc_estadual, String cliTelefone, String cliInsc_municipal, 
             String cliCompl, String cliDataNasc, String cliSexo, String cliRg, int cliId){
         boolean retorno = false;
         String sql = "UPDATE TBLCLIENTES SET CLIENDERECO = ?, CLINUMERO = ?, CLIBAIRRO = ?, CLICEP = ?, CLIESTAID = ?, CLICIDAID = ?, "
                 + "CLIEMAIL = ?, CLINOME = ?, CLIRAZAO = ?, CLITIPO = ?, CLICNPJ = ?, CLIINSC_ESTADUAL = ?, CLITELEFONE = ?, CLIINSC_MUNICIPAL = ?, "
                 + "CLICOMPL = ?, CLIDATANASC = ?, CLISEXO = ?, CLIRG = ? WHERE CLIID = ?";
         try {
             conexao = ConexaoBanco.conectar();
             pst = conexao.prepareStatement(sql);
             pst.setString(1, cliEndereco.toUpperCase());
             pst.setInt(2, cliNumero);
             pst.setString(3, cliBairro.toUpperCase());
             pst.setString(4, cliCep);
             pst.setInt(5, cliEstaId);
             pst.setInt(6, cliCidaId);
             pst.setString(7, cliEmail.toUpperCase());
             pst.setString(8, cliNome.toUpperCase());
             pst.setString(9, cliRazao);
             pst.setString(10, cliTipo);
             pst.setString(11, cliCnpj);
             pst.setString(12, cliInsc_estadual);
             pst.setString(13, cliTelefone);
             pst.setString(14, cliInsc_municipal);
             pst.setString(15, cliCompl.toUpperCase());
             pst.setString(16, cliDataNasc);
             pst.setString(17, cliSexo);
             pst.setString(18, cliRg);
             pst.setInt(19, cliId);
             int alterado = pst.executeUpdate();
             if(alterado > 0){ retorno = true; }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return retorno;
     }
     
    public boolean excluir(int cliId){
      boolean retorno = false;
      String sql = "DELETE FROM TBLCLIENTES WHERE CLIID = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, cliId);
            int excluido = pst.executeUpdate();
            if(excluido > 0){ retorno = true; }
        } catch (Exception e) {
            e.printStackTrace();
        }
      return retorno;
    }
    
    public ArrayList<Cliente> listar(){
        ArrayList<Cliente> lista = new ArrayList();
        String sql = "SELECT * FROM TBLCLIENTES WHERE CLITIPO = ? ORDER BY CLINOME ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "PJ");
            rs = pst.executeQuery();
            while(rs.next()){
                Cliente clientes = new Cliente();
                clientes.setNome(rs.getString("CLINOME").toUpperCase());
                lista.add(clientes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public ArrayList<Cliente> ordenar(){
        ArrayList<Cliente> lista = new ArrayList();
        String sql = "SELECT * FROM TBLCLIENTES ORDER BY CLINOME ASC";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                Cliente clientes = new Cliente();
                clientes.setNome(rs.getString("CLINOME").toUpperCase());
                lista.add(clientes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void pesquisaId(String nome){
        String sql = "SELECT * FROM TBLCLIENTES WHERE CLINOME = ? ";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, nome.toUpperCase());
            rs = pst.executeQuery();
            if(rs.next()){
                setId(rs.getInt("CLIID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void pesquisaNome(int cliId){
        String sql = "SELECT * FROM TBLCLIENTES WHERE CLIID = ? ";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, cliId);
            rs = pst.executeQuery();
            if(rs.next()){
                setNome(rs.getString("CLINOME").toUpperCase());
                setCpf(rs.getString("CLICNPJ"));
                setEndereco(rs.getString("CLIENDERECO").toUpperCase());
                setNumero(rs.getInt("CLINUMERO"));
                setCompl(rs.getString("CLICOMPL").toUpperCase());
                setBairro(rs.getString("CLIBAIRRO").toUpperCase());
                setTelefone(rs.getString("CLITELEFONE"));
                setEmail(rs.getString("CLIEMAIL"));
                setEstado(rs.getInt("CLIESTAID"));
                setCidade(rs.getInt("CLICIDAID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
}
