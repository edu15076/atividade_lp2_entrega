package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.EmpresaDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.dao.utils.Pair;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;

import java.util.List;

public class EmpresaController {
    private final EmpresaDAO dao;

    public EmpresaController(EmpresaDAO dao) {
        this.dao = dao;
    }

    public Empresa cadastrar(String nome, String cnpj, String cpf, Double porcentagemComissaoEntregador)
            throws AtributoInvalidoException, DAOException {
        Empresa empresa = new Empresa();
        empresa.setNome(nome);
        empresa.setCnpj(cnpj);
        empresa.setCpf(cpf);
        empresa.setPorcentagemComissaoEntregador(porcentagemComissaoEntregador);

        dao.salvar(empresa);

        return empresa;
    }

    public Empresa recuperarPorCPF(String cpf) throws DAOException {
        List<Empresa> empresaList = dao.consultarCampo(new Pair<>("cpf", cpf));

        if (empresaList.isEmpty())
            return null;

        return empresaList.get(0);
    }

    public Empresa recuperarPorCNPJ(String cnpj) throws DAOException {
        List<Empresa> empresaList = dao.consultarCampo(new Pair<>("cnpj", cnpj));

        if (empresaList.isEmpty())
            return null;

        return empresaList.get(0);
    }
}
