package co.edu.unbosque.ProyectoBases.MariaDB.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Cliente idCliente;
	private double precio;
	private LocalDate fecha;
	private Integer idCasa;
	private Integer idApartamento;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public Integer getIdCasa() {
		return idCasa;
	}
	public void setIdCasa(Integer idCasa) {
		this.idCasa = idCasa;
	}
	public Integer getIdApartamento() {
		return idApartamento;
	}
	public void setIdApartamento(Integer idApartamento) {
		this.idApartamento = idApartamento;
	}
	
	
}
