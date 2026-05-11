package com.tuapp.reservasturismo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuapp.reservasturismo.dto.api.ApiError;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import com.tuapp.reservasturismo.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfing {

    private final JwtFilter jwtFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    private final ObjectMapper objectMapper;

    public SecurityConfing(JwtFilter jwtFilter, CorsConfigurationSource corsConfigurationSource, ObjectMapper objectMapper) {
        this.jwtFilter = jwtFilter;
        this.corsConfigurationSource = corsConfigurationSource;
        this.objectMapper = objectMapper;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/api/auth/**").permitAll()
                        .requestMatchers("/categorias/**", "/productos/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> escribirErrorSeguridad(
                                response,
                                HttpStatus.UNAUTHORIZED,
                                "Debes iniciar sesión para acceder a este recurso.",
                                "NO_AUTENTICADO",
                                request.getRequestURI()
                        ))
                        .accessDeniedHandler((request, response, accessDeniedException) -> escribirErrorSeguridad(
                                response,
                                HttpStatus.FORBIDDEN,
                                "No tienes permisos para realizar esta acción.",
                                "ACCESO_DENEGADO",
                                request.getRequestURI()
                        ))
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    private void escribirErrorSeguridad(jakarta.servlet.http.HttpServletResponse response,
                                        HttpStatus status,
                                        String message,
                                        String codigo,
                                        String path) throws java.io.IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(
                response.getWriter(),
                ApiResponse.error(message, List.of(new ApiError(codigo, message)), path, status.value())
        );
    }
}
