package apap.tutorial.belajarbelajar.repository;

import apap.tutorial.belajarbelajar.model.PenyelenggaraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PenyelenggaraDb extends JpaRepository<PenyelenggaraModel, Long>  {
    Optional<PenyelenggaraModel> findByNamaPenyelenggara(String namaPenyelenggara);
}