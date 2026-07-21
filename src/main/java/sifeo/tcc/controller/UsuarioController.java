package sifeo.tcc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sifeo.tcc.models.dto.UsuarioPerfilDTO;
import sifeo.tcc.models.entities.Usuario;
import sifeo.tcc.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/informacao")
    public ResponseEntity<UsuarioPerfilDTO> obterPerfilUsuarioAtual() {

        String loginUsuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = usuarioService.buscarPorLogin(loginUsuario);

        UsuarioPerfilDTO perfilDTO = new UsuarioPerfilDTO(
                usuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(perfilDTO);
    }
}