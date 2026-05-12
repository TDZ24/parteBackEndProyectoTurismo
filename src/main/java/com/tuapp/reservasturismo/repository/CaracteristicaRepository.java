package com.tuapp.reservasturismo.repository;

import com.tuapp.reservasturismo.model.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    List<Caracteristica> findByProductoId(Long productoId);
}
