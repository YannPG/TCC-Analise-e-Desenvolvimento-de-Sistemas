package sifeo.tcc.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "setor")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitio_id", nullable = false)
    private Sitio sitio;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private Double hectares;

    @Column(length = 100)
    private String plantio;

    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;
}