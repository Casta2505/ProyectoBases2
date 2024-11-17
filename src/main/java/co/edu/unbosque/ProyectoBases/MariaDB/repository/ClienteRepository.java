package co.edu.unbosque.ProyectoBases.MariaDB.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	public List<Cliente> findAll();

	public Optional<Cliente> findById(long idCliente);

	public void deleteById(long idCliente);

	public Optional<Cliente> findByEmail(String email);
}
