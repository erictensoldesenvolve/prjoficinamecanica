package br.com.digimaxx.dao;

import java.sql.*;

public class ConexaoConfig {
    public static Connection conectar() {
        java.sql.Connection conexao = null;
        //a linha abaixo chama o driver importado para a biblioteca
        String driver = "com.mysql.jdbc.Driver";
        //armazena informacoes sobre o banco de dados
        String url = "jdbc:mysql://127.0.0.1:3306/bdathosx";
        //String url = "jdbc:mysql://xmysql.drogariaprecobom.com.br/drogariaprecobom";
        String user = "root";
        String password = "";        
        
        //estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
