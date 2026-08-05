package sifeo.tcc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sifeo.tcc.models.dto.request.EquipamentoRequestDTO;
import sifeo.tcc.models.dto.response.EquipamentoResponseDTO;
import sifeo.tcc.models.entities.Equipamento;
import sifeo.tcc.models.entities.Sitio;
import sifeo.tcc.repository.EquipamentoRepository;
import sifeo.tcc.repository.SitioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;
    private final SitioRepository sitioRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository, SitioRepository sitioRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.sitioRepository = sitioRepository;
    }

    @Transactional(readOnly = true)
    public List<EquipamentoResponseDTO> listarTodos(Integer sitioId) {
        List<Equipamento> equipamentos;
        if (sitioId != null) {
            equipamentos = equipamentoRepository.findBySitioId(sitioId);
        } else {
            equipamentos = equipamentoRepository.findAll();
        }
        return equipamentos.stream().map(this::mapearParaDTO).collect(Collectors.toList());
    }

    @Transactional
    public EquipamentoResponseDTO cadastrar(EquipamentoRequestDTO dto) {
        Sitio sitio = sitioRepository.findById(dto.getSitioId())
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada."));

        Equipamento equipamento = new Equipamento();
        preencherEntidade(equipamento, dto, sitio);

        Equipamento salvo = equipamentoRepository.save(equipamento);
        return mapearParaDTO(salvo);
    }

    @Transactional
    public EquipamentoResponseDTO atualizar(Integer id, EquipamentoRequestDTO dto) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado."));

        Sitio sitio = sitioRepository.findById(dto.getSitioId())
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada."));

        preencherEntidade(equipamento, dto, sitio);

        Equipamento atualizado = equipamentoRepository.save(equipamento);
        return mapearParaDTO(atualizado);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!equipamentoRepository.existsById(id)) {
            throw new RuntimeException("Equipamento não encontrado para exclusão.");
        }
        equipamentoRepository.deleteById(id);
    }

    private void preencherEntidade(Equipamento eqp, EquipamentoRequestDTO dto, Sitio sitio) {
        eqp.setSitio(sitio);
        eqp.setNome(dto.getNome());
        eqp.setTipo(dto.getTipo());
        eqp.setMarcaModelo(dto.getMarcaModelo());
        eqp.setAno(dto.getAno());
        eqp.setStatus(dto.getStatus());
        eqp.setDataAquisicao(dto.getDataAquisicao());
        eqp.setDataVenda(dto.getDataVenda());
        eqp.setDescricao(dto.getDescricao());
    }

    private EquipamentoResponseDTO mapearParaDTO(Equipamento eqp) {
        EquipamentoResponseDTO dto = new EquipamentoResponseDTO();
        dto.setId(eqp.getId());
        dto.setNome(eqp.getNome());
        dto.setTipo(eqp.getTipo());
        dto.setMarcaModelo(eqp.getMarcaModelo());
        dto.setAno(eqp.getAno());
        dto.setStatus(eqp.getStatus());
        dto.setDataAquisicao(eqp.getDataAquisicao());
        dto.setDataVenda(eqp.getDataVenda());
        dto.setDescricao(eqp.getDescricao());
        if (eqp.getSitio() != null) {
            dto.setNomePropriedade(eqp.getSitio().getNome());
        }
        return dto;
    }
}