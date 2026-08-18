package sifeo.tcc.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import sifeo.tcc.models.enums.StatusAtividade;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AtividadeRequestDTO {

    @NotNull(message = "O Setor é obrigatório")
    private Integer setorId;

    @NotNull(message = "O Tipo de Atividade é obrigatório")
    private Integer tipoAtividadeId;

    @NotNull(message = "O Responsável é obrigatório")
    private Integer responsavelId;

    private List<Integer> equipamentosIds;

    @NotNull(message = "A data da atividade é obrigatória")
    private LocalDateTime dataAtividade;

    @NotNull(message = "O status é obrigatório")
    private StatusAtividade status;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;
}