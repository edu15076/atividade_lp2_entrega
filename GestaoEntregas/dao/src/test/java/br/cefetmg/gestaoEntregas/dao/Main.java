package br.cefetmg.gestaoEntregas.dao;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Administrador;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.converters.TipoPerfilConverter;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;

public class Main {
    public static void main(String[] args) throws DAOException {
        try {
//            TipoPerfilConverter tpc = new TipoPerfilConverter();
//            System.out.println(tpc.convertToEntityAttribute("Entregador"));

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
            funcionario.setSenha("c7a505d579e5e574d29aa957d4d317440a7832e9132d9963a08de1c6d498296e"); // senha boa
            funcionario.setTelefone("132412");
            funcionario.setEmpresa(empresa);
            funcionarioDAO.salvar(funcionario);
            System.out.println(funcionarioDAO.consultarTodos().getFirst().getNome());

            AdministradorDAO administradorDAO = new AdministradorDAO();
            Administrador administrador = new Administrador();
            administrador.setFuncionario(funcionario);
            administradorDAO.salvar(administrador);
        } catch (AtributoInvalidoException e) {
            throw new RuntimeException(e);
        }
    }
}
