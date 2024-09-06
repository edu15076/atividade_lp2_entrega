package br.cefetmg.gestaoEntregas.controllers;

import br.cefetmg.gestaoEntregas.dao.exceptions.DAOException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Relatorio<Key, Data> {
    private List<Key> chaves;
    private Map<String, List<Data>> campos;
    private List<LocalDate> dias;
    private boolean buildCompleta;


    public static @FunctionalInterface
    interface ColetaDado<H, D> {
        D coletarDado(H header, LocalDate data) throws Exception;
    }
    private ColetaDado<Key, Data> coletaDado;

    public Relatorio() {
        this.chaves = null;
        this.dias = null;
        this.coletaDado = null;
        this.campos = null;
        this.buildCompleta = false;
    }

    public Relatorio(List<Key> chaves, List<LocalDate> dias, ColetaDado<Key, Data> coletaDado) {
        this.chaves = chaves;
        this.dias = dias;
        this.coletaDado = coletaDado;
        this.campos = new HashMap<String, List<Data>>();
        this.buildCompleta = false;
    }

    public Relatorio(List<Key> chaves, LocalDate primeiroDia, int intervalo, ColetaDado<Key, Data> coletaDado) {
        this.chaves = chaves;
        this.dias = new ArrayList<>(intervalo);
        for (int i = 0; i < intervalo; i++) {
            dias.set(i, primeiroDia.plusDays(i));
        }
        this.coletaDado = coletaDado;
        this.campos = new HashMap<String, List<Data>>();
        this.buildCompleta = false;
    }

    public List<Key> getChaves() {
        return chaves;
    }

    public void setChaves(List<Key> chaves) {
        this.chaves = chaves;
    }

    public List<LocalDate> getDias() {
        return dias;
    }

    public void setDias(List<LocalDate> dias) {
        this.dias = dias;
    }

    public void setDias(LocalDate primeiroDia, int intervalo) {
        dias = new ArrayList<>();

        for (int i = 0; i < intervalo; i++) {
            dias.add(primeiroDia.plusDays(i));
        }
    }

    public ColetaDado<Key, Data> getColetaDado() {
        return coletaDado;
    }

    public void setColetaDado(ColetaDado<Key, Data> coletaDado) {
        this.coletaDado = coletaDado;
    }

    public void buildRelatorio() throws Exception {
        this.campos = new HashMap<>();

        for (Key cabecalho : this.chaves) {
            List<Data> campos = new ArrayList<>();
            for (LocalDate dia : this.dias)
                campos.add(this.coletaDado.coletarDado(cabecalho, dia));

            this.campos.put(cabecalho.toString(), campos);
        }

        this.buildCompleta = true;
    }

    public List<Data> getLinha(Key chave) {
        if(!this.buildCompleta)
            return null;

        return this.campos.get(chave.toString());
    }

    public List<Data> getColuna(LocalDate dia) {
        int diaIndice = (int) ChronoUnit.DAYS.between(dias.get(0), dia);

        if(diaIndice < 0 || diaIndice >= dias.size())
            throw new RuntimeException("O dia " + dia.toString() + " está fora do periodo do relatório.");

        List<Data> coluna = new ArrayList<Data>();
        for(Key key : chaves)
            coluna.add(campos.get(key.toString()).get(diaIndice));

        return coluna;
    }
}
