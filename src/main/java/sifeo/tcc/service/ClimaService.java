package sifeo.tcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sifeo.tcc.models.dto.request.ClimaRequestDTO;
import sifeo.tcc.models.dto.response.ClimaResponseDTO;
import sifeo.tcc.models.entities.Clima;
import sifeo.tcc.models.entities.Sitio;
import sifeo.tcc.repository.ClimaRepository;
import sifeo.tcc.repository.SitioRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClimaService {

    private final ClimaRepository climaRepository;
    private final SitioRepository sitioRepository;

    public ClimaService(ClimaRepository climaRepository, SitioRepository sitioRepository) {
        this.climaRepository = climaRepository;
        this.sitioRepository = sitioRepository;
    }

    public List<ClimaResponseDTO> listarPorSitio(Integer sitioId) {
        if (sitioId == null) {
            return climaRepository.findAll().stream().map(this::mapearParaDTO).collect(Collectors.toList());
        }
        return climaRepository.findBySitioIdOrderByDataHoraDesc(sitioId).stream().map(this::mapearParaDTO).collect(Collectors.toList());
    }

    @Transactional
    public ClimaResponseDTO cadastrar(ClimaRequestDTO dto) {
        Sitio sitio = sitioRepository.findById(dto.getSitioId())
                .orElseThrow(() -> new RuntimeException("Sítio não encontrado com ID: " + dto.getSitioId()));

        Clima clima = new Clima();
        clima.setSitio(sitio);
        clima.setTempo(dto.getTempo());
        clima.setDescricao(dto.getDescricao());
        clima.setDataHora(dto.getDataHora());

        clima.setMilimetros(dto.getMilimetros() != null ? dto.getMilimetros() : BigDecimal.ZERO);

        clima = climaRepository.save(clima);
        return mapearParaDTO(clima);
    }

    @Transactional
    public ClimaResponseDTO atualizar(Integer id, ClimaRequestDTO dto) {
        Clima clima = climaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro climático não encontrado."));

        clima.setTempo(dto.getTempo());
        clima.setDescricao(dto.getDescricao());
        clima.setDataHora(dto.getDataHora());

        if (dto.getMilimetros() != null) {
            clima.setMilimetros(dto.getMilimetros());
        }

        clima = climaRepository.save(clima);
        return mapearParaDTO(clima);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!climaRepository.existsById(id)) {
            throw new RuntimeException("Registro climático não encontrado.");
        }
        climaRepository.deleteById(id);
    }

    private ClimaResponseDTO mapearParaDTO(Clima clima) {
        ClimaResponseDTO dto = new ClimaResponseDTO();
        dto.setId(clima.getId());
        dto.setTempo(clima.getTempo());
        dto.setDescricao(clima.getDescricao());
        dto.setMilimetros(clima.getMilimetros());
        dto.setDataHora(clima.getDataHora());

        if (clima.getSitio() != null) {
            dto.setPropriedadeNome(clima.getSitio().getNome());
        }
        return dto;
    }
}