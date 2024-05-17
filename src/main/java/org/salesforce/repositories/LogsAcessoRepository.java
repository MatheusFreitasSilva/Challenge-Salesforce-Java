package org.salesforce.repositories;

import org.salesforce.entities.logs.LogsAcesso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Classe que manipula o repositório de Logs de Acesso.
 */
public class LogsAcessoRepository extends _BaseRepository implements _Logger<LogsAcessoRepository> {

    /** Nome da tabela de clientes */
    public static final String TB_NAME = "LOGS_ACESSO";

    /** Mapeamento das colunas da tabela de clientes */
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "DATA_HORA_CONEXAO", "DATA_HORA_CONEXAO",
            "DATA_HORA_DESCONEXAO", "DATA_HORA_DESCONEXAO",
            "IP_USADO", "IP_USADO",
            "CLIENTE_ID", "CLIENTE_ID"
    );

    /**
     * Adiciona um Logs de Acesso ao banco de dados.
     * @param logsAcesso Objeto LogsAcesso.
     */
    public void create(LogsAcesso logsAcesso){
        try (var stmt = conn.prepareStatement(
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
            logInfo("Logs de Acesso adicionado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao adicionar ao Banco de Dados: ");
            e.printStackTrace();
        }
    }

    /**
     * Lê o banco de dados e retorna uma lista das informações de Logs de Acesso, sem parâmetros de busca.
     * @return Lista de Logs de Acesso
     */
    public List<LogsAcesso> readAll(){
        var logs = new ArrayList<LogsAcesso>();
        try (var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY ID")) {
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
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * Atualiza uma informação no banco de dados, por parâmetro.
     * @param id ID do Log.
     * @param logsAcesso Objeto LogsAcesso.
     */
    public void update(int id, LogsAcesso logsAcesso){
        try (var stmt = conn.prepareStatement(
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
            logInfo("Log de acesso atualizado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao atualizar log de acesso: ");
            e.printStackTrace();
        }
    }

    /**
     * Deleta uma informação no banco de dados, por parâmetro.
     * @param id ID do Log.
     */
    public void deleteById(int id){
        try (var stmt = conn.prepareStatement("DELETE FROM %s WHERE ID = ?"
                     .formatted(TB_NAME))) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logInfo("Log de acesso deletado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao deletar Log de acesso: ");
            e.printStackTrace();
        }
    }

    /**
     * Lê o banco de dados na tabela Logs de Acesso, por parâmetro.
     * @param id ID do Log.
     * @return Objeto optional LogsAcesso.
     */
    public Optional<LogsAcesso> findById(int id) {
        try (var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
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
            logError("Erro ao filtrar banco de dados: ");
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
