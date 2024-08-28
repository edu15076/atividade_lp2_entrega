package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.AtendenteDAO;
import br.cefetmg.gestaoEntregas.dao.PerfilDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.*;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import jakarta.persistence.Entity;

public class PerfilController {
    private final PerfilDAO dao;

    public PerfilController() throws DAOException {
        this.dao = new PerfilDAO();
    }

    public Perfil criar(Funcionario funcionario, TipoPerfil tipoPerfil) throws DAOException {
        Perfil perfil = switch (tipoPerfil) {
            case ATENDENTE -> new Atendente();
            case ENTREGADOR -> new Entregador();
            case ADMINISTRADOR -> new Administrador();
        };

        perfil.setTipoPerfil(tipoPerfil);
        perfil.setFuncionario(funcionario);

        dao.salvar(perfil);

        return perfil;
    }

    public Perfil criar(String nome, String senha, String telefone, Empresa empresa, TipoPerfil tipoPerfil) throws DAOException, AtributoInvalidoException {
        FuncionarioController funcionarioController = new FuncionarioController();
        Funcionario funcionario = funcionarioController.cadastrar(nome, senha, telefone, empresa);
        return this.criar(funcionario, tipoPerfil);
    }


}
