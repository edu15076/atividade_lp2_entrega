package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.FuncionarioDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.dao.utils.Pair;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.security.auth.login.LoginException;
import java.util.List;

public class FuncionarioController {
    private final FuncionarioDAO dao;

    public FuncionarioController() throws DAOException {
//        CheckLoginInterceptor.init(this.getClass());
        dao = new FuncionarioDAO();
    }

    public Funcionario cadastrar(String nome, String senha, String telefone, Empresa empresa) throws DAOException, AtributoInvalidoException {
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(nome);
        String hashSenha = DigestUtils.sha256Hex(senha);
        funcionario.setSenha(hashSenha);
        funcionario.setTelefone(telefone);
        funcionario.setEmpresa(empresa);

        dao.salvar(funcionario);

        return funcionario;
    }

    public void deletar(Funcionario funcionario) throws DAOException {
        dao.deletar(funcionario);
    }

    public void atualizar(Funcionario funcionario) throws DAOException {
        dao.atualizar(funcionario);
    }

    public Funcionario consultarTelefone(String telefone) throws DAOException {
        List<Funcionario> funcionarios = dao.consultarCampo(new Pair<>("telefone", telefone));

        return funcionarios.isEmpty() ? null : funcionarios.getFirst();
    }

    public List<Funcionario> consultarFuncionarios(Empresa empresa) throws DAOException {
        return dao.consultarCampo(new Pair<>("empresa", empresa));
    }
}
