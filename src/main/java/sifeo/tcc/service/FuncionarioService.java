package sifeo.tcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sifeo.tcc.models.dto.request.FuncionarioRequestDTO;
import sifeo.tcc.models.dto.response.FuncionarioResponseDTO;
import sifeo.tcc.models.entities.Funcionario;
import sifeo.tcc.models.entities.Sitio;
import sifeo.tcc.repository.FuncionarioRepository;
import sifeo.tcc.repository.SitioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final SitioRepository sitioRepository;

    public FuncionarioService(FuncionarioRepository repository, SitioRepository sitioRepository) {
        this.repository = repository;
        this.sitioRepository = sitioRepository;
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponseDTO> listarPorSitio(Integer sitioId) {
        List<Funcionario> funcionarios;

        if (sitioId != null) {
            funcionarios = repository.findBySitioId(sitioId);
        } else {
            funcionarios = repository.findAll();
        }

        return funcionarios.stream().map(this::mapearParaDTO).collect(Collectors.toList());
    }

    private FuncionarioResponseDTO mapearParaDTO(Funcionario f) {
        FuncionarioResponseDTO dto = new FuncionarioResponseDTO();
        dto.setId(f.getId());
        dto.setNomeCompleto(f.getNomeCompleto());
        dto.setCpf(f.getCpf());
        dto.setDataNascimento(f.getDataNascimento());
        dto.setTelefone(f.getTelefone());
        dto.setEmail(f.getEmail());
        dto.setCargo(f.getCargo());
        dto.setDataAdmissao(f.getDataAdmissao());
        dto.setStatus(f.getStatus());

        if (f.getSitio() != null) {
            dto.setPropriedadeNome(f.getSitio().getNome());
        }
        return dto;
    }

    @Transactional
    public FuncionarioResponseDTO cadastrar(FuncionarioRequestDTO dto) {
        Funcionario f = new Funcionario();
        f.setNomeCompleto(dto.getNomeCompleto());
        f.setCpf(dto.getCpf());
        f.setTelefone(dto.getTelefone());
        f.setEmail(dto.getEmail());
        f.setCargo(dto.getCargo());
        f.setDataAdmissao(dto.getDataAdmissao());
        f.setDataNascimento(dto.getDataNascimento());
        f.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");
        Sitio sitio = sitioRepository.findById(dto.getSitioId())
                .orElseThrow(() -> new RuntimeException("Sítio não encontrado com ID: " + dto.getSitioId()));
        f.setSitio(sitio);

        f = repository.save(f);
        return mapearParaDTO(f);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado com o ID: " + id);
        }

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir. Este funcionário possui vínculos ativos no sistema.");
        }
    }

    @Transactional
    public FuncionarioResponseDTO atualizar(Integer id, FuncionarioRequestDTO dto) {
        Funcionario f = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

        f.setNomeCompleto(dto.getNomeCompleto());
        f.setCpf(dto.getCpf());
        f.setTelefone(dto.getTelefone());
        f.setEmail(dto.getEmail());
        f.setCargo(dto.getCargo());
        f.setDataAdmissao(dto.getDataAdmissao());
        f.setDataNascimento(dto.getDataNascimento());
        f.setStatus(dto.getStatus() != null ? dto.getStatus() : "ATIVO");

        f = repository.save(f);
        return mapearParaDTO(f);
    }
}