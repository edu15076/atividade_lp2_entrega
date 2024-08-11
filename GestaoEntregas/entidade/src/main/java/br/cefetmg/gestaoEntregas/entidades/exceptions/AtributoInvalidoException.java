package br.cefetmg.gestaoEntregas.entidades.exceptions;

public class AtributoInvalidoException extends Exception{

    public AtributoInvalidoException(String message) {
        super(message);
    }

    public AtributoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
