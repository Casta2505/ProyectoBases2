package co.edu.unbosque.ProyectoBases.PostGres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoBases.PostGres.model.Casa;

public interface CasaRepository extends CrudRepository<Casa, Long> {
	public List<Casa> findAll();

	public Optional<Casa> findById(Long idCompra);

	public void deleteById(Integer idCompra);

	public List<Casa> findAllByComprada(boolean comprada);
}
