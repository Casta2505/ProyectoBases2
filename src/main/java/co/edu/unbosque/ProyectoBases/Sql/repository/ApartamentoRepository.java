package co.edu.unbosque.ProyectoBases.Sql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoBases.Sql.model.Apartamento;

public interface ApartamentoRepository extends CrudRepository<Apartamento, Long> {
	public List<Apartamento> findAll();

	public Optional<Apartamento> findById(Long idCompra);

	public void deleteById(Integer idCompra);
}
