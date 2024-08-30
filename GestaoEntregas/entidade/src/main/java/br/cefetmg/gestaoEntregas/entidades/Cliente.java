package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String telefone;
    private String cnpj;
    private String cpf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpresa", nullable = false)
    private Empresa empresa;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Pedido> pedidos;

    public Cliente(String nome, String logradouro, String bairro, String telefone, String cnpj, String cpf, Empresa empresa) throws AtributoInvalidoException {
        if(nome.length() < 2 || !nome.matches("[a-zA-Z\\s]+"))
            throw new AtributoInvalidoException("Nome invalido, ele deve ser maior que 2 caracteres e só deve contar letras e espaços.");
        this.nome = nome;

        this.logradouro = logradouro;
        this.bairro = bairro;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.cpf = cpf;
        this.empresa = empresa;
    }

    public Cliente() {
    }

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
        if(nome.length() < 2 || !nome.matches("[a-zA-Z\\s]+"))
            throw new AtributoInvalidoException("Nome invalido, ele deve ser maior que 2 caracteres e só deve contar letras e espaços.");
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCodigo() {
        if (this.cnpj == null)
            return this.cpf;
        return this.cnpj;
    }

    public String getEndereco() {
        return this.logradouro + ", " + this.bairro;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
