package org.salesforce.entities.user;

import org.salesforce.entities._BaseEntity;

public class Cliente extends _BaseEntity {
    private int empresa_id;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;

    public Cliente() {
    }
    public Cliente(String cpf, String nome, String sobrenome, String email, String senha, int empresa_id){
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.empresa_id = empresa_id;
    }
    public Cliente(int id, String cpf, String nome, String sobrenome, String email, String senha, int empresa_id) {
        super(id);
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.empresa_id = empresa_id;
    }

    public Cliente(int id, String cpf, String nome, String sobrenome, String email, String senha) {
        super(id);
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() + '\'' +
                ", empresa_id=" + empresa_id +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                "} ";
    }
}
