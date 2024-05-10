package org.salesforce.entities.logs;

import org.salesforce.entities._BaseEntity;
import org.salesforce.repositories._Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsAcesso extends _BaseEntity implements _Logger<LogsAcesso> {
    private String horarioConexao;
    private String horarioDesconexao;
    private int cliente_id;
    private String ipUsado;

    public LogsAcesso() {}
    public LogsAcesso(int id, int cliente_id, String ipUsado) {
        super(id);
        this.cliente_id = cliente_id;
        this.ipUsado = ipUsado;
    }

    public LogsAcesso(int id, String horarioConexao, String horarioDesconexao, int cliente_id, String ipUsado) {
        super(id);
        this.horarioConexao = horarioConexao;
        this.horarioDesconexao = horarioDesconexao;
        this.cliente_id = cliente_id;
        this.ipUsado = ipUsado;
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

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getIpUsado() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            logError("Erro ao obter IP: ");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "LogsAcesso{" +
                "id=" + getId() + '\'' +
                ", horarioConexao=" + horarioConexao +
                ", horarioDesconexao=" + horarioDesconexao +
                ", cliente_id=" + cliente_id +
                ", ipUsado='" + ipUsado + '\'' +
                "} ";
    }
}
