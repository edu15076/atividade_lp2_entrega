package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cnpj;

    @Column(unique = true)
    private String cpf;
    private Double porcentagemComissaoEntregador;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cliente> clientes;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produto> produtos;

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) throws AtributoInvalidoException {
        if(cnpj.length() != 14 && !cnpj.matches("[0-9]+"))
            throw new AtributoInvalidoException("CNPJ inválido.");
        this.cnpj = cnpj;
    }

    public Double getPorcentagemComissaoEntregador() {
        return porcentagemComissaoEntregador;
    }

    public void setPorcentagemComissaoEntregador(Double porcentagemComissaoEntregador) {
        this.porcentagemComissaoEntregador = porcentagemComissaoEntregador;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws AtributoInvalidoException {
        if(cpf.length() != 11 && !cpf.matches("[0-9]+"))
            throw new AtributoInvalidoException("CPF inválido.");
        this.cpf = cpf;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Empresa) obj).getId();
    }
}
