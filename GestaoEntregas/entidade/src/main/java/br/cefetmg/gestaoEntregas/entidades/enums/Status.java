package br.cefetmg.gestaoEntregas.entidades.enums;

public enum Status {
    EM_PREPARACAO("Em preparação"),
    SAIU_PARA_ENTREGA("Saiu para entrega"),
    ENTREGUE("Entregue");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status valueOfDescricao(String descricao) {
        return valueOf(descricao.toUpperCase());
    }
}
