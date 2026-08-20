package sifeo.tcc.models.dto.request;

import lombok.Data;
import sifeo.tcc.models.enums.CategoriaInsumo;
import sifeo.tcc.models.enums.UnidadeMedida;

@Data
public class InsumoRequestDTO {
    private Integer id;
    private Integer sitioId;
    private String nome;
    private String descricao;
    private Double quantidadeEstoque;

    private CategoriaInsumo categoria;
    private UnidadeMedida unidadeMedida;
    private String fornecedor;
}