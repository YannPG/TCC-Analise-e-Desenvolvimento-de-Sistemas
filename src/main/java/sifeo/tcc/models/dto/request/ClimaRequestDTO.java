package sifeo.tcc.models.dto.request;

import lombok.Data;
import sifeo.tcc.models.enums.Tempo;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ClimaRequestDTO {
    private Integer id;
    private Integer sitioId;
    private Tempo tempo;
    private String descricao;
    private BigDecimal milimetros;
    private LocalDateTime dataHora;
}