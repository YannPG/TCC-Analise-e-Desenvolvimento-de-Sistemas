package sifeo.tcc.models.dto.request;

import lombok.Data;

@Data
public class PerfilRequestDTO {
    private String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String cpf;
    private String senha;      
}