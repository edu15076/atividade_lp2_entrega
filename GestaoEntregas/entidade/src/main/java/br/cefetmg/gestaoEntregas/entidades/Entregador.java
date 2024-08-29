package br.cefetmg.gestaoEntregas.entidades;

import br.cefetmg.gestaoEntregas.entidades.enums.TipoPerfil;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("ENTREGADOR")
public class Entregador extends Perfil {
    public Entregador() {
        this.setTipoPerfil(TipoPerfil.ENTREGADOR);
    }

    @OneToMany(mappedBy = "entregador", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Pedido> pedidos;

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public String toString() {
        return "Entregador{id= " + getFuncionario().getId()  +
                ", nome='" + getFuncionario().getNome() + '\'' +
                ", telefone='" + getFuncionario().getTelefone() + '\'' +
                '}';
    }
}
