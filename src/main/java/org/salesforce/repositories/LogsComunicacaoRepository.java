package org.salesforce.repositories;

import org.salesforce.entities.logs.LogsComunicacao;
import org.salesforce.infrastructure.OracleDBConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Optional;

public class LogsComunicacaoRepository extends _BaseRepository implements _Logger<LogsComunicacaoRepository> {
    public static final String TB_NAME = "LOGS_COMUNICACAO";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "DATA_HORA_CONEXAO", "DATA_HORA_CONEXAO",
            "DATA_HORA_DESCONEXAO", "DATA_HORA_DESCONEXAO",
            "DESCRICAO", "DESCRICAO",
            "STATUS", "STATUS",
            "ASSUNTO", "ASSUNTO",
            "IP_USADO", "IP_USADO",
            "CLIENTE_ID", "CLIENTE_ID",
            "ATENDENTE_ID", "ATENDENTE_ID"
    );

    public void create(LogsComunicacao logsComunicacao){
        try (var stmt = conn.prepareStatement(
                     "INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("IP_USADO"),
                                     TB_COLUMNS.get("DATA_HORA_CONEXAO"),
                                     TB_COLUMNS.get("DATA_HORA_DESCONEXAO"),
                                     TB_COLUMNS.get("DESCRICAO"),
                                     TB_COLUMNS.get("ASSUNTO"),
                                     TB_COLUMNS.get("STATUS"),
                                     TB_COLUMNS.get("CLIENTE_ID"),
                                     TB_COLUMNS.get("ATENDENTE_ID")))) {
            stmt.setString(1, logsComunicacao.getIpUsado());
            stmt.setString(2, logsComunicacao.getHorarioConexao());
            stmt.setString(3, logsComunicacao.getHorarioDesconexao());
            stmt.setString(4, logsComunicacao.getDescricao());
            stmt.setString(5, logsComunicacao.getAssunto());
            stmt.setString(6, logsComunicacao.getStatus());
            stmt.setInt(7, logsComunicacao.getCliente_id());
            stmt.setInt(8, logsComunicacao.getAtendente_id());
            stmt.executeUpdate();
            logInfo("Logs de Comunicação adicionado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao adicionar ao Banco de Dados: ");
            e.printStackTrace();
        }
    }

    public List<LogsComunicacao> readAll(){
        var logs = new ArrayList<LogsComunicacao>();
        try (var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY ID")) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new LogsComunicacao(
                        rs.getInt("ID"),
                        rs.getString("DATA_HORA_CONEXAO"),
                        rs.getString("DATA_HORA_DESCONEXAO"),
                        rs.getString("DESCRICAO"),
                        rs.getString("STATUS"),
                        rs.getString("ASSUNTO"),
                        rs.getString("IP_USADO"),
                        rs.getInt("CLIENTE_ID"),
                        rs.getInt("ATENDENTE_ID")));
            }
        } catch (Exception e) {
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }
        return logs;
    }

    public void update(int id, LogsComunicacao logsComunicacao){
        try (var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("IP_USADO"),
                                     TB_COLUMNS.get("DATA_HORA_CONEXAO"),
                                     TB_COLUMNS.get("DATA_HORA_DESCONEXAO"),
                                     TB_COLUMNS.get("DESCRICAO"),
                                     TB_COLUMNS.get("ASSUNTO"),
                                     TB_COLUMNS.get("STATUS"),
                                     TB_COLUMNS.get("CLIENTE_ID"),
                                     TB_COLUMNS.get("ATENDENTE_ID")))) {

            stmt.setString(1, logsComunicacao.getIpUsado());
            stmt.setString(2, logsComunicacao.getHorarioConexao());
            stmt.setString(3, logsComunicacao.getHorarioDesconexao());
            stmt.setString(4, logsComunicacao.getDescricao());
            stmt.setString(5, logsComunicacao.getAssunto());
            stmt.setString(6, logsComunicacao.getAssunto());
            stmt.setInt(7, logsComunicacao.getCliente_id());
            stmt.setInt(8, logsComunicacao.getAtendente_id());
            stmt.setInt(9, id);
            stmt.executeUpdate();
            logInfo("Log de comunicação atualizado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao atualizar log de comunicação: ");
            e.printStackTrace();
        }
    }

    public void deleteById(int id){
        try (var stmt = conn.prepareStatement("DELETE FROM %s WHERE ID = ?"
                     .formatted(TB_NAME))) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logInfo("Log de comunicação deletado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao deletar log de comunicação: ");
            e.printStackTrace();
        }
    }

    public Optional<LogsComunicacao> findById(int id) {
        try (var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new LogsComunicacao(
                        rs.getInt("ID"),
                        rs.getString("DATA_HORA_CONEXAO"),
                        rs.getString("DATA_HORA_DESCONEXAO"),
                        rs.getString("DESCRICAO"),
                        rs.getString("STATUS"),
                        rs.getString("ASSUNTO"),
                        rs.getString("IP_USADO"),
                        rs.getInt("CLIENTE_ID"),
                        rs.getInt("ATENDENTE_ID")));
            }
        } catch (Exception e) {
            logError("Erro ao filtrar banco de dados: ");
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
