package sifeo.tcc.models.embedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class EquipamentoAtividadeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "equipamento_id")
    private Integer equipamentoId;

    @Column(name = "atividade_id")
    private Integer atividadeId;
}