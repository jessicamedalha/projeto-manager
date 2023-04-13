package br.com.projeto.manager.controller;

import br.com.projeto.manager.dto.UsuarioAutenticadoDTO;
import br.com.projeto.manager.entity.Usuario;
import br.com.projeto.manager.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/saveUser")
    @ResponseStatus(HttpStatus.OK)
    public Usuario saveUser(@RequestBody Usuario usuario){
        return usuarioService.saveUser(usuario);
    }

    @GetMapping("/listarTodos")
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @PostMapping("/autenticar")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioAutenticadoDTO autenticar(@RequestParam  String login, @RequestParam String senha) {
        return usuarioService.autenticar(login, senha);
    }

    @GetMapping("/renovar-ticket")
    @ResponseStatus(HttpStatus.OK)
    public Boolean renovarTicket(@RequestParam  String token){

        return usuarioService.renovarTicket(token);
    }

}
