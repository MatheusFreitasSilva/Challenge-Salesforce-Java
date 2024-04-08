package org.salesforce.entities.user;

import org.salesforce.entities._BaseEntity;

public class Empresa extends _BaseEntity {
    private String cnpj;
    private String nome;
    private String tamanho;
    private boolean brasileira;
    private String setor;

    public Empresa() {}

    public Empresa(String cnpj, String nome, String tamanho, boolean brasileira, String setor) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.tamanho = tamanho;
        this.brasileira = brasileira;
        this.setor = setor;
    }

    public Empresa(int id, String cnpj, String nome, String tamanho, boolean brasileira, String setor) {
        super(id);
        this.cnpj = cnpj;
        this.nome = nome;
        this.tamanho = tamanho;
        this.brasileira = brasileira;
        this.setor = setor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isBrasileira() {
        return brasileira;
    }

    public void setBrasileira(boolean brasileira) {
        this.brasileira = brasileira;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id='" + getId() + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", brasileira=" + brasileira +
                ", setor='" + setor + '\'' +
                "} ";
    }
}
