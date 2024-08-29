package br.cefetmg.gestaoEntregas.dao.exceptions;

public class DAOException extends Exception {
    public DAOException(String mensagem, Exception e) {
        super(mensagem, e);
    }

    public DAOException(String mensagem) {
        super(mensagem);
    }
}
