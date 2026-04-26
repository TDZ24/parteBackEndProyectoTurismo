package com.tuapp.reservasturismo.dto;

public class LoginResponseDTO {
    private String mensaje;
    private String token;         // token de sesión simulado (UUID)
    private Long   usuarioId;
    private String nombre;
    private String email;
    private String rol;
    private String avatarUrl;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String mensaje, String token,
                            Long usuarioId, String nombre,
                            String email, String rol, String avatarUrl) {
        this.mensaje    = mensaje;
        this.token      = token;
        this.usuarioId  = usuarioId;
        this.nombre     = nombre;
        this.email      = email;
        this.rol        = rol;
        this.avatarUrl  = avatarUrl;
    }

    public String getMensaje()   { return mensaje; }
    public String getToken()     { return token; }
    public Long   getUsuarioId() { return usuarioId; }
    public String getNombre()    { return nombre; }
    public String getEmail()     { return email; }
    public String getRol()       { return rol; }
    public String getAvatarUrl() { return avatarUrl; }

    public void setMensaje(String mensaje)     { this.mensaje = mensaje; }
    public void setToken(String token)         { this.token = token; }
    public void setUsuarioId(Long usuarioId)   { this.usuarioId = usuarioId; }
    public void setNombre(String nombre)       { this.nombre = nombre; }
    public void setEmail(String email)         { this.email = email; }
    public void setRol(String rol)             { this.rol = rol; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}