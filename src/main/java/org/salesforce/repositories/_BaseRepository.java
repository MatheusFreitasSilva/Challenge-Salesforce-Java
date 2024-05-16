package org.salesforce.repositories;

import org.salesforce.infrastructure.OracleDBConfiguration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe abstrata para configuração do banco de dados, possibilitando que as classes de repositório
 * herdem a configuração do banco de dados.
 */
public abstract class _BaseRepository {

    public static Connection conn = new OracleDBConfiguration().getConnection();

}
