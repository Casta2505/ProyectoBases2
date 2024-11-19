package co.edu.unbosque.ProyectoBases.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Cliente;
import co.edu.unbosque.ProyectoBases.MariaDB.model.Venta;
import co.edu.unbosque.ProyectoBases.MariaDB.repository.VentaRepository;
import co.edu.unbosque.ProyectoBases.PostGres.model.Casa;
import co.edu.unbosque.ProyectoBases.PostGres.repository.CasaRepository;
import co.edu.unbosque.ProyectoBases.Sql.model.Apartamento;
import co.edu.unbosque.ProyectoBases.Sql.repository.ApartamentoRepository;

@Controller
public class VentaController {
	@Autowired
	private VentaRepository ventaRep;
	@Autowired
	private CasaRepository casaRep;
	@Autowired
	private ApartamentoRepository aptoRep;

	@GetMapping("/MostrarVenderCasa")
	public String ventaCasa(Model model) {
		return "VenderCasa";
	}

	@GetMapping("/MostrarVenderApartamento")
	public String ventaApartamento(Model model) {
		return "VenderApartamento";
	}

	@PostMapping("/VenderCasa")
	public String venderCasa(@SessionAttribute("cuenta") Cliente cliente, Model model,
			@RequestParam("area") double area, @RequestParam("precio") double precio,
			@RequestParam("direccion") String direccion, @RequestParam("habitaciones") Integer habitaciones,
			@RequestParam("numDePisos") Integer numDePisos,
			@RequestParam(value = "jardin", defaultValue = "no") String jardinStr,
			@RequestParam("imagen") String imagen) throws IOException {
		Casa casa = new Casa();
		boolean jardin = jardinStr.equals("si");
		casa.setArea(area);
		casa.setComprada(false);
		casa.setDireccion(direccion);
		casa.setHabitaciones(habitaciones);
		casa.setJardin(jardin);
		casa.setNumDePisos(numDePisos);
		casa.setPrecio(precio);
		casa.setUrlImagen(imagen);
		this.casaRep.save(casa);

		Venta venta = new Venta();
		venta.setFecha(LocalDate.now());
		venta.setIdCasa(casa.getId());
		venta.setIdCliente(cliente);
		venta.setIdApartamento((long) 0);
		venta.setPrecio(precio);
		this.ventaRep.save(venta);
		model.addAttribute("cuenta", cliente);
		return "IndexCliente";
	}

	@PostMapping("/VenderApartamento")
	public String venderApartamento(@SessionAttribute("cuenta") Cliente cliente, Model model,
			@RequestParam("area") double area, @RequestParam("precio") double precio,
			@RequestParam("direccion") String direccion, @RequestParam("habitaciones") Integer habitaciones,
			@RequestParam("piso") Integer piso, @RequestParam(value = "balcon", defaultValue = "false") Boolean balcon,
			@RequestParam("numDePisosEdificio") Integer numDePisosEdificio,
			@RequestParam("urlImagen") String urlImagen) {
		Apartamento apto = new Apartamento();
		apto.setArea(area);
		apto.setBalcon(balcon);
		apto.setComprada(balcon);
		apto.setDireccion(direccion);
		apto.setHabitaciones(habitaciones);
		apto.setNumDePisosEdificio(numDePisosEdificio);
		apto.setPiso(piso);
		apto.setPrecio(precio);
		apto.setUrlImagen(urlImagen);
		this.aptoRep.save(apto);

		Venta venta = new Venta();
		venta.setFecha(LocalDate.now());
		venta.setIdApartamento(apto.getId());
		venta.setIdCasa((long) 0);
		venta.setIdCliente(cliente);
		venta.setPrecio(precio);
		this.ventaRep.save(venta);
		model.addAttribute("cuenta", cliente);
		return "IndexCliente";
	}
}
