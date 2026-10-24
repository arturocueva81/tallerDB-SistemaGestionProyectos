package com.krakedev.proyectos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.krakedev.proyectos.entidades.Proyecto;
import com.krakedev.proyectos.repositories.ProyectoRepository;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public Proyecto guardar(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public List<Proyecto> listar() {
        return proyectoRepository.findAll();
    }

    public Proyecto buscarPorId(int id) {
        try {
            Optional<Proyecto> resultado = proyectoRepository.findById(id);
            if (resultado.isPresent()) {
                return resultado.get();
            } else {
                throw new RuntimeException("Proyecto no encontrado con id: " + id);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el proyecto: " + e.getMessage());
        }
    }

    public Proyecto actualizar(int id, Proyecto proyectoActualizado) {
        try {
            Proyecto existente = buscarPorId(id);
            existente.setNombre(proyectoActualizado.getNombre());
            existente.setDescripcion(proyectoActualizado.getDescripcion());
            existente.setFechaInicio(proyectoActualizado.getFechaInicio());
            return proyectoRepository.save(existente);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el proyecto: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        try {
            buscarPorId(id);
            proyectoRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el proyecto: " + e.getMessage());
        }
    }
}