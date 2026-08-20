package sifeo.tcc.models.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FuncionarioRequestDTO {
    private Integer sitioId;
    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private String email;
    private String cargo;
    private LocalDate dataAdmissao;
    private LocalDate dataNascimento;
    private String status;
}