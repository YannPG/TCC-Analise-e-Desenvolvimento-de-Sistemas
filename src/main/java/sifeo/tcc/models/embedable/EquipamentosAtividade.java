package sifeo.tcc.models.embedable;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class EquipamentosAtividade implements Serializable {

    private Integer idEquipamento;

    private Integer historicoAtividade;
}
