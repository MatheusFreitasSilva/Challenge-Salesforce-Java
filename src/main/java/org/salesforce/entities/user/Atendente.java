package org.salesforce.entities.user;

public class Atendente extends Usuario {
    public Atendente() {}
    public Atendente(String cpf, String nome, String sobrenome, String email, String senha) {
        super(cpf, nome, sobrenome, email, senha);
    }

    public Atendente(int id, String cpf, String nome, String sobrenome, String email, String senha) {
        super(id, cpf, nome, sobrenome, email, senha);
    }
}
