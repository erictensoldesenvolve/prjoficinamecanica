package br.com.digimaxx.utilitarios;

import java.io.FileWriter;
import java.sql.*;


public class BackupJDBC {
    
    // 🔧 CONFIGURAÇÃO DO BANCO ONLINE
    
    private static final String URL =
            "jdbc:mysql://127.0.0.1:3306/"+buscaNome()+"?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    
    public static String buscaNome(){
        UsuarioSistema usuario = new UsuarioSistema();
        usuario.lerConfig();
        String database = usuario.getDatabase();
        return database;
    }

    public static void gerarBackup(String arquivo) throws Exception {

        try (
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            Statement stmt = conn.createStatement();
            FileWriter fw = new FileWriter(arquivo)
        ) {

            fw.write("SET FOREIGN_KEY_CHECKS=0;\n\n");

            ResultSet rsTables = stmt.executeQuery("SHOW TABLES");

            while (rsTables.next()) {
                String tabela = rsTables.getString(1);

                // 🔹 DROP TABLE
                fw.write("DROP TABLE IF EXISTS `" + tabela + "`;\n");

                // 🔹 CREATE TABLE
                ResultSet rsCreate = stmt.executeQuery("SHOW CREATE TABLE `" + tabela + "`");
                if (rsCreate.next()) {
                    fw.write(rsCreate.getString(2) + ";\n\n");
                }

                // 🔹 DADOS
                ResultSet rsDados = stmt.executeQuery("SELECT * FROM `" + tabela + "`");
                ResultSetMetaData meta = rsDados.getMetaData();
                int colunas = meta.getColumnCount();

                while (rsDados.next()) {
                    StringBuilder insert = new StringBuilder(
                            "INSERT INTO `" + tabela + "` VALUES ("
                    );

                    for (int i = 1; i <= colunas; i++) {
                        Object valor = rsDados.getObject(i);

                        if (valor == null) {
                            insert.append("NULL");
                        } else {
                            insert.append("'")
                                  .append(valor.toString().replace("'", "''"))
                                  .append("'");
                        }

                        if (i < colunas) insert.append(", ");
                    }

                    insert.append(");\n");
                    fw.write(insert.toString());
                }

                fw.write("\n\n");
            }

            fw.write("SET FOREIGN_KEY_CHECKS=1;\n");
        }
    }
}
