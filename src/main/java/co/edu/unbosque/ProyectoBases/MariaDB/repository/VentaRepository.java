package co.edu.unbosque.ProyectoBases.MariaDB.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Venta;

public interface VentaRepository extends CrudRepository<Venta, Long> {
	public List<Venta> findAll();
	public Optional<Venta> findById(Long idCliente);
	public void deleteById(Long idCliente);
}
