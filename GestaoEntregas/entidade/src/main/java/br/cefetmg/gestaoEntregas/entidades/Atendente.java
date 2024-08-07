package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class Atendente extends Perfil {
    public Atendente() {
        this.setTipoPerfil(TipoPerfil.ATENDENTE);
    }
}
