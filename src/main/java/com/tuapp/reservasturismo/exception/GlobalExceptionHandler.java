package com.tuapp.reservasturismo.exception;

import com.tuapp.reservasturismo.dto.api.ApiError;
import com.tuapp.reservasturismo.dto.api.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Captura excepciones no manejadas en los controllers y retorna una estructura
 * consistente para que el frontend siempre pueda leer success, message, errors,
 * timestamp, path y status.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> manejarValidaciones(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest request) {
        List<ApiError> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiError(error.getField(), error.getDefaultMessage()))
                .toList();

        return construirRespuesta(HttpStatus.BAD_REQUEST, "Hay datos inválidos en la solicitud.", errores, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> manejarConstraintViolations(ConstraintViolationException ex,
                                                                         HttpServletRequest request) {
        List<ApiError> errores = ex.getConstraintViolations()
                .stream()
                .map(error -> new ApiError(error.getPropertyPath().toString(), error.getMessage()))
                .toList();

        return construirRespuesta(HttpStatus.BAD_REQUEST, "Hay parámetros inválidos en la solicitud.", errores, request);
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ApiResponse<Void>> manejarCredenciales(CredencialesInvalidasException ex,
                                                                 HttpServletRequest request) {
        return construirRespuesta(HttpStatus.UNAUTHORIZED, ex.getMessage(), List.of(new ApiError(ex.getCodigo(), ex.getMessage())), request);
    }

    @ExceptionHandler(ReservaException.class)
    public ResponseEntity<ApiResponse<Void>> manejarReservaException(ReservaException ex,
                                                                     HttpServletRequest request) {
        HttpStatus status = resolverEstado(ex.getCodigo());
        return construirRespuesta(status, ex.getMessage(), List.of(new ApiError(ex.getCodigo(), ex.getMessage())), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> manejarIllegalArgument(IllegalArgumentException ex,
                                                                    HttpServletRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of(new ApiError("ARGUMENTO_INVALIDO", ex.getMessage())), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> manejarAccessDenied(AccessDeniedException ex,
                                                                 HttpServletRequest request) {
        return construirRespuesta(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.", List.of(new ApiError("ACCESO_DENEGADO", "Acceso denegado")), request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> manejarRuntime(RuntimeException ex,
                                                            HttpServletRequest request) {
        return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of(new ApiError("ERROR_NEGOCIO", ex.getMessage())), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> manejarGeneral(Exception ex,
                                                            HttpServletRequest request) {
        return construirRespuesta(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno. Intenta de nuevo más tarde.",
                List.of(new ApiError("ERROR_INTERNO", "Error interno del servidor")),
                request
        );
    }

    private HttpStatus resolverEstado(String codigo) {
        if (codigo == null) {
            return HttpStatus.BAD_REQUEST;
        }
        if (codigo.endsWith("NO_ENCONTRADO") || codigo.endsWith("NO_ENCONTRADA")) {
            return HttpStatus.NOT_FOUND;
        }
        if (codigo.contains("YA_EXISTE") || codigo.contains("YA_CANCELADA")) {
            return HttpStatus.CONFLICT;
        }
        if (codigo.contains("ACCESO_DENEGADO") || codigo.contains("TOKEN")) {
            return HttpStatus.FORBIDDEN;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private ResponseEntity<ApiResponse<Void>> construirRespuesta(HttpStatus status,
                                                                 String message,
                                                                 List<ApiError> errors,
                                                                 HttpServletRequest request) {
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(message, errors, request.getRequestURI(), status.value()));
    }
}
