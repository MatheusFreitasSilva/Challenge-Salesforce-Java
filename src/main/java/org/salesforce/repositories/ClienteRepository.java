package org.salesforce.repositories;

import org.salesforce.infrastructure.OracleDBConfiguration;
import org.salesforce.entities.user.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Classe que manipula o repositório de cliente.
 */
public class ClienteRepository extends _BaseRepository implements _Logger<ClienteRepository> {
    public static final String TB_NAME = "CLIENTE";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "CPF", "CPF",
            "NOME", "NOME",
            "SOBRENOME", "SOBRENOME",
            "EMAIL", "EMAIL",
            "EMPRESA_ID", "EMPRESA_ID",
            "SENHA", "SENHA"
    );

    /**
     * Adiciona um cliente ao banco de dados.
     * @param cliente Objeto cliente.
     */
    public void create(Cliente cliente) {

        try (var stmt = conn.prepareStatement(
                     "INSERT INTO %s(%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("CPF"),
                                     TB_COLUMNS.get("NOME"),
                                     TB_COLUMNS.get("SOBRENOME"),
                                     TB_COLUMNS.get("EMAIL"),
                                     TB_COLUMNS.get("EMPRESA_ID"),
                                     TB_COLUMNS.get("SENHA")))) {
            stmt.setString(1, cliente.getCpf().replaceAll("[^0-9]", ""));
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getSobrenome());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, cliente.getEmpresa_id());
            stmt.setString(6, cliente.getSenha());
            stmt.executeUpdate();
            logInfo("Cliente adicionado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao adicionar cliente: ");
            e.printStackTrace();
        }
    }

    /**
     * Lê o banco de dados e retorna uma lista das informações de clientes, sem parâmetros de busca.
     * @return Lista de clientes
     */
    public List<Cliente> reedAll() {
        var clientes = new ArrayList<Cliente>();
        try (var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY ID")) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("ID"),
                        rs.getString("CPF"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getInt("EMPRESA_ID")));
            }
        } catch (Exception e) {
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }
        return clientes;
    }

    /**
     * Lê o banco de dados na tabela cliente, por parâmetro.
     * @param cpf CPF do cliente.
     * @return Objeto optional cliente.
     */
    public Optional<Cliente> findByCpf(String cpf) {
        try (var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE %s = ?".formatted(
                     TB_COLUMNS.get("CPF")))) {
            stmt.setString(1, cpf.replaceAll("[^0-9]", ""));
            var rs = stmt.executeQuery();
            while (rs.next()) {
                return Optional.of(new Cliente(
                        rs.getInt("ID"),
                        rs.getString("CPF"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getInt("EMPRESA_ID")));
            }
        } catch (Exception e) {
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Lê o banco de dados na tabela cliente, por parâmetro.
     * @param id ID do cliente.
     * @return Objeto optional cliente.
     */
    public Optional<Cliente> findById(int id) {
        try (var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Cliente(
                        rs.getInt("ID"),
                        rs.getString("CPF"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getInt("EMPRESA_ID")));
            }
        } catch (Exception e) {
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Atualiza uma informação no banco de dados.
     * @param id ID do cliente.
     */
    public void update(int id, Cliente cliente) {
        try (var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("CPF"),
                                     TB_COLUMNS.get("NOME"),
                                     TB_COLUMNS.get("SOBRENOME"),
                                     TB_COLUMNS.get("EMAIL"),
                                     TB_COLUMNS.get("SENHA")))) {

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getSobrenome());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenha());
            stmt.setInt(6, id);
            stmt.executeUpdate();
            logInfo("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao atualizar cliente: ");
            e.printStackTrace();
        }
    }

    /**
     * Atualiza uma informação no banco de dados, por parâmetro.
     * @param id ID do cliente.
     * @param cliente Objeto cliente.
     */
    public void updateIdEmpresa(int id, Cliente cliente) {
        try (var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("EMPRESA_ID")))) {

            stmt.setInt(1, cliente.getEmpresa_id());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            logInfo("ID da Empresa atualizado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao atualizar ID da Empresa");
            e.printStackTrace();
        }
    }

    /**
     * Deleta uma informação no banco de dados, por parâmetro.
     * @param cpf CPF do cliente.
     */
    public void deleteByCpf(String cpf){
        try (var stmt = conn.prepareStatement("DELETE FROM %s WHERE %s = ?"
                     .formatted(TB_NAME,
                             TB_COLUMNS.get("CPF")))) {
            stmt.setString(1, cpf.replaceAll("[^0-9]", ""));
            stmt.executeUpdate();
            logInfo("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao deletar cliente: ");
            e.printStackTrace();
        }
    }

    /**
     * Deleta uma informação no banco de dados, por parâmetro.
     * @param id ID do cliente.
     */
    public void deleteById(int id){
        try (var stmt = conn.prepareStatement("DELETE FROM %s WHERE ID = ?"
                     .formatted(TB_NAME))) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logInfo("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao deletar cliente: ");
            e.printStackTrace();
        }
    }

    /**
     * Busca o ID de um cliente através do CPF.
     * @param cpf CPF do cliente.
     * @return Optional integer id.
     */
    public int findIdByCpf(String cpf) {
        Optional<Cliente> clientes = findByCpf(cpf);

        Optional<Integer> idOptional = clientes.stream()
                .map(Cliente::getId)
                .findFirst();

        return idOptional.orElse(0);
    }
}
