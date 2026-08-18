package sifeo.tcc.models.dto.response;

import lombok.Data;
import sifeo.tcc.models.enums.StatusAtividade;

import java.time.LocalDateTime;

@Data
public class AtividadeResponseDTO {
    private Integer id;
    private LocalDateTime dataAtividade;

    private String tipoAtividadeNome;
    private String setorNome;
    private String responsavelNome;
    private String equipamentosNomes;
    private StatusAtividade status;
    private String descricao;
}