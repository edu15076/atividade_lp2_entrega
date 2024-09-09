package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.EntregadorDAO;
import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Entregador;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;
import br.cefetmg.gestaoEntregas.entidades.Pedido;
import br.cefetmg.gestaoEntregas.entidades.enums.Status;
import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import br.cefetmg.gestaoEntregas.entidades.exceptions.AtributoInvalidoException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class EntregadorController {
    private final EntregadorDAO dao;

    public EntregadorController() throws DAOException {
        this.dao = new EntregadorDAO();
    }

    public void criar(String nome, String senha, String telefone, Empresa empresa) throws DAOException, AtributoInvalidoException {
        FuncionarioController funcionarioController = new FuncionarioController();

        Funcionario funcionario = funcionarioController.cadastrar(nome, senha, telefone, empresa);
        Entregador entregador = new Entregador();
        entregador.setFuncionario(funcionario);

        dao.salvar(entregador);
    }

    public double calcularComissao(Entregador entregador) {
        Empresa empresa = entregador.getFuncionario().getEmpresa();
        List<Pedido> pedidos = entregador.getPedidos();
        double montante = 0;

        for (Pedido pedido : pedidos) {
            montante += pedido.getValorTotal() * empresa.getPorcentagemComissaoEntregador();
        };

        return montante;
    }

    public double calcularComissaoDia(Entregador entregador, LocalDate dia) throws DAOException {
        Empresa empresa = entregador.getFuncionario().getEmpresa();
        List<Pedido> pedidos = dao.consultarPedidosEntreguesDia(entregador, Date.valueOf(dia));

        double montante = 0.0;

        for (Pedido pedido : pedidos)
            montante += pedido.getValorTotal();

        return empresa.getPorcentagemComissaoEntregador() * montante;
    }

    public List<Entregador> recuperarEntregadoresEmpresa(Empresa empresa) throws DAOException {
        return dao.consultarEntregadoresEmpresa(empresa);
    }

    public Entregador recuperarPorFuncionarioTelefone(String telefone) throws DAOException {
        return dao.consultarEntregador(telefone);
    }
}
