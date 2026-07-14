package sifeo.tcc.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria idCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    private Equipamentos idEquipamentos;

    @ManyToOne(fetch = FetchType.LAZY)
    private Setores idSetores;

    @ManyToOne(fetch = FetchType.LAZY)
    private Insumos idInsumos;

    @ManyToOne(fetch = FetchType.LAZY)
    private Funcionario funcionario;

    private String nome;

    private String descricao;

    private LocalDateTime dataAdicionado;

    private byte arquivo;

    private boolean receitaDespesa;

    private BigDecimal valor;
}
