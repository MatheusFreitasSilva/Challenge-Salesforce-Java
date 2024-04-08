package org.salesforce.entities.user;

public class Cliente extends Usuario {
    private int empresa_id;

    public Cliente() {
    }
    public Cliente(String cpf, String nome, String sobrenome, String email, String senha, int empresa_id){
        super(cpf, nome, sobrenome, email, senha);
        this.empresa_id = empresa_id;
    }
    public Cliente(int id, String cpf, String nome, String sobrenome, String email, String senha, int empresa_id) {
        super(id, cpf, nome, sobrenome, email, senha);
        this.empresa_id = empresa_id;
    }

    public Cliente(int id, String cpf, String nome, String sobrenome, String email, String senha) {
        super(id, cpf, nome, sobrenome, email, senha);
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Cliente= " + super.toString() +
                "empresa_id=" + empresa_id +
                "} ";
    }
}
