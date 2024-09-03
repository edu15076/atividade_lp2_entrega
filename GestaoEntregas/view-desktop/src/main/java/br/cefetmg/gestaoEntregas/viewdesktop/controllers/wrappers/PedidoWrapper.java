package br.cefetmg.gestaoEntregas.viewdesktop.controllers.wrappers;

import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;

import java.time.LocalDate;

public class PedidoWrapper {
    private String cpf;
    private String nomeCliente;
    private Status status;
    private LocalDate data;
    private double preco;

    public PedidoWrapper(Pedido pedido) {
        status = pedido.getStatus();
        data = pedido.getData().toLocalDate();
        preco = pedido.getValorTotal();
        cpf = pedido.getCliente().getCpf();
        nomeCliente = pedido.getCliente().getNome();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
