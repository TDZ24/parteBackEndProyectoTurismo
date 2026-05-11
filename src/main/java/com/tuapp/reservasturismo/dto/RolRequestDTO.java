package com.tuapp.reservasturismo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RolRequestDTO {

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(regexp = "ADMIN|USER|admin|user", message = "Rol inválido. Los valores permitidos son: ADMIN, USER")
    private String rol;

    private String tokenAdmin;

    public RolRequestDTO() {}

    public String getRol()        { return rol; }
    public String getTokenAdmin() { return tokenAdmin; }

    public void setRol(String rol)              { this.rol = rol; }
    public void setTokenAdmin(String tokenAdmin){ this.tokenAdmin = tokenAdmin; }
}
