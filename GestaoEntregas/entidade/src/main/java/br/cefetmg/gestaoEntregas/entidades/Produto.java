package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    private String localizacao;

    @Column(nullable = false)
    private String codigo;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "produto")
    private List<ItemPedido> itemPedido;

    @ManyToOne(fetch = FetchType.EAGER)
    private Empresa empresa;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws AtributoInvalidoException {
        if(nome.isEmpty())
            throw new AtributoInvalidoException("Nome invalido.");
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) throws AtributoInvalidoException {
        if (codigo.isEmpty())
            throw new AtributoInvalidoException("Codigo de produto deve ser preenchido");
        this.codigo = codigo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public List<ItemPedido> getItemPedido() {
        return itemPedido;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setItemPedido(List<ItemPedido> itemPedido) {
        this.itemPedido = itemPedido;
    }
}
