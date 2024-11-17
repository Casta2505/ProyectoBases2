package co.edu.unbosque.ProyectoBases.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.ProyectoBases.PostGres.model.Casa;
import co.edu.unbosque.ProyectoBases.PostGres.repository.CasaRepository;

@Controller
public class CasaController {
	@Autowired
	private CasaRepository casaRep;

	@GetMapping("/")
	public String inicio(Model model) {
		model.addAttribute("mensaje", "");
		return "Login";
	}

	@GetMapping("/IndexCliente")
	public String indexCliente(Model model) {
		return "IndexCliente";
	}

	@GetMapping("/IndexAdmin")
	public String indexAdmin(Model model) {
		return "IndexAdmin";
	}

	@GetMapping("/MostrarCasas")
	public String mostarCasas(Model model) {
		List<Casa> lista = casaRep.findAll();
		if (lista.isEmpty()) {
			return "index";
		}
		model.addAttribute("casas", lista);
		return "MostrarCasas";
	}

	@GetMapping("/AgregarCasaPagina")
	public String agregarCasaPagina() {
		return "AgregarCasa";
	}

	@PostMapping("/AgregarCasa")
	public String actualizarCasa(Model model, @RequestParam("area") double area, @RequestParam("precio") double precio,
			@RequestParam("direccion") String direccion, @RequestParam("habitaciones") Integer habitaciones,
			@RequestParam("numDePisos") Integer numDePisos,
			@RequestParam(value = "jardin", defaultValue = "false") boolean jardin,
			@RequestParam("urlImagen") String urlImagen) {
		Casa casaAux = new Casa();
		casaAux.setArea(area);
		casaAux.setDireccion(direccion);
		casaAux.setHabitaciones(habitaciones);
		casaAux.setJardin(jardin);
		casaAux.setNumDePisos(numDePisos);
		casaAux.setPrecio(precio);
		casaAux.setUrlImagen(urlImagen);
		this.casaRep.save(casaAux);
		return "index";
	}
}
