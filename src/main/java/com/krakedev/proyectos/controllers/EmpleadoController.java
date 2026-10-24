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

import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.services.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
	//controller de empleado
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping("/")
    public ResponseEntity<?> guardar(@RequestBody Empleado empleado) {
        try {
            Empleado guardado = empleadoService.guardar(empleado);
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al guardar el empleado: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> listar() {
        try {
            List<Empleado> empleados = empleadoService.listar();
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al listar los empleados: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        try {
            Empleado empleado = empleadoService.buscarPorId(id);
            return ResponseEntity.ok(empleado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al buscar el empleado: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Empleado empleado) {
        try {
            Empleado actualizado = empleadoService.actualizar(id, empleado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al actualizar el empleado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {
        try {
            empleadoService.eliminar(id);
            return ResponseEntity.ok("Empleado eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al eliminar el empleado: " + e.getMessage());
        }
    }
}