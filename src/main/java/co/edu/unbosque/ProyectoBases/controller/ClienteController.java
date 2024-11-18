package co.edu.unbosque.ProyectoBases.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.ProyectoBases.MariaDB.model.Cliente;
import co.edu.unbosque.ProyectoBases.MariaDB.repository.ClienteRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class ClienteController {
	@Autowired
	public ClienteRepository rep;

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
		cliente.setApartamentos(new ArrayList<Integer>());
		cliente.setCasas(new ArrayList<Integer>());
		cliente.setContrasena(contrasena);
		cliente.setDireccion(direccion);
		cliente.setEmail(email);
		cliente.setNombre(nombre);
		cliente.setTelefono(telefono);
		rep.save(cliente);
		model.addAttribute("mensaje", "");
		return "IndexCliente";
	}

}
