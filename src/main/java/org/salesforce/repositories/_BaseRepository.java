package org.salesforce.repositories;

import org.salesforce.infrastructure.OracleDBConfiguration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class _BaseRepository {
    public static Connection conn = new OracleDBConfiguration().getConnection();

    protected boolean tableExists(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        try (ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return resultSet.next();
        }
    }
}
