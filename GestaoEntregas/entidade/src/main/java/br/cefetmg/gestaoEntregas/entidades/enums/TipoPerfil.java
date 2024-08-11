package br.cefetmg.gestaoEntregas.entidades.enums;

public enum TipoPerfil {
    ADMINISTRADOR("Administrador"),
    ATENDENTE("Atendente"),
    ENTREGADOR("Entregador");

    private final String descricao;

    TipoPerfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return getDescricao().toUpperCase();
    }

    public static TipoPerfil valueOfDescricao(String descricao) {
        return valueOf(descricao.toUpperCase());
    }
}
