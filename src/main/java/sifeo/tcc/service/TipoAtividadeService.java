package sifeo.tcc.service;
import org.springframework.stereotype.Service;
import sifeo.tcc.models.dto.request.TipoAtividadeRequestDTO;
import sifeo.tcc.models.dto.response.TipoAtividadeResponseDTO;
import sifeo.tcc.models.entities.Sitio;
import sifeo.tcc.models.entities.TipoAtividade;
import sifeo.tcc.repository.SitioRepository;
import sifeo.tcc.repository.TipoAtividadeRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoAtividadeService {
    private final TipoAtividadeRepository tipoRepository;
    private final SitioRepository sitioRepository;

    public TipoAtividadeService(TipoAtividadeRepository tipoRepository, SitioRepository sitioRepository) {
        this.tipoRepository = tipoRepository;
        this.sitioRepository = sitioRepository;
    }

    public List<TipoAtividadeResponseDTO> listarPorSitio(Integer sitioId) {
        return tipoRepository.findBySitioIdOrderByNomeAsc(sitioId).stream().map(tipo -> {
            TipoAtividadeResponseDTO dto = new TipoAtividadeResponseDTO();
            dto.setId(tipo.getId());
            dto.setNome(tipo.getNome());
            return dto;
        }).collect(Collectors.toList());
    }

    public TipoAtividadeResponseDTO cadastrar(TipoAtividadeRequestDTO dto) {
        Sitio sitio = sitioRepository.findById(dto.getSitioId())
                .orElseThrow(() -> new RuntimeException("Sítio não encontrado"));
        TipoAtividade tipo = new TipoAtividade();
        tipo.setSitio(sitio);
        tipo.setNome(dto.getNome().trim());
        tipo = tipoRepository.save(tipo);

        TipoAtividadeResponseDTO response = new TipoAtividadeResponseDTO();
        response.setId(tipo.getId());
        response.setNome(tipo.getNome());
        return response;
    }

    public void deletar(Integer id) {
        tipoRepository.deleteById(id);
    }
}