package org.salesforce.repositories;

import org.salesforce.entities.user.Atendente;
import org.salesforce.infrastructure.OracleDBConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AtendenteRepository {
    public static final String TB_NAME = "ATENDENTE";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "CPF", "CPF",
            "NOME", "NOME",
            "SOBRENOME", "SOBRENOME",
            "EMAIL", "EMAIL",
            "SENHA", "SENHA"
    );

    public void create(Atendente atendente) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
                     "INSERT INTO %s(%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("CPF"),
                                     TB_COLUMNS.get("NOME"),
                                     TB_COLUMNS.get("SOBRENOME"),
                                     TB_COLUMNS.get("EMAIL"),
                                     TB_COLUMNS.get("SENHA")))) {
            stmt.setString(1, atendente.getCpf().replaceAll("[^0-9]", ""));
            stmt.setString(2, atendente.getNome());
            stmt.setString(3, atendente.getSobrenome());
            stmt.setString(4, atendente.getEmail());
            stmt.setString(5, atendente.getSenha());
            stmt.executeUpdate();
            System.out.println("Atendente criado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Atendente> reedAll() {
        var atendentes = new ArrayList<Atendente>();
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " ORDER BY ID")) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                atendentes.add(new Atendente(
                        rs.getInt("ID"),
                        rs.getString("CPF"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atendentes;
    }

    public Optional<Atendente> findByCpf(String cpf) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE %s = ?".formatted(
                     TB_COLUMNS.get("CPF")))) {
            stmt.setString(1, cpf.replaceAll("[^0-9]", ""));
            var rs = stmt.executeQuery();
            while (rs.next()) {
                return Optional.of(new Atendente(
                        rs.getInt("ID"),
                        rs.getString("CPF"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Atendente> findById(int id) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Atendente(
                        rs.getInt("ID"),
                        rs.getString("CPF"),
                        rs.getString("NOME"),
                        rs.getString("SOBRENOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void update(int id, Atendente atendente) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("CPF"),
                                     TB_COLUMNS.get("NOME"),
                                     TB_COLUMNS.get("SOBRENOME"),
                                     TB_COLUMNS.get("EMAIL"),
                                     TB_COLUMNS.get("SENHA")))) {

            stmt.setString(1, atendente.getCpf());
            stmt.setString(2, atendente.getNome());
            stmt.setString(3, atendente.getSobrenome());
            stmt.setString(4, atendente.getEmail());
            stmt.setString(5, atendente.getSenha());
            stmt.setInt(6, id);
            stmt.executeUpdate();
            System.out.println("Atendente atualizado com sucesso!");
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
            System.out.println("Atendente deletado com sucesso!");
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
            System.out.println("Atendente deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int findIdByCpf(String cpf) {
        Optional<Atendente> atendentes = findByCpf(cpf);

        Optional<Integer> idOptional = atendentes.stream()
                .map(Atendente::getId)
                .findFirst();

        return idOptional.orElse(0);
    }
}
