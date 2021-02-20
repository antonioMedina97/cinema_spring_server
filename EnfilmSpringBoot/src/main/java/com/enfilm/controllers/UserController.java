package com.enfilm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enfilm.jwtSecurity.AutenticadorJWT;
import com.enfilm.model.entities.User;
import com.enfilm.model.repositories.UserRepository;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	
	
	/**
	 * Autentica un usuario, dados su datos de acceso: nombre de usuario y contraseña
	 */
	@PostMapping("/user/autentica")
	public DTO autenticaUsuario (@RequestBody DatosAutenticacionUsuario datos) {
		DTO dto = new DTO(); // Voy a devolver un dto

		// Intento localizar un usuario a partir de su nombre de usuario y su password
		User usuAutenticado = userRepository.findByEmailAndPassword(datos.email, datos.password);
		if (usuAutenticado != null) {
			System.out.println("autenticado");
			dto.put("jwt", AutenticadorJWT.codificaJWT(usuAutenticado));
		}

		// Finalmente devuelvo el JWT creado, puede estar vacío si la autenticación no ha funcionado
		return dto;
	}

	@GetMapping("/user/get")
	public DTO getUsuario(int id) {
		User user = userRepository.findById(id).get();
		
		return getDTOFromUsuario(user);

	}


	private DTO getDTOFromUsuario(User usu) {
		DTO dto = new DTO(); // Voy a devolver un dto
		if (usu != null) {
			dto.put("id", usu.getId());
			dto.put("name", usu.getName());
			dto.put("surname", usu.getSurname());
			dto.put("password", usu.getPassword());
			dto.put("payment", usu.getPayment());
			dto.put("phone", usu.getPhone());
			dto.put("role", usu.getRole());
		}
		return dto;
	}
	
	/**
	 * Clase que contiene los datos de autenticacion del usuario
	 */
	static class DatosAutenticacionUsuario {
		String email;
		String password;

		/**
		 * Constructor
		 */
		public DatosAutenticacionUsuario(String email, String password) {
			super();
			this.email = email;
			this.password = password;
		}
	}
}
