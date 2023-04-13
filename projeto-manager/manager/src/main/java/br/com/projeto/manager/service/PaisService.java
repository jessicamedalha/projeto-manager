package br.com.projeto.manager.service;

import br.com.projeto.manager.entity.Pais;
import br.com.projeto.manager.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisService {

    @Autowired
    private final  PaisRepository paisRepository;

    @Autowired
    private final TokenService tokenService;

    public PaisService(PaisRepository paisRepository, TokenService tokenService) {
        this.paisRepository = paisRepository;
        this.tokenService = tokenService;
    }

    public ResponseEntity<List<Pais>> listaPaises(String token){
        var verifica_token = tokenService.getSubject(token);
        if(verifica_token != null){
            return ResponseEntity.status(HttpStatus.OK).body(paisRepository.findAll());
        }
        Pais paises = new Pais();
        List<Pais> vazio = List.of(paises);

       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(vazio);
    }

    public Pais salvar(Pais pais){
        return paisRepository.save(pais);
    }

    public Optional<Pais> buscarPorId(Long id){
        return paisRepository.findById(id);
    }

    public void removerPorId(Long id){
        paisRepository.deleteById(id);
    }
}
