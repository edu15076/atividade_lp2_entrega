package br.cefetmg.gestaoEntregas.controllers;


import br.cefetmg.gestaoEntregas.dao.EmpresaDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.security.auth.login.LoginException;

public class Main {
    public static void test() throws LoginException {
        LoginController.checkLoginOrException(new TipoPerfil[] {TipoPerfil.ADMINISTRADOR});
        System.out.println("Meu pau");
    }

    public static void main(String[] args) {
        try {
            FuncionarioController fc = new FuncionarioController();
            EmpresaDAO edao = new EmpresaDAO();
            Funcionario f1 = fc.consultarTelefone("132412");
            System.out.println(DigestUtils.sha256Hex("senha boa"));
            Funcionario f = LoginController.logar(f1, "senha boa", edao.consultar(1L));
            assert f != null;
            System.out.println(f.getNome());
            test();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
//        try {
//            LoginController lg = LoginController.getInstance();
////            Funcionario f3 = lg.logar(FuncionarioController.getInstance, "abcate", edao.consultar(1L));
//            FuncionarioController fc = new FuncionarioController();
//            EmpresaDAO edao = new EmpresaDAO();
//
//            // TODO fazer o controller de empresa
//            Funcionario f1 = fc.cadastrar("Zezão", "abcate", "35939778", edao.consultar(1L));
//            Funcionario f = lg.logar(f1, "abcate", edao.consultar(1L));
//
//            System.out.println(LoginController.getFuncionarioLogado().getNome());
//
//            ProdutoController pc = new ProdutoController();
//            pc.cradastrar("Jocazão", "Pindamonhagaba", edao.consultar(2L));
//        } catch (DAOException e) {
//            throw new RuntimeException(e);
//        } catch (AtributoInvalidoException e) {
//            throw new RuntimeException(e);
//        }
    }
}
