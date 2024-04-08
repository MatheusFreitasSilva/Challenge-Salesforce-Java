package org.salesforce.entities.logs;

import org.salesforce.entities._BaseEntity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsComunicacao extends _BaseEntity {
    private String horarioConexao;
    private String horarioDesconexao;
    private String descricao;
    private String status;
    private String assunto;
    private String ipUsado;
    private int cliente_id;
    private int atendente_id;
    public LogsComunicacao(){}

    public LogsComunicacao(String descricao,
                           String status, String assunto, String ipUsado, int cliente_id,
                           int atendente_id) {
        this.descricao = descricao;
        this.status = status;
        this.assunto = assunto;
        this.ipUsado = ipUsado;
        this.cliente_id = cliente_id;
        this.atendente_id = atendente_id;
    }
    public LogsComunicacao(String horarioConexao, String horarioDesconexao, String descricao,
                           String status, String assunto, String ipUsado, int cliente_id,
                           int atendente_id) {
        this.horarioConexao = horarioConexao;
        this.horarioDesconexao = horarioDesconexao;
        this.descricao = descricao;
        this.status = status;
        this.assunto = assunto;
        this.ipUsado = ipUsado;
        this.cliente_id = cliente_id;
        this.atendente_id = atendente_id;
    }

    public LogsComunicacao(int id, String horarioConexao, String horarioDesconexao,
                           String descricao, String status, String assunto, String ipUsado,
                           int cliente_id, int atendente_id) {
        super(id);
        this.horarioConexao = horarioConexao;
        this.horarioDesconexao = horarioDesconexao;
        this.descricao = descricao;
        this.status = status;
        this.assunto = assunto;
        this.ipUsado = ipUsado;
        this.cliente_id = cliente_id;
        this.atendente_id = atendente_id;
    }

    public String getHorarioConexao() {
        Date dataHoraAtual = new Date();
        this.horarioConexao = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(dataHoraAtual);
        return horarioConexao;
    }

    public String getHorarioDesconexao() {
        Date dataHoraAtual = new Date();
        this.horarioDesconexao = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(dataHoraAtual);
        return horarioDesconexao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getIpUsado() {
        return ipUsado;
    }

    public void setIpUsado(String ipUsado) {
        this.ipUsado = ipUsado;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getAtendente_id() {
        return atendente_id;
    }

    public void setAtendente_id(int atendente_id) {
        this.atendente_id = atendente_id;
    }

    @Override
    public String toString() {
        return "LogsComunicacao{" +
                "id=" + getId() + '\'' +
                ", horarioConexao=" + horarioConexao +
                ", horarioDesconexao=" + horarioDesconexao +
                ", descricao='" + descricao + '\'' +
                ", status='" + status + '\'' +
                ", assunto='" + assunto + '\'' +
                ", ipUsado='" + ipUsado + '\'' +
                ", cliente_id=" + cliente_id +
                ", atendente_id=" + atendente_id +
                '}';
    }
}
