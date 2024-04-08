package org.salesforce.repositories;

import org.salesforce.infrastructure.OracleDBConfiguration;
import org.salesforce.entities.user.Empresa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EmpresaRepository {
    public static final String TB_NAME = "EMPRESA";
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "CNPJ", "CNPJ",
            "BRASILEIRA", "BRASILEIRA",
            "SETOR", "SETOR",
            "NOME", "NOME",
            "TAMANHO", "TAMANHO"
    );

    public List<Empresa> reedAll(){
        var empresas = new ArrayList<Empresa>();
        try(var conn = new OracleDBConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID")){
            var rs = stmt.executeQuery();
            while(rs.next()){
                empresas.add(new Empresa(
                        rs.getInt("ID"),
                        rs.getString("CNPJ"),
                        rs.getString("NOME"),
                        rs.getString("TAMANHO"),
                        rs.getBoolean("BRASILEIRA"),
                        rs.getString("SETOR")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return empresas;
    }
    public Optional<Empresa> findByCnpj(String cnpj){
        try(var conn = new OracleDBConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" WHERE %s = ?".formatted(
                    TB_COLUMNS.get("CNPJ")))){
            stmt.setString(1, cnpj.replaceAll("[^0-9]", ""));
            var rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Empresa(
                        rs.getInt("ID"),
                        rs.getString("CNPJ"),
                        rs.getString("NOME"),
                        rs.getString("TAMANHO"),
                        rs.getBoolean("BRASILEIRA"),
                        rs.getString("SETOR")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Empresa> findById(int id){
        try(var conn = new OracleDBConfiguration().getConnection();
            var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ){
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Empresa(
                        rs.getInt("ID"),
                        rs.getString("CNPJ"),
                        rs.getString("NOME"),
                        rs.getString("TAMANHO"),
                        rs.getBoolean("BRASILEIRA"),
                        rs.getString("SETOR")));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void create(Empresa empresa){
        try(var conn = new OracleDBConfiguration().getConnection();
            var stmt = conn.prepareStatement(
                    "INSERT INTO %s(%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
                            .formatted(TB_NAME,
                                    TB_COLUMNS.get("CNPJ"),
                                    TB_COLUMNS.get("BRASILEIRA"),
                                    TB_COLUMNS.get("SETOR"),
                                    TB_COLUMNS.get("NOME"),
                                    TB_COLUMNS.get("TAMANHO")))) {
            stmt.setString(1, empresa.getCnpj().replaceAll("[^0-9]", ""));
            stmt.setBoolean(2, empresa.isBrasileira());
            stmt.setString(3, empresa.getSetor());
            stmt.setString(4, empresa.getNome());
            stmt.setString(5, empresa.getTamanho());
            stmt.executeUpdate();
            System.out.println("Empresa criada com sucesso!");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void update(int id, Empresa empresa) {
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("CNPJ"),
                                     TB_COLUMNS.get("BRASILEIRA"),
                                     TB_COLUMNS.get("SETOR"),
                                     TB_COLUMNS.get("NOME"),
                                     TB_COLUMNS.get("TAMANHO")))) {

            stmt.setString(1, empresa.getCnpj());
            stmt.setBoolean(2, empresa.isBrasileira());
            stmt.setString(3, empresa.getSetor());
            stmt.setString(4, empresa.getNome());
            stmt.setString(5, empresa.getTamanho());
            stmt.setInt(6, id);
            stmt.executeUpdate();
            System.out.println("Empresa atualizada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByCnpj(String cnpj){
        try (var conn = new OracleDBConfiguration().getConnection();
             var stmt = conn.prepareStatement("DELETE FROM %s WHERE %s = ?"
                     .formatted(TB_NAME,
                             TB_COLUMNS.get("CNPJ")))) {
            stmt.setString(1, cnpj.replaceAll("[^0-9]", ""));
            stmt.executeUpdate();
            System.out.println("Empresa deletada com sucesso!");
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
            System.out.println("Empresa deletada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
