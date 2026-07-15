package sifeo.tcc.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id")
    private Equipamento equipamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id")
    private Setor setor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(name = "data_adicionado", nullable = false)
    private LocalDate dataAdicionado;

    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;

    @Column(name = "receita_despesa", nullable = false)
    private boolean receitaDespesa;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
}