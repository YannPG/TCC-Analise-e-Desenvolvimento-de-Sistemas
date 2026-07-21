package sifeo.tcc.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sifeo.tcc.exception.dto.RegraNegocioException;
import sifeo.tcc.models.entities.Usuario;
import sifeo.tcc.repository.UsuarioRepository;
import sifeo.tcc.security.dto.RegistroRequest;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registrarNovoUsuario(RegistroRequest request) {

        String cpfLimpo = request.cpf().replaceAll("\\D", "");

        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RegraNegocioException("Este e-mail já está cadastrado no sistema.");
        }

        if (usuarioRepository.existsByCpf(cpfLimpo)) {
            throw new RegraNegocioException("Este CPF já está vinculado a outra conta.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeCompleto(request.nomeCompleto());
        novoUsuario.setNomeUsuario(request.nomeUsuario());
        novoUsuario.setCpf(cpfLimpo);
        novoUsuario.setEmail(request.email());
        novoUsuario.setSenha(passwordEncoder.encode(request.senha()));

        usuarioRepository.save(novoUsuario);
    }

    public Usuario buscarPorLogin(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado no sistema."));
    }
}