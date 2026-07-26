package sifeo.tcc.models.dto;

import lombok.Data;

@Data
public class SitioRequestDTO {
    private String nome;
    private String cep;
    private String endereco;
    private String municipio;
    private String uf;
    private String cnpj;
}
