package sifeo.tcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sifeo.tcc.models.dto.request.AtividadeRequestDTO;
import sifeo.tcc.models.dto.response.AtividadeResponseDTO;
import sifeo.tcc.models.entities.*;
import sifeo.tcc.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    private final HistoricoAtividadeRepository atividadeRepository;
    private final SetorRepository setorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final TipoAtividadeRepository tipoAtividadeRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final SitioRepository sitioRepository;

    public AtividadeService(
            HistoricoAtividadeRepository atividadeRepository,
            SetorRepository setorRepository,
            FuncionarioRepository funcionarioRepository,
            TipoAtividadeRepository tipoAtividadeRepository,
            EquipamentoRepository equipamentoRepository,
            SitioRepository sitioRepository) {
        this.atividadeRepository = atividadeRepository;
        this.setorRepository = setorRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.tipoAtividadeRepository = tipoAtividadeRepository;
        this.equipamentoRepository = equipamentoRepository;
        this.sitioRepository = sitioRepository;
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarTodos(Integer sitioId) {
        List<HistoricoAtividade> atividades = (sitioId != null)
                ? atividadeRepository.findBySetorSitioId(sitioId)
                : atividadeRepository.findAll();

        return atividades.stream().map(this::mapearParaDTO).collect(Collectors.toList());
    }

    @Transactional
    public AtividadeResponseDTO cadastrar(AtividadeRequestDTO dto) {
        HistoricoAtividade atividade = new HistoricoAtividade();
        preencherEValidadeEntidade(atividade, dto);
        HistoricoAtividade salva = atividadeRepository.save(atividade);
        return mapearParaDTO(salva);
    }

    @Transactional
    public AtividadeResponseDTO atualizar(Integer id, AtividadeRequestDTO dto) {
        HistoricoAtividade atividade = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada."));

        preencherEValidadeEntidade(atividade, dto);

        HistoricoAtividade atualizada = atividadeRepository.save(atividade);
        return mapearParaDTO(atualizada);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!atividadeRepository.existsById(id)) {
            throw new RuntimeException("Atividade não encontrada para exclusão.");
        }
        atividadeRepository.deleteById(id);
    }

    private void preencherEValidadeEntidade(HistoricoAtividade atividade, AtividadeRequestDTO dto) {
        Sitio sitio = sitioRepository.findById(dto.getSitioId())
                .orElseThrow(() -> new RuntimeException("Sítio não encontrado."));

        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new RuntimeException("Setor não encontrado."));

        TipoAtividade tipo = tipoAtividadeRepository.findById(dto.getTipoAtividadeId())
                .orElseThrow(() -> new RuntimeException("Tipo de Atividade não encontrado."));

        Funcionario responsavel = funcionarioRepository.findById(dto.getResponsavelId())
                .orElseThrow(() -> new RuntimeException("Funcionário responsável não encontrado."));

        atividade.setSitio(sitio);
        atividade.setSetor(setor);
        atividade.setTipoAtividade(tipo);
        atividade.setResponsavel(responsavel);
        atividade.setDataAtividade(dto.getDataAtividade());
        atividade.setStatus(dto.getStatus());
        atividade.setDescricao(dto.getDescricao());

        if (dto.getEquipamentoId() != null) {
            Equipamento eq = equipamentoRepository.findById(dto.getEquipamentoId())
                    .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));
            atividade.setEquipamento(eq);
        } else {
            atividade.setEquipamento(null);
        }
    }

    private AtividadeResponseDTO mapearParaDTO(HistoricoAtividade atividade) {
        AtividadeResponseDTO dto = new AtividadeResponseDTO();
        dto.setId(atividade.getId());
        dto.setDataAtividade(atividade.getDataAtividade());
        dto.setStatus(atividade.getStatus());
        dto.setDescricao(atividade.getDescricao());

        if (atividade.getTipoAtividade() != null) {
            dto.setTipoAtividadeNome(atividade.getTipoAtividade().getNome());
            dto.setTipoAtividadeId(atividade.getTipoAtividade().getId()); 
        }

        if (atividade.getSetor() != null) {
            dto.setSetorNome(atividade.getSetor().getNome());
            dto.setSetorId(atividade.getSetor().getId());
        }

        if (atividade.getResponsavel() != null) {
            dto.setResponsavelNome(atividade.getResponsavel().getNomeCompleto());
            dto.setResponsavelId(atividade.getResponsavel().getId());
        }

        if (atividade.getEquipamento() != null) {
            dto.setEquipamentoNome(atividade.getEquipamento().getNome());
            dto.setEquipamentoId(atividade.getEquipamento().getId());
        } else {
            dto.setEquipamentoNome("-");
        }

        return dto;
    }
}