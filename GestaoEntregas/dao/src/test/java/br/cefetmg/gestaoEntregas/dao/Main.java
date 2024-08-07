package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Administrador;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;

public class Main {
    public static void main(String[] args) throws DAOException {
        EmpresaDAO empresaDAO = new EmpresaDAO();
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa 1");
        empresa.setCnpj("2143325");
        empresa.setCpf("2143325");
        empresa.setPorcentagemComissaoEntregador(.1);
        empresaDAO.salvar(empresa);
        System.out.println(empresaDAO.consultarTodos().getFirst().getNome());

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario 1");
        funcionario.setSenha("senha boa");
        funcionario.setTelefone("132412");
        funcionario.setEmpresa(empresa);
        funcionarioDAO.salvar(funcionario);
        System.out.println(funcionarioDAO.consultarTodos().getFirst().getNome());

        AdministradorDAO administradorDAO = new AdministradorDAO();
        Administrador administrador = new Administrador();
        administrador.setFuncionario(funcionario);
        administradorDAO.salvar(administrador);
    }
}
