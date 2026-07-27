package sifeo.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sifeo.tcc.exception.model.AcessoNegadoException;
import sifeo.tcc.exception.model.RecursoNaoEncontradoException;
import sifeo.tcc.models.dto.request.SitioRequestDTO;
import sifeo.tcc.models.dto.request.SitioResponseDTO;
import sifeo.tcc.models.entities.Sitio;
import sifeo.tcc.models.entities.Usuario;
import sifeo.tcc.repository.SitioRepository;
import sifeo.tcc.utils.DocumentoValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SitioService {

    @Autowired
    private SitioRepository sitioRepository;

    private Sitio buscarSitioSeguro(Integer idSitio, Usuario usuarioLogado) {
        Sitio sitio = sitioRepository.findById(idSitio)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Propriedade não encontrada com o ID: " + idSitio));

        if (!sitio.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new AcessoNegadoException("Violação de segurança: Você não tem permissão para acessar ou modificar esta propriedade.");
        }
        return sitio;
    }

    private void validarIntegridadeDosDados(SitioRequestDTO dto) {
        if (dto.getCnpj() != null && !dto.getCnpj().trim().isEmpty()) {
            DocumentoValidator.validarCpfCnpj(dto.getCnpj());
        }

        if (dto.getCep() != null && !dto.getCep().trim().isEmpty()) {
            DocumentoValidator.validarCep(dto.getCep());
        }
    }

    private String extrairApenasNumeros(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        return valor.replaceAll("\\D", "");
    }

    private SitioResponseDTO converterParaDTO(Sitio sitio) {
        SitioResponseDTO dto = new SitioResponseDTO();
        dto.setId(sitio.getId());
        dto.setNome(sitio.getNome());
        dto.setCnpj(sitio.getCnpj());
        dto.setCep(sitio.getCep());
        dto.setEndereco(sitio.getEndereco());
        dto.setMunicipio(sitio.getMunicipio());
        dto.setUf(sitio.getUf());
        dto.setQuantidadeSetores(sitio.getSetores() != null ? sitio.getSetores().size() : 0);

        return dto;
    }

    public List<SitioResponseDTO> listarSitiosDoUsuario(Integer usuarioId) {
        List<Sitio> sitios = sitioRepository.findByUsuarioId(usuarioId);
        return sitios.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public SitioResponseDTO criarSitio(SitioRequestDTO dto, Usuario usuarioLogado) {
        validarIntegridadeDosDados(dto);

        Sitio sitio = new Sitio();
        sitio.setNome(dto.getNome());
        sitio.setCnpj(extrairApenasNumeros(dto.getCnpj()));
        sitio.setCep(extrairApenasNumeros(dto.getCep()));

        sitio.setEndereco(dto.getEndereco());
        sitio.setMunicipio(dto.getMunicipio());
        sitio.setUf(dto.getUf());
        sitio.setUsuario(usuarioLogado);

        Sitio sitioSalvo = sitioRepository.save(sitio);
        return converterParaDTO(sitioSalvo);
    }

    public SitioResponseDTO atualizarSitio(Integer id, SitioRequestDTO dto, Usuario usuarioLogado) {
        Sitio sitio = buscarSitioSeguro(id, usuarioLogado);

        validarIntegridadeDosDados(dto);

        sitio.setNome(dto.getNome());

        sitio.setCnpj(extrairApenasNumeros(dto.getCnpj()));
        sitio.setCep(extrairApenasNumeros(dto.getCep()));

        sitio.setEndereco(dto.getEndereco());
        sitio.setMunicipio(dto.getMunicipio());
        sitio.setUf(dto.getUf());

        sitio = sitioRepository.save(sitio);
        return converterParaDTO(sitio);
    }

    public void deletarSitio(Integer id, Usuario usuarioLogado) {
        Sitio sitio = buscarSitioSeguro(id, usuarioLogado);
        sitioRepository.delete(sitio);
    }
}