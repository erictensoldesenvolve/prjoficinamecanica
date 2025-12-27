/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.digimaxx.utilitarios;

import br.com.digimaxx.dao.ConexaoConfig;
import br.com.digimaxx.telas.Principal;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.sql.*;
import java.util.List;

/**
 *
 * @author eric
 */
public class UsuarioSistema {
    private int usuId;
    private String usuEndereco;
    private int usuNumero;
    private String usuBairro;
    private String usuCep;
    private int usuEstaId;
    private int usuCidaId;
    private Date usuDataInicio;
    private Date usuDataLicenca;
    private String usuEmail;
    private String usuNome;
    private String usuRazao;
    private String usuTipo;
    private String usuCnpj;
    private String usuInsc_estadual;
    private String usuTelefone;
    private String usuInsc_municipal;
    private String usuCompl;
    private String usuLicenca;
    private String database;

    public int getUsuId() {
        return usuId;
    }

    public void setUsuId(int usuId) {
        this.usuId = usuId;
    }

    public String getUsuEndereco() {
        return usuEndereco;
    }

    public void setUsuEndereco(String usuEndereco) {
        this.usuEndereco = usuEndereco.toUpperCase();
    }

    public int getUsuNumero() {
        return usuNumero;
    }

    public void setUsuNumero(int usuNumero) {
        this.usuNumero = usuNumero;
    }

    public String getUsuBairro() {
        return usuBairro;
    }

    public void setUsuBairro(String usuBairro) {
        this.usuBairro = usuBairro.toUpperCase();
    }

    public String getUsuCep() {
        return usuCep;
    }

    public void setUsuCep(String usuCep) {
        this.usuCep = usuCep;
    }

    public int getUsuEstaId() {
        return usuEstaId;
    }

    public void setUsuEstaId(int usuEstaId) {
        this.usuEstaId = usuEstaId;
    }

    public int getUsuCidaId() {
        return usuCidaId;
    }

    public void setUsuCidaId(int usuCidaId) {
        this.usuCidaId = usuCidaId;
    }

    public Date getUsuDataInicio() {
        return usuDataInicio;
    }

    public void setUsuDataInicio(Date usuDataInicio) {
        this.usuDataInicio = usuDataInicio;
    }

    public Date getUsuDataLicenca() {
        return usuDataLicenca;
    }

    public void setUsuDataLicenca(Date usuDataLicenca) {
        this.usuDataLicenca = usuDataLicenca;
    }

