package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Administrador extends Perfil {
    public Administrador() {
        this.setTipoPerfil(TipoPerfil.ADMINISTRADOR);
    }
}
