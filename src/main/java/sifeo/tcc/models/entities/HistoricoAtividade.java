package sifeo.tcc.models.entities;

import jakarta.persistence.*;
import sifeo.tcc.models.embedable.EquipamentosAtividade;

import java.time.LocalDate;

@Entity
@Table(name = "historico_atividade")
public class HistoricoAtividade {

    @EmbeddedId
    private EquipamentosAtividade id;

    private String descricaoAtividade;

    private boolean statusAtividade;

    private LocalDate dataAtividade;
}
