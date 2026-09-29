package pe.jllalle.gestortareasapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.jllalle.gestortareasapi.entity.Proyecto;
import pe.jllalle.gestortareasapi.entity.Usuario;
import pe.jllalle.gestortareasapi.exception.RecursoNoEncontradoException;
import pe.jllalle.gestortareasapi.repository.ProyectoRepository;
import pe.jllalle.gestortareasapi.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceImplTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ProyectoServiceImpl proyectoService;

    @Test
    void crear_deberiaCrearProyectoConPropietarioExistente() {
        Usuario propietario = new Usuario();
        propietario.setId(36L);
        propietario.setNombre("Carlos Ruiz");

        Proyecto guardado = new Proyecto();
        guardado.setId(1L);
        guardado.setNombre("Rediseño Web");
        guardado.setDescripcion("Actualizar el sitio institucional");
        guardado.setPropietario(propietario);

        when(usuarioRepository.findById(36L)).thenReturn(Optional.of(propietario));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(guardado);

        Proyecto resultado = proyectoService.crear("Rediseño Web", "Actualizar el sitio institucional", 36L);

        assertEquals("Rediseño Web", resultado.getNombre());
        assertEquals(propietario, resultado.getPropietario());
        verify(proyectoRepository).save(any(Proyecto.class));
    }

    @Test
    void crear_deberiaLanzarExcepcion_siPropietarioNoExiste() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () ->
                proyectoService.crear("Rediseño Web", "Descripcion", 99L));

        verify(proyectoRepository, never()).save(any());
    }
}

