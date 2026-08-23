package sifeo.tcc.models.embedable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sifeo.tcc.models.entities.Equipamento;
import sifeo.tcc.models.entities.HistoricoAtividade;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class EquipamentoAtividadeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("atividadeId")
    @JoinColumn(name = "atividade_id")
    private HistoricoAtividade atividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipamentoId")
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;
}