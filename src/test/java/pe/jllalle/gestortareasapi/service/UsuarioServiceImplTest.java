package pe.jllalle.gestortareasapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.jllalle.gestortareasapi.entity.Rol;
import pe.jllalle.gestortareasapi.entity.Usuario;
import pe.jllalle.gestortareasapi.exception.RecursoDuplicadoException;
import pe.jllalle.gestortareasapi.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void registrar_deberiaGuardarUsuarioConPasswordEncriptado() {
        // Arrange: preparamos los datos y le decimos a los mocks cómo comportarse
        Usuario guardado = new Usuario();
        guardado.setId(1L);
        guardado.setNombre("Juan Perez");
        guardado.setEmail("juan@example.com");
        guardado.setPassword("hash123");
        guardado.setRol(Rol.USER);

        when(usuarioRepository.findByEmail("juan@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hash123");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);

        // Act: ejecutamos el método que queremos probar
        Usuario resultado = usuarioService.registrar("Juan Perez", "juan@example.com", "password123", Rol.USER);

        // Assert: confirmamos que el resultado es el esperado
        assertEquals("juan@example.com", resultado.getEmail());
        assertEquals("hash123", resultado.getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void registrar_deberiaLanzarExcepcion_siEmailYaExiste() {
        when(usuarioRepository.findByEmail("juan@example.com"))
                .thenReturn(Optional.of(new Usuario()));

        assertThrows(RecursoDuplicadoException.class, () ->
                usuarioService.registrar("Juan Perez", "juan@example.com", "password123", Rol.USER));

        verify(usuarioRepository, never()).save(any());
    }
}