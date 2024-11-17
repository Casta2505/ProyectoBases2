package co.edu.unbosque.ProyectoBases.MariaDB.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Compra;

public interface CompraRepository extends CrudRepository<Compra, Long>{
    public List<Compra> findAll();
	public Optional<Compra> findById(Long idCompra);
	public void deleteById(Long idCompra);
}
