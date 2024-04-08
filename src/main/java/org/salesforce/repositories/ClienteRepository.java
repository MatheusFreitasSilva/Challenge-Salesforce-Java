package org.salesforce.repositories;

import org.salesforce.infrastructure.OracleDBConfiguration;
import org.salesforce.entities.user.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClienteRepository {
    public static final String TB_NAME = "CLIENTE";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "CPF", "CPF",
            "NOME", "NOME",
            "SOBRENOME", "SOBRENOME",
            "EMAIL", "EMAIL",
            "EMPRESA_ID", "EMPRESA_ID",
            "SENHA", "SENHA"
    );

    public void create(Cliente cliente) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
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
            System.out.println("Cliente criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> reedAll() {
        var clientes = new ArrayList<Cliente>();
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY ID")) {
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
            e.printStackTrace();
        }
        return clientes;
    }

    public Optional<Cliente> findByCpf(String cpf) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE %s = ?".formatted(
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
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Cliente> findById(int id) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
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
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void update(int id, Cliente cliente) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
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
            System.out.println("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateIdEmpresa(int id, Cliente cliente) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("EMPRESA_ID")))) {

            stmt.setInt(1, cliente.getEmpresa_id());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByCpf(String cpf){
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("DELETE FROM %s WHERE %s = ?"
                     .formatted(TB_NAME,
                             TB_COLUMNS.get("CPF")))) {
            stmt.setString(1, cpf.replaceAll("[^0-9]", ""));
            stmt.executeUpdate();
            System.out.println("Cliente deletado com sucesso!");
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
            System.out.println("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int findIdByCpf(String cpf) {
        Optional<Cliente> clientes = findByCpf(cpf);

        Optional<Integer> idOptional = clientes.stream()
                .map(Cliente::getId)
                .findFirst();

        return idOptional.orElse(0);
    }
}
