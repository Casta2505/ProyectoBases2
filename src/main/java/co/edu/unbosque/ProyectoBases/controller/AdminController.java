package co.edu.unbosque.ProyectoBases.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.ProyectoBases.PostGres.model.Casa;
import co.edu.unbosque.ProyectoBases.PostGres.repository.CasaRepository;
import co.edu.unbosque.ProyectoBases.Sql.model.Apartamento;
import co.edu.unbosque.ProyectoBases.Sql.repository.ApartamentoRepository;

@Controller
public class AdminController {
	@Autowired
	private CasaRepository casaRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@GetMapping("/Propiedades")
	public String getPropiedades(Model model) {
		model.addAttribute("casas", casaRepository.findAll());
		model.addAttribute("apartamentos", apartamentoRepository.findAll());
		return "AdminTabla";
	}

	@PostMapping("/actualizarCasa")
	public String actualizarCasa(Model model, @RequestParam Long id, @RequestParam String field,
			@RequestParam String value) {
		Casa casa = casaRepository.findById(id).orElseThrow();

		if (field.equals("area")) {
			casa.setArea(Double.parseDouble(value));
		} else if (field.equals("precio")) {
			casa.setPrecio(Double.parseDouble(value));
		} else if (field.equals("habitaciones")) {
			casa.setHabitaciones(Integer.parseInt(value));
		} else if (field.equals("direccion")) {
			casa.setDireccion(value);
		}

		casaRepository.save(casa);
		return "AdminTabla";
	}

	@PostMapping("/actualizarApartamento")
	public String actualizarApartamento(@RequestParam Long id, @RequestParam String field, @RequestParam String value) {
		Apartamento apartamento = apartamentoRepository.findById(id).orElseThrow();

		if (field.equals("area")) {
			apartamento.setArea(Double.parseDouble(value));
		} else if (field.equals("precio")) {
			apartamento.setPrecio(Double.parseDouble(value));
		} else if (field.equals("habitaciones")) {
			apartamento.setHabitaciones(Integer.parseInt(value));
		} else if (field.equals("direccion")) {
			apartamento.setDireccion(value);
		}

		apartamentoRepository.save(apartamento);
		return "AdminTabla";
	}

	@PostMapping("/eliminarCasa")
	public String eliminarCasa(Model model, @RequestParam("id") Long id) {
		try {
			casaRepository.deleteById(id);
		} catch (Exception e) {

		}
		model.addAttribute("casas", casaRepository.findAll());
		model.addAttribute("apartamentos", apartamentoRepository.findAll());
		return "AdminTabla";
	}

	@PostMapping("/eliminarApartamento")
	public String eliminarApartamento(Model model, @RequestParam("id") Long id) {
		try {
			apartamentoRepository.deleteById(id);
		} catch (Exception e) {

		}
		model.addAttribute("casas", casaRepository.findAll());
		model.addAttribute("apartamentos", apartamentoRepository.findAll());
		return "AdminTabla";
	}

}
