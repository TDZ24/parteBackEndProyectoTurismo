package com.tuapp.reservasturismo.service.email;

import com.tuapp.reservasturismo.model.Destino;
import com.tuapp.reservasturismo.model.Reserva;
import com.tuapp.reservasturismo.model.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${turismo.mail.remitente}")
    private String nombreRemitente;

    @Value("${spring.mail.username}")
    private String emailRemitente;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Envía el correo de confirmación al usuario cuando crea una reserva.
     */
    public void enviarConfirmacionReserva(Usuario usuario, Reserva reserva, Destino destino) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(emailRemitente, nombreRemitente);
            helper.setTo(usuario.getEmail());
            helper.setSubject("✅ Confirmación de tu reserva #" + reserva.getId() + " - Explora Mundo");
            helper.setText(construirCuerpoCorreo(usuario, reserva, destino), true); // true = HTML

            mailSender.send(mensaje);

        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            // No bloqueamos la reserva si el correo falla — solo lo registramos
            System.err.println("⚠️ No se pudo enviar el correo de confirmación: " + e.getMessage());
        }
    }

    /**
     * Construye el HTML del correo de confirmación.
     */
    private String construirCuerpoCorreo(Usuario usuario, Reserva reserva, Destino destino) {
        String nombreDestino  = destino != null ? destino.getNombre()   : "Destino #" + reserva.getDestinoId();
        String ubicacion      = destino != null ? destino.getUbicacion() : "-";
        String precio         = destino != null
                ? String.format("$%,.0f", destino.getPrecio()) : "-";

        return """
            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden;">

              <!-- Encabezado -->
              <div style="background-color: #1a73e8; padding: 24px; text-align: center;">
                <h1 style="color: white; margin: 0; font-size: 22px;">TurAventura</h1>
                <p style="color: #d0e8ff; margin: 4px 0 0;">Agencia de Turismo</p>
              </div>

              <!-- Cuerpo -->
              <div style="padding: 32px;">
                <h2 style="color: #1a73e8;">¡Hola, %s! Tu reserva está confirmada ✅</h2>
                <p style="color: #444;">Gracias por elegir Explora Mundo. Aquí tienes el resumen de tu reserva:</p>

                <!-- Tarjeta de reserva -->
                <div style="background-color: #f8f9fa; border-left: 4px solid #1a73e8; padding: 20px; border-radius: 4px; margin: 20px 0;">
                  <table style="width: 100%%; border-collapse: collapse;">
                    <tr>
                      <td style="padding: 8px 0; color: #666; width: 40%%;">📋 Número de reserva</td>
                      <td style="padding: 8px 0; font-weight: bold; color: #222;">#%d</td>
                    </tr>
                    <tr>
                      <td style="padding: 8px 0; color: #666;">📍 Destino</td>
                      <td style="padding: 8px 0; font-weight: bold; color: #222;">%s</td>
                    </tr>
                    <tr>
                      <td style="padding: 8px 0; color: #666;">🗺️ Ubicación</td>
                      <td style="padding: 8px 0; color: #222;">%s</td>
                    </tr>
                    <tr>
                      <td style="padding: 8px 0; color: #666;">📅 Fecha inicio</td>
                      <td style="padding: 8px 0; color: #222;">%s</td>
                    </tr>
                    <tr>
                      <td style="padding: 8px 0; color: #666;">📅 Fecha fin</td>
                      <td style="padding: 8px 0; color: #222;">%s</td>
                    </tr>
                    <tr>
                      <td style="padding: 8px 0; color: #666;">👥 Personas</td>
                      <td style="padding: 8px 0; color: #222;">%d</td>
                    </tr>
                    <tr>
                      <td style="padding: 8px 0; color: #666;">💰 Precio por persona</td>
                      <td style="padding: 8px 0; font-weight: bold; color: #1a73e8;">%s</td>
                    </tr>
                    <tr>
                      <td style="padding: 8px 0; color: #666;">📌 Estado</td>
                      <td style="padding: 8px 0;">
                        <span style="background-color: #e6f4ea; color: #137333; padding: 2px 10px; border-radius: 12px; font-size: 13px;">%s</span>
                      </td>
                    </tr>
                  </table>
                </div>

                <p style="color: #444;">Si tienes alguna pregunta sobre tu reserva, no dudes en contactarnos.</p>
                <p style="color: #444;">¡Que disfrutes tu viaje! 🌟</p>
              </div>

              <!-- Pie -->
              <div style="background-color: #f1f3f4; padding: 16px; text-align: center;">
                <p style="color: #888; font-size: 12px; margin: 0;">© 2025 Explora Mundo · Este es un correo automático, por favor no respondas.</p>
              </div>

            </div>
            """.formatted(
                usuario.getNombre(),
                reserva.getId(),
                nombreDestino,
                ubicacion,
                reserva.getFechaInicio(),
                reserva.getFechaFin(),
                reserva.getCantidadPersonas(),
                precio,
                reserva.getEstado()
        );
    }
}