package br.com.projeto.manager.controller;

import br.com.projeto.manager.entity.Pais;
import br.com.projeto.manager.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/pais")
public class PaisController {
    @Autowired
    private PaisService paisService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Pais>> listaPaises(@RequestParam  String token){
        return paisService.listaPaises(token);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Pais salvar(@RequestBody Pais pais){
        return paisService.salvar(pais);
    }

   /* @GetMapping("/pesquisar")
    @ResponseStatus(HttpStatus.OK)
    public Pais buscarPorId(){
        return paisService.buscarPorId();
    }*/

    @GetMapping("/excluir")
    @ResponseStatus(HttpStatus.OK)
    public void removerPorId(@RequestBody Long id){
        paisService.removerPorId(id);
    }
}
