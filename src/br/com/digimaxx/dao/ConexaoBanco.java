package br.com.digimaxx.dao;

import br.com.digimaxx.utilitarios.UsuarioSistema;
import java.sql.*;

public class ConexaoBanco {
    UsuarioSistema usuario = new UsuarioSistema();
    public static String nomeBanco;
    public static Connection conectar() {
    
        java.sql.Connection conexao = null;
        //a linha abaixo chama o driver importado para a biblioteca
        String driver = "com.mysql.jdbc.Driver";
        //armazena informacoes sobre o banco de dados
        String url = "jdbc:mysql://127.0.0.1:3306/"+nomeBanco;
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
