package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.EmpresaDAO;
import br.cefetmg.gestaoEntregas.dao.FuncionarioDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import org.apache.commons.codec.digest.DigestUtils;

import javax.security.auth.login.LoginException;

public class LoginController {
    private static Funcionario funcionarioLogado = null;

    public static Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    public static boolean checkLogin(TipoPerfil[] tiposPerfil) {
        boolean acesso = false;

        if (funcionarioLogado != null) {
            for (TipoPerfil tipoPerfil : tiposPerfil)
                acesso = acesso || funcionarioLogado.inPerfis(tipoPerfil);
        }

        return acesso;
    }

    public static void checkLoginOrException(TipoPerfil[] tiposPerfil) throws LoginException {
        if(!checkLogin(tiposPerfil))
            throw new LoginException("Usuário logado não tem acesso");
    }

    public static Funcionario logar(Funcionario funcionario, String senha, Empresa empresa) {
        String hashSenha = DigestUtils.sha256Hex(senha);

        if (!hashSenha.equals(funcionario.getSenha()) || !funcionario.getEmpresa().equals(empresa))
            return null;

        return funcionarioLogado = funcionario;
    }

    public static void logout() {
        funcionarioLogado = null;
    }
}
