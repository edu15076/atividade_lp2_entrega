package br.cefetmg.gestaoEntregas.controllers;

import java.util.List;

import br.cefetmg.gestaoEntregas.dao.ClienteDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.dao.utils.Pair;
import br.cefetmg.gestaoEntregas.entidades.Cliente;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;

public class ClienteController {
    private final ClienteDAO dao;

    public ClienteController() throws DAOException {
        dao = new ClienteDAO();
    }

    public Cliente cadastrar(String nome, String logradouro, String bairro, String telefone, String cnpj, String cpf, Empresa empresa)
            throws AtributoInvalidoException, DAOException {
        Cliente cliente = new Cliente(nome, logradouro, bairro, telefone, cnpj, cpf, empresa);

        dao.salvar(cliente);

        return cliente;
    }

    public Cliente consultarCPF(String cpf) throws DAOException {
        List<Cliente> resultadosConsulta = dao.consultarCampo(new Pair<String, String>("cpf", cpf));

        return resultadosConsulta.isEmpty() ? null : resultadosConsulta.get(0);
    }

    public Cliente consultarCNPJ(String cnpj) throws DAOException {
        List<Cliente> resultadosConsulta = dao.consultarCampo(new Pair<String, String>("cnpj", cnpj));

        return resultadosConsulta.isEmpty() ? null : resultadosConsulta.get(0);
    }
}
