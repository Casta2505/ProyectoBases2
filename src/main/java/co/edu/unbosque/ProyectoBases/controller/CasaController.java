package co.edu.unbosque.ProyectoBases.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Cliente;
import co.edu.unbosque.ProyectoBases.PostGres.model.Casa;
import co.edu.unbosque.ProyectoBases.PostGres.repository.CasaRepository;

@Controller
@SessionAttributes("casa")
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
	public String mostarCasas(Model model, @ModelAttribute("cuenta") Cliente cliente) {
		List<Casa> lista = casaRep.findAll();
		model.addAttribute("casas", lista);
		model.addAttribute("cuenta", cliente);
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
			@RequestParam(value = "jardin", defaultValue = "no") String jardinStr,
			@RequestParam("imagen") String imagen) throws IOException {
		Casa casaAux = new Casa();
		boolean jardin = jardinStr.equals("si");
		casaAux.setArea(area);
		casaAux.setDireccion(direccion);
		casaAux.setHabitaciones(habitaciones);
		casaAux.setJardin(jardin);
		casaAux.setNumDePisos(numDePisos);
		casaAux.setPrecio(precio);
		casaAux.setUrlImagen(imagen);
		casaAux.setComprada(false);
		this.casaRep.save(casaAux);
		return "IndexAdmin";
	}

	@GetMapping("/ComprarCasaEnvio")
	public String comprarCasa(Model model, @RequestParam("id") Long id) {
		Optional<Casa> casa = casaRep.findById(id);
		model.addAttribute("casa", casa.get());
		return "ComprarPropiedad";
	}
}
