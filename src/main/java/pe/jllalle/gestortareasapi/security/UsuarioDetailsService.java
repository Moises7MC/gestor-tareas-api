package pe.jllalle.gestortareasapi.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.jllalle.gestortareasapi.entity.Usuario;
import pe.jllalle.gestortareasapi.repository.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Spring Security llama automáticamente a este método cuando necesita cargar un usuario.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Busca al usuario en la BD
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email " + email));

        //User es una clase que Spring Security utiliza para representar al usuario durante la autenticación.
        return User.builder()
                .username(usuario.getEmail()) //Le dices a Spring Security: "El username de este usuario será su email."
                .password(usuario.getPassword()) //Le dices a Spring Security: "El password de este usuario será la contraseña almacenada."
                //Spring Security posteriormente utiliza esa contraseña almacenada para comparar la contraseña que escribió el usuario.
                .authorities("ROLE_" + usuario.getRol().name())// (permisos/roles)
                //Spring Security espera que los roles empiecen con el prefijo ROLE_ por convención (así que ADMIN se convierte en ROLE_ADMIN)
                .build();
    }
}