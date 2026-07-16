package sifeo.tcc.security.dto;

public record RegistroRequest(
        String nomeUsuario,
        String cpf,
        String nomeCompleto,
        String email,
        String senha
) {}