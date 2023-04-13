package br.com.projeto.manager.service;

import br.com.projeto.manager.dto.UsuarioAutenticadoDTO;
import br.com.projeto.manager.entity.Token;
import br.com.projeto.manager.entity.Usuario;
import br.com.projeto.manager.repository.TokenRepository;
import br.com.projeto.manager.repository.UsuarioRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
   @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final TokenRepository tokenRepository;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, TokenRepository tokenRepository, TokenService tokenService, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    public Usuario saveUser(Usuario usuario) {
        System.out.print(usuario);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public UsuarioAutenticadoDTO autenticar(String login, String senha){

        Optional<Usuario> optUsuario = usuarioRepository.findByLogin(login);

        boolean validar = false;
        UsuarioAutenticadoDTO usuarioAutenticadoDTO = null;

        if (optUsuario.isEmpty()) {
            usuarioAutenticadoDTO = UsuarioAutenticadoDTO.builder()
                    .token("")
                    .login(login)
                    .autenticado(validar)
                    .administrador(false)
                    .build();
            return usuarioAutenticadoDTO;

        }
        validar = encoder.matches(senha,optUsuario.get().getSenha());

        if (!validar) {
            usuarioAutenticadoDTO = UsuarioAutenticadoDTO.builder()
                    .token("")
                    .login(login)
                    .autenticado(validar)
                    .administrador(optUsuario.get().getAdministrador())
                    .build();
            return usuarioAutenticadoDTO;

        }

        Token tokenEntity = tokenService.gerarToken(optUsuario);
        tokenRepository.save(tokenEntity);

        usuarioAutenticadoDTO = UsuarioAutenticadoDTO.builder()
                .token(tokenEntity.getToken())
                .login(optUsuario.get().getLogin())
                .autenticado(validar)
                    .administrador(optUsuario.get().getAdministrador())
                .build();

        return usuarioAutenticadoDTO;

    }
    public Boolean renovarTicket(String token){
        Optional<Token> tokenEntity = tokenRepository.findByToken(token);

        if(tokenEntity.isEmpty()){
            return false;
        }
        var usuario = usuarioRepository.findByLogin(tokenEntity.get().getLogin());

        return tokenService.getRenova(usuario, tokenEntity);
    }


}
