package org.salesforce.repositories;

import org.salesforce.entities.user.Cliente;
import org.salesforce.entities.user.Empresa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Classe que manipula o repositório de Empresa.
 */
public class EmpresaRepository extends _BaseRepository implements _Logger<EmpresaRepository> {

    /** Nome da tabela de clientes */
    public static final String TB_NAME = "EMPRESA";

    /** Mapeamento das colunas da tabela de clientes */
    public static final Map<String, String> TB_COLUMNS = Map.of(
            "CNPJ", "CNPJ",
            "NACIONAL_BOOL", "NACIONAL_BOOL",
            "SETOR", "SETOR",
            "NOME", "NOME",
            "TAMANHO", "TAMANHO"
    );

    /**
     * Lê o banco de dados e retorna uma lista das informações de empresas, sem parâmetros de busca.
     * @return Lista de empresas
     */
    public List<Empresa> reedAll(){
        var empresas = new ArrayList<Empresa>();
        try(var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" ORDER BY ID")){
            var rs = stmt.executeQuery();
            while(rs.next()){
                empresas.add(new Empresa(
                        rs.getInt("ID"),
                        rs.getString("CNPJ"),
                        rs.getString("NOME"),
                        rs.getString("TAMANHO"),
                        rs.getBoolean("NACIONAL_BOOL"),
                        rs.getString("SETOR")));
            }
        }
        catch (Exception e){
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }
        return empresas;
    }

    /**
     * Lê o banco de dados na tabela cliente, por parâmetro.
     * @param cnpj CNPJ da empresa.
     * @return Objeto optional Empresa.
     */
    public Optional<Empresa> findByCnpj(String cnpj){
        try(var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME +" WHERE %s = ?".formatted(
                    TB_COLUMNS.get("CNPJ")))){
            stmt.setString(1, cnpj.replaceAll("[^0-9]", ""));
            var rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Empresa(
                        rs.getInt("ID"),
                        rs.getString("CNPJ"),
                        rs.getString("NOME"),
                        rs.getString("TAMANHO"),
                        rs.getBoolean("NACIONAL_BOOL"),
                        rs.getString("SETOR")));
            }
        }
        catch (Exception e){
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Lê o banco de dados na tabela cliente, por parâmetro.
     * @param id ID da empresa.
     * @return Objeto optional Empresa.
     */
    public Optional<Empresa> findById(int id){
        try(var stmt = conn.prepareStatement("SELECT * FROM " + TB_NAME + " WHERE ID = ?")
        ){
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if(rs.next()){
                return Optional.of(new Empresa(
                        rs.getInt("ID"),
                        rs.getString("CNPJ"),
                        rs.getString("NOME"),
                        rs.getString("TAMANHO"),
                        rs.getBoolean("NACIONAL_BOOL"),
                        rs.getString("SETOR")));
            }
        }
        catch (Exception e){
            logError("Erro ao filtrar Banco de Dados: ");
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Adiciona uma empresa ao banco de dados.
     * @param empresa Objeto empresa.
     */
    public void create(Empresa empresa){
        try(var stmt = conn.prepareStatement(
                    "INSERT INTO %s(%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)"
                            .formatted(TB_NAME,
                                    TB_COLUMNS.get("CNPJ"),
                                    TB_COLUMNS.get("NACIONAL_BOOL"),
                                    TB_COLUMNS.get("SETOR"),
                                    TB_COLUMNS.get("NOME"),
                                    TB_COLUMNS.get("TAMANHO")))) {
            stmt.setString(1, empresa.getCnpj().replaceAll("[^0-9]", ""));
            stmt.setBoolean(2, empresa.isBrasileira());
            stmt.setString(3, empresa.getSetor());
            stmt.setString(4, empresa.getNome());
            stmt.setString(5, empresa.getTamanho());
            stmt.executeUpdate();
            logInfo("Empresa adicionada com sucesso!");
        }
        catch (SQLException e){
            logError("Erro ao adicionar empresa: ");
            e.printStackTrace();
        }
    }

    /**
     * Atualiza uma informação no banco de dados, por parâmetro.
     * @param id ID da empresa.
     * @param empresa Objeto empresa.
     */
    public void update(int id, Empresa empresa) {
        try (var stmt = conn.prepareStatement(
                     "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE ID = ?"
                             .formatted(TB_NAME,
                                     TB_COLUMNS.get("CNPJ"),
                                     TB_COLUMNS.get("NACIONAL_BOOL"),
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
            logInfo("Empresa atualizada com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao atualizar empresa: ");
            e.printStackTrace();
        }
    }

    /**
     * Deleta uma informação no banco de dados, por parâmetro.
     * @param cnpj CNPJ da empresa.
     */
    public void deleteByCnpj(String cnpj){
        try (var stmt = conn.prepareStatement("DELETE FROM %s WHERE %s = ?"
                     .formatted(TB_NAME,
                             TB_COLUMNS.get("CNPJ")))) {
            stmt.setString(1, cnpj.replaceAll("[^0-9]", ""));
            stmt.executeUpdate();
            logInfo("Empresa deletada com sucesso!");
        } catch (SQLException e) {
            logError("Ero ao deletar Empresa: ");
            e.printStackTrace();
        }
    }

    /**
     * Deleta uma informação no banco de dados, por parâmetro.
     * @param id ID da empresa.
     */
    public void deleteById(int id){
        try (var stmt = conn.prepareStatement("DELETE FROM %s WHERE ID = ?"
                     .formatted(TB_NAME))) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            logInfo("Empresa deletada com sucesso!");
        } catch (SQLException e) {
            logError("Erro ao deletar empresa: ");
            e.printStackTrace();
        }
    }

    /**
     * Obtém o ID de uma empresa pelo seu CNPJ.
     *
     * @param cnpj O CNPJ da empresa.
     * @return Resposta com o ID da empresa no formato JSON se encontrado, caso contrário, retorna NOT_FOUND.
     */
    public int findIdByCnpj(String cnpj) {
        Optional<Empresa> empresas = findByCnpj(cnpj);

        Optional<Integer> idOptional = empresas.stream()
                .map(Empresa::getId)
                .findFirst();

        return idOptional.orElse(0);
    }
}
