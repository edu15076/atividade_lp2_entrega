package br.cefetmg.gestaoEntregas.viewdesktop.controllers.wrappers;

import br.cefetmg.gestaoEntregas.entidades.Entregador;
import br.cefetmg.gestaoEntregas.entidades.ItemPedido;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoWrapper {
    private String codigo;
    private String nomeCliente;
    private Status status;
    private String endereco;
    private LocalDate data;
    private double preco;
    private String observacoes;
    private List<ItemPedido> itens;
    private String nomeEntregador;
    private Pedido pedido;

    public PedidoWrapper(Pedido pedido) {
        status = pedido.getStatus();
        data = pedido.getData().toLocalDate();
        preco = pedido.getValorTotal();
        codigo = pedido.getCliente().getCodigo();
        nomeCliente = pedido.getCliente().getNome();
        observacoes = pedido.getObservacoes();
        endereco = pedido.getCliente().getLogradouro() + ", " + pedido.getCliente().getBairro();
        itens = pedido.getItensPedido();

        Entregador entregador = pedido.getEntregador();
        if(entregador != null)
            nomeEntregador = pedido.getEntregador().getFuncionario().getNome();
        else
            nomeEntregador = "";

        this.pedido = pedido;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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

    public String getNomeEntregador() {
        return nomeEntregador;
    }

    public void setNomeEntregador(String nomeEntregador) {
        this.nomeEntregador = nomeEntregador;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
