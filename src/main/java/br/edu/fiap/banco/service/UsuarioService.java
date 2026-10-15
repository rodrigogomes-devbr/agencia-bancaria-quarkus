package br.edu.fiap.banco.service;


import br.edu.fiap.banco.dto.UsuarioRequest;
import br.edu.fiap.banco.dto.UsuarioResponse;
import br.edu.fiap.banco.entity.Usuario;
import br.edu.fiap.banco.exception.EmailJaCadastradoException;
import br.edu.fiap.banco.repository.UsuarioRepository;
import br.edu.fiap.banco.security.SenhaBCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioService {
    private final UsuarioRepository usuario;
    private final SenhaBCrypt senhas;

    @Inject
    public UsuarioService(UsuarioRepository usuarios, SenhaBCrypt senhas){
        this.usuario = usuarios;
        this.senhas = senhas;
    }

    public UsuarioResponse cadastrarUsuario(UsuarioRequest request){
        if(usuario.existePorEmail(request.email())){
            throw new EmailJaCadastradoException();
        }

        String hash = senhas.criarHash(request.senha());

        Usuario novoUsuario = Usuario.novo(request.nome(), request.email(), hash);

        return UsuarioResponse.de(usuario.salvar(novoUsuario));
    }
}
