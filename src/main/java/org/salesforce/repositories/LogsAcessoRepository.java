package org.salesforce.repositories;

import org.salesforce.entities.logs.LogsAcesso;
import org.salesforce.infrastructure.OracleDBConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LogsAcessoRepository {
    public static final String TB_NAME = "LOGS_ACESSO";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "DATA_HORA_CONEXAO", "DATA_HORA_CONEXAO",
            "DATA_HORA_DESCONEXAO", "DATA_HORA_DESCONEXAO",
            "IP_USADO", "IP_USADO",
            "CLIENTE_ID", "CLIENTE_ID"
    );

    public void create(LogsAcesso logsAcesso){
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
                     "INSERT INTO %s(%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("IP_USADO"),
                                     TB_COLUMNS.get("DATA_HORA_CONEXAO"),
                                     TB_COLUMNS.get("DATA_HORA_DESCONEXAO"),
                                     TB_COLUMNS.get("CLIENTE_ID")))) {
            stmt.setString(1, logsAcesso.getIpUsado());
            stmt.setString(2, logsAcesso.getHorarioConexao());
            stmt.setString(3, logsAcesso.getHorarioDesconexao());
            stmt.setInt(4, logsAcesso.getCliente_id());
            stmt.executeUpdate();
            System.out.println("Logs de Acesso criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LogsAcesso> readAll(){
        var logs = new ArrayList<LogsAcesso>();
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY ID")) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new LogsAcesso(
                        rs.getInt("ID"),
                        rs.getString("DATA_HORA_CONEXAO"),
                        rs.getString("DATA_HORA_DESCONEXAO"),
                        rs.getInt("CLIENTE_ID"),
                        rs.getString("IP_USADO")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    public void update(int id, LogsAcesso logsAcesso){
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("IP_USADO"),
                                     TB_COLUMNS.get("DATA_HORA_CONEXAO"),
                                     TB_COLUMNS.get("DATA_HORA_DESCONEXAO"),
                                     TB_COLUMNS.get("CLIENTE_ID")))) {

            stmt.setString(1, logsAcesso.getIpUsado());
            stmt.setString(2, logsAcesso.getHorarioConexao());
            stmt.setString(3, logsAcesso.getHorarioDesconexao());
            stmt.setInt(4, logsAcesso.getCliente_id());
            stmt.setInt(5, id);
            stmt.executeUpdate();
            System.out.println("Log de acesso atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id){
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("DELETE FROM %s WHERE ID = ?"
                     .formatted(TB_NAME))) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Log de comunicação deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Optional<LogsAcesso> findById(int id) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new LogsAcesso(
                        rs.getInt("ID"),
                        rs.getString("DATA_HORA_CONEXAO"),
                        rs.getString("DATA_HORA_DESCONEXAO"),
                        rs.getInt("CLIENTE_ID"),
                        rs.getString("IP_USADO")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
