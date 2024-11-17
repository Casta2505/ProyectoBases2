package co.edu.unbosque.ProyectoBases.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.ProyectoBases.Sql.model.Apartamento;
import co.edu.unbosque.ProyectoBases.Sql.repository.ApartamentoRepository;

@Controller
public class ApartamentoController {
	@Autowired
	private ApartamentoRepository aptoRep;

	@GetMapping("/MostrarApartamentos")
	public String mostrarApartamentos(Model model) {
		List<Apartamento> lista = aptoRep.findAll();
		model.addAttribute("apartamentos", lista);
		return "MostrarApartamentos";
	}

	@GetMapping("/AgregarApartamentoPagina")
	public String agregarCasaPagina() {
		return "AgregarApartamento";
	}

	@PostMapping("/AgregarApartamento")
	public String actualizarApartamento(Model model, @RequestParam("area") double area,
			@RequestParam("precio") double precio, @RequestParam("direccion") String direccion,
			@RequestParam("habitaciones") Integer habitaciones, @RequestParam("piso") Integer piso,
			@RequestParam(value = "balcon", defaultValue = "false") Boolean balcon,
			@RequestParam("numDePisosEdificio") Integer numDePisosEdificio,
			@RequestParam("urlImagen") String urlImagen) {
		
		Apartamento apartamentoAux = new Apartamento();
		apartamentoAux.setArea(area);
		apartamentoAux.setDireccion(direccion);
		apartamentoAux.setHabitaciones(habitaciones);
		apartamentoAux.setPiso(piso);
		apartamentoAux.setBalcon(balcon);
		apartamentoAux.setNumDePisosEdificio(numDePisosEdificio);
		apartamentoAux.setPrecio(precio);
		apartamentoAux.setUrlImagen(urlImagen);
		aptoRep.save(apartamentoAux);

		return "index";
	}
}
