package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.krakedev.proyectos.entidades.Empleado;
import com.krakedev.proyectos.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    public Empleado buscarPorId(int id) {
        try {
            Optional<Empleado> resultado = empleadoRepository.findById(id);
            if (resultado.isPresent()) {
                return resultado.get();
            } else {
                throw new RuntimeException("Empleado no encontrado con id: " + id);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el empleado: " + e.getMessage());
        }
    }

    public Empleado actualizar(int id, Empleado empleadoActualizado) {
        try {
            Empleado existente = buscarPorId(id);
            existente.setNombre(empleadoActualizado.getNombre());
            existente.setCargo(empleadoActualizado.getCargo());
            return empleadoRepository.save(existente);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el empleado: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            buscarPorId(id);
            empleadoRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el empleado: " + e.getMessage());
        }
    }
}