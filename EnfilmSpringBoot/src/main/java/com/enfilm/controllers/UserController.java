package com.enfilm.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enfilm.jwtSecurity.AutenticadorJWT;
import com.enfilm.model.entities.Movie;
import com.enfilm.model.entities.User;
import com.enfilm.model.repositories.UserRepository;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	/**
	 * Autentica un usuario, dados su datos de acceso: nombre de usuario y
	 * contraseña
	 */
	@PostMapping("/user/autentica")
	public DTO autenticaUsuario(@RequestBody DatosAutenticacionUsuario datos) {
		DTO dto = new DTO(); // Voy a devolver un dto

		// Intento localizar un usuario a partir de su nombre de usuario y su password
		User usuAutenticado = userRepository.findByEmailAndPassword(datos.email, datos.password);
		if (usuAutenticado != null) {
			System.out.println("autenticado");
			dto.put("jwt", AutenticadorJWT.codificaJWT(usuAutenticado));
		}

		// Finalmente devuelvo el JWT creado, puede estar vacío si la autenticación no
		// ha funcionado
		return dto;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/user/getUserAutenticado")
	public DTO getUsuario(HttpServletRequest request) {

		int idUser = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);

		User user = userRepository.findById(idUser).get();

		System.out.println("getUserAutenticado sends");

		return getDTOFromUsuario(user);

	}
	
	@GetMapping("/user/byId")
	public DTO getUserById(int idUser) {
		
		DTO dto = new DTO();
		
		dto.put("result", "fail");
		try {
			User user = userRepository.findById(idUser).get();

			dto.put("user", getDTOFromUsuario(user));
			
			dto.put("result", "ok");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		


		return dto;
	}
	

	@GetMapping("/user/getId")

	public DTO getUsuarioId(HttpServletRequest request) {

		int idUser = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);

		User user = userRepository.findById(idUser).get();

		DTO dto = new DTO();

		dto.put("id_usuario", user.getId());

		return dto;

	}



	/**
	 * usado para comprobar si una contraseña es igual a la contraseña del usuario
	 * autenticado
	 */
	@PostMapping("/usuario/ratificaPassword")
	public DTO ratificaPassword(@RequestBody DTO dtoRecibido, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor

		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo el usuario
																									// autenticado, por
																									// su JWT

		try {
			User usuarioAutenticado = userRepository.findById(idUsuAutenticado).get(); // Localizo todos los datos del
																						// usuario
			String password = (String) dtoRecibido.get("password"); // Compruebo la contraseña
			if (password.equals(usuarioAutenticado.getPassword())) {
				dto.put("result", "ok"); // Devuelvo éxito, las contraseñas son iguales
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dto;
	}

	/**
	 * Recibe una nueva password para el usuario autenticado y la modifica en la
	 * unidad de persistencia
	 */
	@PostMapping("/usuario/modificaPassword")
	public DTO modificaPassword(@RequestBody DTO dtoRecibido, HttpServletRequest request) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor

		int idUsuAutenticado = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request); // Obtengo el usuario
																									// autenticado, por
																									// su JWT

		try {
			User usuarioAutenticado = userRepository.findById(idUsuAutenticado).get(); // Localizo al usuario
			String password = (String) dtoRecibido.get("password"); // Recibo la password que llega en el dtoRecibido
			usuarioAutenticado.setPassword(password); // Modifico la password
			userRepository.save(usuarioAutenticado); // Guardo el usuario, con nueva password, en la unidad de
														// persistencia
			dto.put("result", "ok"); // Devuelvo éxito
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dto;
	}

	/**
	 * Recibe los datos personales del usuario y los modifica en la unidad de
	 * persistencia
	 */
	@PostMapping("/user/update")
	public DTO modificaDatosUsuario(@RequestBody DTO dtoRecibido) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor																								// su JWT

		try {
			User userToUpdate = userRepository.findById((Integer) dtoRecibido.get("id")).get(); // Localizo al usuario
			// Cargo los datos recibidos en el usuario localizado por su id.
			userToUpdate.setEmail((String) dtoRecibido.get("email"));
			userToUpdate.setName((String) dtoRecibido.get("name"));
			userToUpdate.setSurname((String) dtoRecibido.get("surname"));
			userToUpdate.setPhone((String) dtoRecibido.get("phone"));
			userToUpdate.setPassword((String) dtoRecibido.get("password"));
			userToUpdate.setRole((int) dtoRecibido.get("role"));

			userRepository.save(userToUpdate); // Guardo el usuario en la unidad de persistencia
			dto.put("result", "ok"); // Devuelvo éxito

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dto;
	}
	
	/**
	 * Recibe los datos personales del usuario y los modifica en la unidad de
	 * persistencia
	 */
	@PostMapping("/user/new")
	public DTO newUser(@RequestBody DTO dtoRecibido) {
		DTO dto = new DTO(); // Voy a devolver un dto
		dto.put("result", "fail"); // Asumo que voy a fallar, si todo va bien se sobrescribe este valor																								// su JWT

		try {
			User userToUpdate = new User();
			
			// Cargo los datos recibidos en el usuario localizado por su id.
			userToUpdate.setEmail((String) dtoRecibido.get("email"));
			userToUpdate.setName((String) dtoRecibido.get("name"));
			userToUpdate.setSurname((String) dtoRecibido.get("surname"));
			userToUpdate.setPhone((String) dtoRecibido.get("phone"));
			userToUpdate.setPayment((String) dtoRecibido.get("payment"));
			userToUpdate.setPassword((String) dtoRecibido.get("password"));
			userToUpdate.setRole((int) dtoRecibido.get("role"));

			userRepository.save(userToUpdate); // Guardo el usuario en la unidad de persistencia
			dto.put("result", "ok"); // Devuelvo éxito

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dto;
	}

	@GetMapping("/user/allEstandar")
	public DTO getUsuariosAllEstandar() {

		DTO dto = new DTO();

		// Here goes the movie list in json format
		List<DTO> userListDTO = new ArrayList<DTO>();

		List<User> userList = new ArrayList<User>();

		try {

			userList =  this.userRepository.findByRole(2);

			for (User user: userList) {

				userListDTO.add(getDTOFromUsuario(user));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		dto.put("user", userListDTO);

		return dto;

	}
	
	@DeleteMapping("/user/delete")
	public DTO deleteUser(int idUser) {
		DTO result = new DTO();
		result.put("result", "fail");
		
		try {
			
			User u = userRepository.findById(idUser).get();
			System.out.println(u.getName());
			
			userRepository.delete(u);
			
			result.put("result", "ok");
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private DTO getDTOFromUsuario(User usu) {
		DTO dto = new DTO(); // Voy a devolver un dto
		if (usu != null) {
			dto.put("id", usu.getId());
			dto.put("name", usu.getName());
			dto.put("surname", usu.getSurname());
			dto.put("email", usu.getEmail());
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
