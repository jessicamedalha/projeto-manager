package br.com.projeto.manager.repository;

import br.com.projeto.manager.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaisRepository extends JpaRepository<Pais, Long> {

}
