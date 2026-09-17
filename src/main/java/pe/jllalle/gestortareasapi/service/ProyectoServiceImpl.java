package pe.jllalle.gestortareasapi.service;

import org.springframework.stereotype.Service;
import pe.jllalle.gestortareasapi.entity.Proyecto;
import pe.jllalle.gestortareasapi.entity.Usuario;
import pe.jllalle.gestortareasapi.exception.RecursoNoEncontradoException;
import pe.jllalle.gestortareasapi.repository.ProyectoRepository;
import pe.jllalle.gestortareasapi.repository.UsuarioRepository;

import java.util.List;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, UsuarioRepository usuarioRepository) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Proyecto crear(String nombre, String descripcion, Long propietarioId) {
        Usuario propietario = usuarioRepository.findById(propietarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con id " + propietarioId));

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setPropietario(propietario);

        return proyectoRepository.save(proyecto);
    }

    @Override
    public List<Proyecto> listarPorPropietario(Long propietarioId) {
        return proyectoRepository.findByPropietarioId(propietarioId);
    }

    @Override
    public Proyecto buscarPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el proyecto con id " + id));
    }
}

