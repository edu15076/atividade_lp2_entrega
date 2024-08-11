package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.AtendenteDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Atendente;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;

public class AtendenteController {
    private final AtendenteDAO dao;

    public AtendenteController() throws DAOException {
        this.dao = new AtendenteDAO();
    }

    public Atendente criar(Funcionario funcionario) throws DAOException {
        Atendente atendente = new Atendente();
        atendente.setFuncionario(funcionario);

        dao.salvar(atendente);

        return atendente;
    }

    public Atendente criar(String nome, String senha, String telefone, Empresa empresa) throws DAOException, AtributoInvalidoException {
        FuncionarioController funcionarioController = new FuncionarioController();

        Funcionario funcionario = funcionarioController.cadastrar(nome, senha, telefone, empresa);
        Atendente atendente = new Atendente();
        atendente.setFuncionario(funcionario);

        dao.salvar(atendente);

        return atendente;
    }
}
