package com.tuapp.reservasturismo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 100, message = "El username debe tener entre 3 y 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 255, message = "La contraseña debe tener entre 6 y 255 caracteres")
    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.USER;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reserva> reservas;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    public enum Rol {
        ADMIN, USER
    }
}