package sifeo.tcc.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sifeo.tcc.models.embedable.EquipamentoAtividadeId;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equipamento_atividade")
public class EquipamentoAtividade {

    @EmbeddedId
    private EquipamentoAtividadeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipamentoId")
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("atividadeId")
    @JoinColumn(name = "atividade_id")
    private HistoricoAtividade atividade;

    @Column(name = "horas_trabalhadas", precision = 5, scale = 2)
    private BigDecimal horasTrabalhadas;
}