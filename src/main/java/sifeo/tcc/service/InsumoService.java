package sifeo.tcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sifeo.tcc.models.dto.request.InsumoRequestDTO;
import sifeo.tcc.models.dto.response.InsumoResponseDTO;
import sifeo.tcc.models.entities.Insumo;
import sifeo.tcc.models.entities.Sitio;
import sifeo.tcc.repository.InsumoRepository;
import sifeo.tcc.repository.SitioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsumoService {

    private final InsumoRepository insumoRepository;
    private final SitioRepository sitioRepository;

    public InsumoService(InsumoRepository insumoRepository, SitioRepository sitioRepository) {
        this.insumoRepository = insumoRepository;
        this.sitioRepository = sitioRepository;
    }

    public List<InsumoResponseDTO> listarPorSitio(Integer sitioId) {
        if (sitioId == null) {
            return insumoRepository.findAll().stream().map(this::mapearParaDTO).collect(Collectors.toList());
        }
        return insumoRepository.findBySitioId(sitioId).stream().map(this::mapearParaDTO).collect(Collectors.toList());
    }

    @Transactional
    public InsumoResponseDTO cadastrar(InsumoRequestDTO dto) {
        Sitio sitio = sitioRepository.findById(dto.getSitioId())
                .orElseThrow(() -> new RuntimeException("Sítio não encontrado com ID: " + dto.getSitioId()));

        Insumo insumo = new Insumo();
        insumo.setSitio(sitio);
        insumo.setNome(dto.getNome());
        insumo.setDescricao(dto.getDescricao());
        insumo.setQuantidadeEstoque(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0.0);

        insumo.setCategoria(dto.getCategoria());
        insumo.setUnidadeMedida(dto.getUnidadeMedida());
        insumo.setFornecedor(dto.getFornecedor());

        insumo = insumoRepository.save(insumo);
        return mapearParaDTO(insumo);
    }

    @Transactional
    public InsumoResponseDTO atualizar(Integer id, InsumoRequestDTO dto) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado com ID: " + id));

        insumo.setNome(dto.getNome());
        insumo.setDescricao(dto.getDescricao());
        if (dto.getQuantidadeEstoque() != null) {
            insumo.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        }

        insumo.setCategoria(dto.getCategoria());
        insumo.setUnidadeMedida(dto.getUnidadeMedida());
        insumo.setFornecedor(dto.getFornecedor());

        insumo = insumoRepository.save(insumo);
        return mapearParaDTO(insumo);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!insumoRepository.existsById(id)) {
            throw new RuntimeException("Insumo não encontrado com o ID: " + id);
        }
        try {
            insumoRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir. O insumo possui vínculos ativos no sistema.");
        }
    }

    private InsumoResponseDTO mapearParaDTO(Insumo insumo) {
        InsumoResponseDTO dto = new InsumoResponseDTO();
        dto.setId(insumo.getId());
        dto.setNome(insumo.getNome());
        dto.setDescricao(insumo.getDescricao());
        dto.setQuantidadeEstoque(insumo.getQuantidadeEstoque());
        dto.setCategoria(insumo.getCategoria());
        dto.setUnidadeMedida(insumo.getUnidadeMedida());
        dto.setFornecedor(insumo.getFornecedor());

        if (insumo.getSitio() != null) {
            dto.setPropriedadeNome(insumo.getSitio().getNome());
        }
        return dto;
    }
}