package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;
import br.cefetmg.gestaoEntregas.entidades.Empresa;
import br.cefetmg.gestaoEntregas.entidades.Entregador;
import br.cefetmg.gestaoEntregas.entidades.Funcionario;

import java.time.LocalDate;

public class RelatorioController {
    public Relatorio<Entregador, Double> relatorioEntregadores(Empresa empresa, LocalDate dataInicio, int intervalo) throws DAOException {
        Relatorio<Entregador, Double> relatorio = new Relatorio<>();
        EntregadorController ec = new EntregadorController();
        relatorio.setChaves(ec.recuperarEntregadoresEmpresa(empresa));
        relatorio.setDias(dataInicio, intervalo);
        relatorio.setColetaDado(new Relatorio.ColetaDado<Entregador, Double>() {
            @Override
            public Double coletarDado(Entregador entregador, LocalDate data) throws DAOException {
                return ec.calcularComissaoDia(entregador, data);
            }
        });

        return relatorio;
    }
}
