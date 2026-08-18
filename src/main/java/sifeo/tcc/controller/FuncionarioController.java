package sifeo.tcc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sifeo.tcc.models.entities.Funcionario;
import sifeo.tcc.repository.FuncionarioRepository;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository repository;

    public FuncionarioController(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }
}