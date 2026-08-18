package sifeo.tcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sifeo.tcc.models.entities.TipoAtividade;

@Repository
public interface TipoAtividadeRepository extends JpaRepository<TipoAtividade, Integer> {
}