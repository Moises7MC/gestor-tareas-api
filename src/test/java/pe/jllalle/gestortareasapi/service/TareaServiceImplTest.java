package pe.jllalle.gestortareasapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.jllalle.gestortareasapi.entity.EstadoTarea;
import pe.jllalle.gestortareasapi.entity.Proyecto;
import pe.jllalle.gestortareasapi.entity.Tarea;
import pe.jllalle.gestortareasapi.entity.Usuario;
import pe.jllalle.gestortareasapi.repository.ProyectoRepository;
import pe.jllalle.gestortareasapi.repository.TareaRepository;
import pe.jllalle.gestortareasapi.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TareaServiceImplTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TareaServiceImpl tareaService;

    @Test
    void crear_deberiaCrearTareaConEstadoPendiente() {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Rediseño Web");

        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tarea resultado = tareaService.crear("Diseñar landing", "Boceto inicial", LocalDate.of(2026, 10, 1), 1L);

        assertEquals(EstadoTarea.PENDIENTE, resultado.getEstado());
        assertEquals("Diseñar landing", resultado.getTitulo());
        assertEquals(proyecto, resultado.getProyecto());
    }

    @Test
    void asignarUsuario_deberiaAsignarUsuarioALaTarea() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setEstado(EstadoTarea.PENDIENTE);

        Usuario usuario = new Usuario();
        usuario.setId(36L);
        usuario.setNombre("Carlos Ruiz");

        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(usuarioRepository.findById(36L)).thenReturn(Optional.of(usuario));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tarea resultado = tareaService.asignarUsuario(1L, 36L);

        assertEquals(usuario, resultado.getAsignadoA());
    }

    @Test
    void cambiarEstado_deberiaActualizarElEstado() {
        Tarea tarea = new Tarea();
        tarea.setId(1L);
        tarea.setEstado(EstadoTarea.PENDIENTE);

        when(tareaRepository.findById(1L)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tarea resultado = tareaService.cambiarEstado(1L, EstadoTarea.EN_PROGRESO);

        assertEquals(EstadoTarea.EN_PROGRESO, resultado.getEstado());
    }
}