    public String getUsuEmail() {
        return usuEmail;
    }

    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail.toUpperCase();
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome.toUpperCase();
    }

    public String getUsuRazao() {
        return usuRazao;
    }

    public void setUsuRazao(String usuRazao) {
        this.usuRazao = usuRazao;
    }

    public String getUsuTipo() {
        return usuTipo;
    }

    public void setUsuTipo(String usuTipo) {
        this.usuTipo = usuTipo;
    }

    public String getUsuCnpj() {
        return usuCnpj;
    }

    public void setUsuCnpj(String usuCnpj) {
        this.usuCnpj = usuCnpj;
    }

    public String getUsuInsc_estadual() {
        return usuInsc_estadual;
    }

    public void setUsuInsc_estadual(String usuInsc_estadual) {
        this.usuInsc_estadual = usuInsc_estadual;
    }

    public String getUsuTelefone() {
        return usuTelefone;
    }

    public void setUsuTelefone(String usuTelefone) {
        this.usuTelefone = usuTelefone;
    }

    public String getUsuInsc_municipal() {
        return usuInsc_municipal;
    }

    public void setUsuInsc_municipal(String usuInsc_municipal) {
        this.usuInsc_municipal = usuInsc_municipal;
    }

    public String getUsuCompl() {
        return usuCompl;
    }

    public void setUsuCompl(String usuCompl) {
        this.usuCompl = usuCompl.toUpperCase();
    }

    public String getUsuLicenca() {
        return usuLicenca;
    }

    public void setUsuLicenca(String usuLicenca) {
        this.usuLicenca = usuLicenca;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
    
    
    
    //    SALVA VARIÁVEIS DE CONEXÃO
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
//    ALTERA DADOS DO CLIENTE
    public boolean alterar(String usuendereco, int usunumero, String usubairro, String usucep, int usuestaid, int usucidaid, 
            String usuemail, String usunome, String usurazao, String usucnpj, String usuinsc_estadual, String usutelefone, 
            String usuinsc_municipal, String usucompl, int usuid){
        boolean retorno = false;
        String sql = "UPDATE TBLUSUARIOS SET USUENDERECO = ?, USUNUMERO = ?, USUBAIRRO = ?, USUCEP = ?, USUESTAID = ?, "
                + "USUCIDAID = ?, USUEMAIL = ?, USUNOME = ?, USURAZAO = ?, USUCNPJ = ?, USUINSC_ESTADUAL = ?, USUTELEFONE = ?, "
                + "USUINSC_MUNICIPAL = ?, USUCOMPL = ? WHERE USUID = ?";
        try {
            conexao = ConexaoConfig.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, usuendereco);
            pst.setInt(2, usunumero);
            pst.setString(3, usubairro);
            pst.setString(4, usucep);
            pst.setInt(5, usuestaid);
            pst.setInt(6, usucidaid);
            pst.setString(7, usuemail);
            pst.setString(8, usunome);
            pst.setString(9, usurazao);
            pst.setString(10, usucnpj);
            pst.setString(11, usuinsc_estadual);
            pst.setString(12, usutelefone);
            pst.setString(13, usuinsc_municipal);
            pst.setString(14, usucompl);
            pst.setInt(15, usuid);
            int alterado = pst.executeUpdate();
            if(alterado > 0){ retorno = true; }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
    
    //  MÉTODO QUE BUSCA OS DADOS NO BANCO PELO CNPJ OU CPF  
    public void buscar(String cnpj){
       String sql = "SELECT * FROM TBLUSUARIOS WHERE USUCNPJ = ?";
        try {
            conexao = ConexaoConfig.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cnpj);
            rs = pst.executeQuery();
            if(rs.next()){
               setUsuId(rs.getInt("USUID"));
               setUsuEndereco(rs.getString("USUENDERECO"));
               setUsuNumero(rs.getInt("USUNUMERO"));
               setUsuBairro(rs.getString("USUBAIRRO"));
               setUsuCep(rs.getString("USUCEP"));
               setUsuEstaId(rs.getInt("USUESTAID"));
               setUsuCidaId(rs.getInt("USUCIDAID"));
               setUsuDataInicio(rs.getDate("USUDATAINICIO"));
               setUsuDataLicenca(rs.getDate("USUDATALICENCA"));
               setUsuEmail(rs.getString("USUEMAIL"));
               setUsuNome(rs.getString("USUNOME"));
               setUsuRazao(rs.getString("USURAZAO"));
               setUsuTipo(rs.getString("USUTIPO"));
               setUsuCnpj(rs.getString("USUCNPJ"));
               setUsuInsc_estadual(rs.getString("USUINSC_ESTADUAL"));
               setUsuTelefone(rs.getString("USUTELEFONE"));
               setUsuInsc_municipal(rs.getString("USUINSC_MUNICIPAL"));
               setUsuCompl(rs.getString("USUCOMPL"));
               setUsuLicenca(rs.getString("USULICENCA")); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            
    public boolean lerConfig(){
        boolean retorna = false;
        String linhaFormatada = null;
        String database = null;
        String caminhoArquivo = "config.txt"; // caminho relativo ou absoluto

        File arquivo = new File(caminhoArquivo);

        if (arquivo.exists()) {
            retorna = true;

            // Se quiser ler o conteúdo do arquivo:
            try {
                List<String> linhas = Files.readAllLines(Paths.get(caminhoArquivo));
                if(linhas.get(1).length() == 18){
                    String linhaCpf = linhas.get(1);
                    linhaFormatada = linhaCpf.replace("CPF=", "");
                    database = linhas.get(3).replace("database=","");
                    setDatabase(database);
                }else{               
                    String linhaCnpj = linhas.get(1);
                    linhaFormatada = linhaCnpj.replace("CNPJ=", ""); 
                    database = linhas.get(3).replace("database=","");
                }                
                Principal.usuCnpj = linhaFormatada;
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            }

        } else {
            System.out.println("Arquivo não encontrado.");
        }
        return retorna;
    }

    public boolean selecionaDados(String dado) {
        boolean retorno = false;
        // Remove tudo que não for número
        String numeros = dado.replaceAll("\\D", ""); // mantém só dígitos

        // Formata automaticamente
        String formatado = formataDocumento(numeros);

        String sql = "SELECT * FROM TBLUSUARIOS WHERE USUCNPJ = ?";
        try {
            conexao = ConexaoConfig.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, formatado); // usa só números para buscar
            rs = pst.executeQuery();

            if (rs.next()) {
                setUsuNome(rs.getString("USUNOME"));
                setUsuCnpj(rs.getString("USUCNPJ"));
                retorno = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
    
    public String formataDocumento(String doc) {
        if (doc.length() == 11) {
            // CPF → 000.000.000-00
            return doc.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                                    "$1.$2.$3-$4");
        } else if (doc.length() == 14) {
            // CNPJ → 00.000.000/0000-00
            return doc.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                                    "$1.$2.$3/$4-$5");
        } else {
            // formato inválido
            return doc;
        }
    }  
    
}
