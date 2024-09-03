package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.converters.StatusConverter;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date data;
    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    @Convert(converter = StatusConverter.class)
    private Status status;
    private String observacoes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEntregador")
    private Entregador entregador;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "pedido")
    private List<ItemPedido> itensPedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idEmpresa")
    private Empresa empresa;

    public Pedido() {
        valorTotal = (double) 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Status getStatus() {
        return status;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        for (ItemPedido item : itensPedido) {
            valorTotal += item.getValorTotal();
        }

        this.itensPedido = itensPedido;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void setEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
