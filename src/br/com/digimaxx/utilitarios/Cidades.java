/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.digimaxx.utilitarios;

import java.sql.*;
import br.com.digimaxx.dao.ConexaoBanco;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author eric
 */
public class Cidades {
    private int cidaId;
    private int estaId;
    private String cidaNome;
    private String cidaDDD;
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    public Cidades(int cidaId, int estaId, String cidaNome, String cidaDDD) {
        this.cidaId = cidaId;
        this.estaId = estaId;
        this.cidaNome = cidaNome;
        this.cidaDDD = cidaDDD;
    }
    public Cidades(){
        super();
    }

    public int getCidaId() {
        return cidaId;
    }

    public void setCidaId(int cidaId) {
        this.cidaId = cidaId;
    }

    public int getEstaId() {
        return estaId;
    }

    public void setEstaId(int estaId) {
        this.estaId = estaId;
    }

    public String getCidaNome() {
        return cidaNome;
    }

    public void setCidaNome(String cidaNome) {
        this.cidaNome = cidaNome;
    }

    public String getCidaDDD() {
        return cidaDDD;
    }

    public void setCidaDDD(String cidaDDD) {
        this.cidaDDD = cidaDDD;
    }
    
    
    public boolean buscaCidades(){
    boolean retorno = false;
    String sql = "SELECT * FROM CIDADES ORDER BY CIDANOME";
    try {
        conexao = ConexaoBanco.conectar();
        pst = conexao.prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            retorno = true;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar cidades!"+e.getMessage());
    }
    return retorno;
}    

//INSERE AS CIDADES ATRAVÉS DO ARQUIVO SQL    
    public void executarArquivoSQL() {

    String caminhoResource = "br/com/digimaxx/sql/cidades.sql";

    try (Connection conn = ConexaoBanco.conectar()) {

        conn.setAutoCommit(false);

        String sqlCompleto = lerArquivoResource(caminhoResource);

        try (Statement stmt = conn.createStatement()) {

            sqlCompleto = sqlCompleto
                    .replaceAll("(?m)--.*$", "")
                    .replaceAll("(?s)/\\*.*?\\*/", "");

            String[] comandos = sqlCompleto.split(";");

            for (String comando : comandos) {
                comando = comando.trim();
                if (!comando.isEmpty()) {
                    stmt.execute(comando);
                }
            }

            conn.commit();
            System.out.println("Arquivo SQL executado com sucesso!");

        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    //LÊ O ARQUIVO SQL E PREPARA PARA INSERIR   
   private String lerArquivoResource(String caminho) throws IOException {

    InputStream is = getClass()
            .getClassLoader()
            .getResourceAsStream(caminho);

    if (is == null) {
        throw new FileNotFoundException(
                "Arquivo SQL não encontrado: " + caminho);
    }

    StringBuilder sb = new StringBuilder();

    try (BufferedReader br = new BufferedReader(
            new InputStreamReader(is, StandardCharsets.UTF_8))) {

        String linha;
        while ((linha = br.readLine()) != null) {
            sb.append(linha).append("\n");
        }
    }

    return sb.toString();
}

    public void pesquisaCidade(int cidaId){
        String sql = "SELECT * FROM cidades WHERE cidaId = "+cidaId;
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                this.setCidaNome(rs.getString("cidaNome"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void pesquisaId(String cidaNome){
        String sql = "SELECT * FROM cidades WHERE cidaNome = ?";
        try {
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, cidaNome);
            rs = pst.executeQuery();
            if(rs.next()){
                this.setCidaId(rs.getInt("cidaId"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    // Método que retorna uma lista de nomes de cidades por UF (JDBC)
    public List<String> buscarCidadesPorEstado(int estaId) {
        List<String> cidades = new ArrayList<>();
        String sql = "SELECT cidaNome FROM cidades WHERE estaId = ? ORDER BY cidaNome";
        
        try{
            conexao = ConexaoBanco.conectar();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, estaId);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                cidades.add(rs.getString("cidaNome"));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao buscar cidades: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return cidades;
    }
    
  
}