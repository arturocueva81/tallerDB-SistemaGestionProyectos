package com.krakedev.proyectos.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.services.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectoController {
	//controller de proyecto
    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping("/")
    public ResponseEntity<?> guardar(@RequestBody Proyecto proyecto) {
        try {
            Proyecto guardado = proyectoService.guardar(proyecto);
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al guardar el proyecto: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> listar() {
        try {
            List<Proyecto> proyectos = proyectoService.listar();
            return ResponseEntity.ok(proyectos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al listar los proyectos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Proyecto proyecto = proyectoService.buscarPorId(id);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al buscar el proyecto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Proyecto proyecto) {
        try {
            Proyecto actualizado = proyectoService.actualizar(id, proyecto);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el proyecto: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            proyectoService.eliminar(id);
            return ResponseEntity.ok("Proyecto eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar el proyecto: " + e.getMessage());
        }
    }
}
