package br.edu.infnet.controller;

import br.edu.infnet.model.domain.Usuario;
import br.edu.infnet.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = {"/usuarios"})
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Usuario> obterPorId(@PathVariable Integer id){

        ResponseEntity<Usuario> retorno = ResponseEntity.notFound().build();
        Usuario usuario = this.findById(id);

        if(usuario != null){

            retorno = ResponseEntity.ok().body(usuario);
        }

        return retorno;
    }

    private Usuario findById(Integer id) {

        Usuario retorno = null;
        try {
            retorno = usuarioRepository.findById(id).get();

        } catch (Exception e){
        }
            return retorno;
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<Usuario> obterPorEmail(@PathVariable String email){

        ResponseEntity<Usuario> retorno = ResponseEntity.notFound().build();

        try{
            Usuario usuario = usuarioRepository.findByEmail(email);

            retorno = ResponseEntity.ok().body(usuario);
        } catch (Exception e){

        }
        return retorno;
    }

    @PostMapping
    public ResponseEntity<Usuario> criarConta(@RequestBody Usuario usuario){

        ResponseEntity<Usuario> retorno = ResponseEntity.badRequest().build();

        if (usuario != null && usuario.getId() == null){

            Usuario usuarioInserido = usuarioRepository.save(usuario);
            retorno = ResponseEntity.status(HttpStatus.CREATED).body(usuarioInserido);
        }

        return retorno;

    }

    @PutMapping
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){

        ResponseEntity<Usuario> retorno = ResponseEntity.badRequest().build();

        if (usuario != null && usuario.getId() == null){

            Usuario usuarioGravado = this.findById(usuario.getId());

            if (usuarioGravado != null){

                try {
                    usuarioGravado = usuarioRepository.save(usuario);
                    retorno = ResponseEntity.ok().body(usuarioGravado);
                } catch (Exception e){

                }
            }

        }

            return retorno;
    }


}
