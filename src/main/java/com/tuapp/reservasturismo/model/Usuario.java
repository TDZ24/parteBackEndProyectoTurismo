package com.tuapp.reservasturismo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String rol;
    private String avatarUrl;

    public Usuario() {}

    public Usuario(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = "USER";
        this.avatarUrl = generarAvatarUrl(email);
    }

    public Usuario(Long id, String nombre, String email, String password, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = (rol != null) ? rol : "USER";
        this.avatarUrl = generarAvatarUrl(email);
    }

    private String generarAvatarUrl(String email) {
        String seed = (email != null) ? email.toLowerCase().trim() : "default";
        return "https://api.dicebear.com/7.x/initials/svg?seed=" + seed;
    }

    public Long getId()          { return id; }
    public String getNombre()    { return nombre; }
    public String getEmail()     { return email; }
    public String getRol()       { return rol; }
    public String getAvatarUrl() { return avatarUrl; }

    // @JsonIgnore hace que Jackson nunca incluya este campo en ninguna respuesta JSON
    @JsonIgnore
    public String getPassword()  { return password; }

    public void setId(Long id)               { this.id = id; }
    public void setNombre(String nombre)     { this.nombre = nombre; }
    public void setPassword(String password) { this.password = password; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public void setEmail(String email) {
        this.email = email;
        this.avatarUrl = generarAvatarUrl(email);
    }

    public void setRol(String rol) {
        if (!"ADMIN".equalsIgnoreCase(rol) && !"USER".equalsIgnoreCase(rol)) {
            throw new IllegalArgumentException("Rol invalido. Solo se permite ADMIN o USER.");
        }
        this.rol = rol.toUpperCase();
    }

    public boolean esAdmin() {
        return "ADMIN".equalsIgnoreCase(this.rol);
    }
}