package co.edu.unbosque.ProyectoBases.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Cliente;
import co.edu.unbosque.ProyectoBases.MariaDB.model.Compra;
import co.edu.unbosque.ProyectoBases.MariaDB.model.Venta;
import co.edu.unbosque.ProyectoBases.MariaDB.repository.ClienteRepository;
import co.edu.unbosque.ProyectoBases.MariaDB.repository.CompraRepository;
import co.edu.unbosque.ProyectoBases.MariaDB.repository.VentaRepository;
import co.edu.unbosque.ProyectoBases.PostGres.model.Casa;
import co.edu.unbosque.ProyectoBases.PostGres.repository.CasaRepository;
import co.edu.unbosque.ProyectoBases.Sql.model.Apartamento;
import co.edu.unbosque.ProyectoBases.Sql.repository.ApartamentoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteController {
	@Autowired
	public ClienteRepository rep;
	@Autowired
	private CasaRepository casaRep;
	@Autowired
	private ApartamentoRepository aptoRep;
	@Autowired
	private CompraRepository compraRep;
	@Autowired
	private VentaRepository ventaRep;

	@PostMapping("/login")
	public String login(Model model, @RequestParam("user") String user, @RequestParam("password") String password,
			HttpSession session) {
		Optional<Cliente> usuAux = rep.findByEmail(user);
		if (usuAux.isPresent()) {
			if (usuAux.get().getContrasena().equals(password)) {
				if (usuAux.get().getEmail().equals("admin")) {
					session.setAttribute("cuenta", usuAux.get());
					return "IndexAdmin";
				}
				session.setAttribute("cuenta", usuAux.get());
				return "IndexCliente";
			}
		}
		model.addAttribute("mensaje", "Usuario/Contraseña inválidos");
		return "Login";
	}

	@PostMapping("/register")
	public String registrarse(Model model, @RequestParam("nombre") String nombre, @RequestParam("email") String email,
			@RequestParam("telefono") String telefono, @RequestParam("direccion") String direccion,
			@RequestParam("contrasena") String contrasena) {
		Cliente cliente = new Cliente();
		cliente.setContrasena(contrasena);
		cliente.setDireccion(direccion);
		cliente.setEmail(email);
		cliente.setNombre(nombre);
		cliente.setTelefono(telefono);
		rep.save(cliente);
		model.addAttribute("mensaje", "");
		return "IndexCliente";
	}

	@GetMapping("/GestorPropiedades")
	public String gestionar(Model model) {
		return "GestorPropiedades";
	}

	@GetMapping("/verCompras")
	public String verCompras(Model model, @SessionAttribute("cuenta") Cliente cliente) {
		List<Compra> compras = compraRep.findAllByIdCliente(cliente);
		List<Casa> casas = new ArrayList<Casa>();
		List<Apartamento> apartamentos = new ArrayList<Apartamento>();
		for (Compra compra : compras) {
			if (compra.getIdApartamento() == 0) {
				Optional<Casa> casaOpt = casaRep.findById(compra.getIdCasa());
				if (casaOpt.isPresent()) {
					casas.add(casaOpt.get());
				} else {
					System.out.println("No se encontró la casa con ID: " + compra.getIdCasa());
					casas.add(new Casa());
				}
				apartamentos.add(new Apartamento());
			} else {
				Optional<Apartamento> aptoOpt = aptoRep.findById(compra.getIdApartamento());
				if (aptoOpt.isPresent()) {
					apartamentos.add(aptoOpt.get());
				} else {
					System.out.println("No se encontró el apartamento con ID: " + compra.getIdApartamento());
					apartamentos.add(new Apartamento());
				}
				casas.add(new Casa());
			}
		}

		model.addAttribute("casas", casas);
		model.addAttribute("apartamentos", apartamentos);
		model.addAttribute("compras", compras);
		return "ListarCompras";
	}

	@GetMapping("/verVentas")
	public String verVentas(Model model, @SessionAttribute("cuenta") Cliente cliente) {
		List<Venta> ventas = ventaRep.findAllByIdCliente(cliente);
		List<Casa> casas = new ArrayList<Casa>();
		List<Apartamento> apartamentos = new ArrayList<Apartamento>();
		for (int i = 0; i < ventas.size(); i++) {
			if (ventas.get(i).getIdApartamento() == 0) {
				casas.add(casaRep.findById(ventas.get(i).getIdCasa()).get());
				apartamentos.add(new Apartamento());
			} else {
				apartamentos.add(aptoRep.findById(ventas.get(i).getIdApartamento()).get());
				casas.add(new Casa());
			}
		}
		model.addAttribute("casas", casas);
		model.addAttribute("apartamentos", apartamentos);
		model.addAttribute("ventas", ventas);
		return "ListarVentas";
	}

}
