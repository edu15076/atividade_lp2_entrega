package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.ProdutoDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.dao.utils.Pair;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Produto;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;

import java.util.List;

public class ProdutoController {
    private final ProdutoDAO dao;

    public ProdutoController() throws DAOException {
        dao = new ProdutoDAO();
    }

    public Produto cradastrar(String nome, String codigo, String localizacao, Empresa empresa) throws AtributoInvalidoException, DAOException {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setLocalizacao(localizacao);
        produto.setCodigo(codigo);
        produto.setEmpresa(empresa);

        dao.salvar(produto);

        return produto;
    }

    public List<Produto> listarProdutosEmpresa(Empresa empresa) throws DAOException {
        return dao.consultarCampo(new Pair<>("empresa", empresa));
    }

    public Produto consultarProdutoCodigo(String codigo) throws DAOException {
        List<Produto> resultadosConsulta = dao.consultarCampo(new Pair<>("codigo", codigo));

        return resultadosConsulta.isEmpty() ? null : resultadosConsulta.get(0);
    }

    public void deletar(Produto produto) throws DAOException {
        dao.deletar(produto);
    }
}
