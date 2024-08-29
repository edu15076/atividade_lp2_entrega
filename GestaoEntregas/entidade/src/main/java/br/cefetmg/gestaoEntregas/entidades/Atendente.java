package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("ATENDENTE")
public class Atendente extends Perfil {
    public Atendente() {
        this.setTipoPerfil(TipoPerfil.ATENDENTE);
    }
}
