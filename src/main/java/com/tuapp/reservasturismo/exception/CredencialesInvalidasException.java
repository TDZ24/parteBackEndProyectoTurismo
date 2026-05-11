package com.tuapp.reservasturismo.exception;

public class CredencialesInvalidasException extends ReservaException {
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje, "CREDENCIALES_INVALIDAS");
    }
}
