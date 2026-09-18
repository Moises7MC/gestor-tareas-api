package pe.jllalle.gestortareasapi.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.jllalle.gestortareasapi.dto.CrearProyectoRequest;
import pe.jllalle.gestortareasapi.dto.ProyectoResponse;
import pe.jllalle.gestortareasapi.entity.Proyecto;
import pe.jllalle.gestortareasapi.service.ProyectoService;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody CrearProyectoRequest request) {
        Proyecto proyecto = proyectoService.crear(request.nombre(), request.descripcion(), request.propietarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProyectoResponse.desde(proyecto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> buscarPorId(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.buscarPorId(id);
        return ResponseEntity.ok(ProyectoResponse.desde(proyecto));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ProyectoResponse>> listarPorPropietario(@PathVariable Long usuarioId) {
        List<ProyectoResponse> proyectos = proyectoService.listarPorPropietario(usuarioId)
                .stream()
                .map(ProyectoResponse::desde)
                .toList();
        return ResponseEntity.ok(proyectos);
    }
}