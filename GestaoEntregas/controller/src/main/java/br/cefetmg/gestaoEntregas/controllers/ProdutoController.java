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

    public Produto cradastrar(String nome, String localizacao, Empresa empresa) throws AtributoInvalidoException, DAOException {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setLocalizacao(localizacao);
        produto.setEmpresa(empresa);

        dao.salvar(produto);

        return produto;
    }

    public List<Produto> listarProdutosEmpresa(Empresa empresa) throws DAOException {
        return dao.consultarCampo(new Pair<String, Empresa>("empresa", empresa));
    }

    public void deletar(Produto produto) throws DAOException {
        dao.deletar(produto);
    }
}
