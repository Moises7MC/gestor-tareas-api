package pe.jllalle.gestortareasapi.service;

import org.springframework.stereotype.Service;
import pe.jllalle.gestortareasapi.entity.EstadoTarea;
import pe.jllalle.gestortareasapi.entity.Proyecto;
import pe.jllalle.gestortareasapi.entity.Tarea;
import pe.jllalle.gestortareasapi.entity.Usuario;
import pe.jllalle.gestortareasapi.exception.RecursoNoEncontradoException;
import pe.jllalle.gestortareasapi.repository.ProyectoRepository;
import pe.jllalle.gestortareasapi.repository.TareaRepository;
import pe.jllalle.gestortareasapi.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaServiceImpl(TareaRepository tareaRepository, ProyectoRepository proyectoRepository, UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Tarea crear(String titulo, String descripcion, LocalDate fechaLimite, Long proyectoId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el proyecto con id " + proyectoId));

        Tarea tarea = new Tarea();
        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
        tarea.setFechaLimite(fechaLimite);
        tarea.setEstado(EstadoTarea.PENDIENTE);
        tarea.setProyecto(proyecto);

        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea buscarPorId(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la tarea con id " + id));
    }

    @Override
    public Tarea asignarUsuario(Long tareaId, Long usuarioId) {
        Tarea tarea = buscarPorId(tareaId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con id " + usuarioId));

        tarea.setAsignadoA(usuario);
        return tareaRepository.save(tarea);
    }

    @Override
    public Tarea cambiarEstado(Long tareaId, EstadoTarea nuevoEstado) {
        Tarea tarea = buscarPorId(tareaId);
        tarea.setEstado(nuevoEstado);
        return tareaRepository.save(tarea);
    }

    @Override
    public List<Tarea> listarPorProyecto(Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId);
    }

    @Override
    public List<Tarea> listarPorUsuarioAsignado(Long usuarioId) {
        return tareaRepository.findByAsignadoAId(usuarioId);
    }
}

