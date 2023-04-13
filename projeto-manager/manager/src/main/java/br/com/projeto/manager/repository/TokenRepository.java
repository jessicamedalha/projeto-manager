package br.com.projeto.manager.repository;

import br.com.projeto.manager.entity.Token;
import br.com.projeto.manager.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository  extends JpaRepository<Token, Long> {
    public Optional<Token> findByToken(String token);

}
