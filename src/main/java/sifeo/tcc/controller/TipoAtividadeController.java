package sifeo.tcc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sifeo.tcc.models.entities.TipoAtividade;
import sifeo.tcc.repository.TipoAtividadeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-atividade")
public class TipoAtividadeController {

    private final TipoAtividadeRepository repository;

    public TipoAtividadeController(TipoAtividadeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<TipoAtividade>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }
}