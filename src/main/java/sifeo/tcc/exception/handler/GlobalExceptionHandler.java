package sifeo.tcc.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sifeo.tcc.exception.dto.ErroResposta;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResposta> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErroResposta erro = new ErroResposta(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResposta> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        ErroResposta erro = new ErroResposta(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Não Autorizado",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErroResposta erro = new ErroResposta(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                "Ocorreu um erro inesperado no sistema. Contate o administrador.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}