package org.salesforce.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe responsável por configurar a conexão com o banco de dados Oracle.
 */

public class OracleDBConfiguration {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER = "RM552602";
    private static final String PASSWORD = "120203";

    /**
     * Obtém uma conexão com o banco de dados Oracle.
     *
     * @return Conexão com o banco de dados Oracle.
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
