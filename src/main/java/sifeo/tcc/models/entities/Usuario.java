package sifeo.tcc.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeUsuario;

    private String cpf;

    private String nomeCompleto;

    private String email;

    private String senha;
}