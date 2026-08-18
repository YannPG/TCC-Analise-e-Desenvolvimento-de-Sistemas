package sifeo.tcc.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sifeo.tcc.models.enums.StatusAtividade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "historico_atividade")
public class HistoricoAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_atividade_id", nullable = false)
    private TipoAtividade tipoAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Funcionario responsavel;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusAtividade status = StatusAtividade.AGENDADA;

    @Column(name = "data_atividade", nullable = false)
    private LocalDateTime dataAtividade;

    @OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipamentoAtividade> equipamentosAtividades = new ArrayList<>();
}