package co.edu.unbosque.ProyectoBases.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Cliente;
import co.edu.unbosque.ProyectoBases.MariaDB.model.Compra;
import co.edu.unbosque.ProyectoBases.MariaDB.repository.ClienteRepository;
import co.edu.unbosque.ProyectoBases.MariaDB.repository.CompraRepository;
import co.edu.unbosque.ProyectoBases.PostGres.model.Casa;
import co.edu.unbosque.ProyectoBases.PostGres.repository.CasaRepository;
import co.edu.unbosque.ProyectoBases.Sql.model.Apartamento;
import co.edu.unbosque.ProyectoBases.Sql.repository.ApartamentoRepository;

@Controller
@SessionAttributes({ "cuenta", "casa" })
public class CompraController {
	@Autowired
	private CompraRepository comRep;
	@Autowired
	private ClienteRepository clienteRep;
	@Autowired
	private CasaRepository casaRep;
	@Autowired
	private ApartamentoRepository aptoRep;

	@GetMapping("/ComprarPropiedad")
	public String comprarCasa(Model model, @SessionAttribute("cuenta") Cliente cliente,
			@SessionAttribute("casa") Object aux) {
		try {
			Casa casa = (Casa) aux;
			model.addAttribute("cliente", cliente);
			Compra compra = new Compra();
			compra.setFecha(LocalDate.now());
			compra.setIdApartamento((long) 0);
			compra.setIdCasa(casa.getId());
			compra.setIdCliente(cliente);
			compra.setPrecio(casa.getPrecio());
			model.addAttribute("cuenta", cliente);
			comRep.save(compra);
		} catch (Exception e) {
			e.printStackTrace();
			Apartamento casa = (Apartamento) aux;
			model.addAttribute("cliente", cliente);
			Compra compra = new Compra();
			compra.setFecha(LocalDate.now());
			compra.setIdApartamento(casa.getId());
			compra.setIdCasa((long) 0);
			compra.setIdCliente(cliente);
			compra.setPrecio(casa.getPrecio());
			model.addAttribute("cuenta", cliente);
			comRep.save(compra);
		}
		return "IndexCliente";
	}
}
