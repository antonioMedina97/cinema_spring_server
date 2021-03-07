package com.enfilm.controllers;

import java.net.http.HttpRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enfilm.jwtSecurity.AutenticadorJWT;
import com.enfilm.model.entities.Movie;
import com.enfilm.model.entities.Theatre;
import com.enfilm.model.entities.Ticket;
import com.enfilm.model.entities.User;
import com.enfilm.model.repositories.MovieRepository;
import com.enfilm.model.repositories.SeatRepository;
import com.enfilm.model.repositories.TheatreRepository;
import com.enfilm.model.repositories.TicketRepository;
import com.enfilm.model.repositories.UserRepository;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

@CrossOrigin
@RestController
public class TicketController {

	@Autowired TicketRepository ticketRepository;
	@Autowired TheatreRepository theatreRep;
	@Autowired MovieRepository movieRep;
	@Autowired SeatRepository seatRep;
	@Autowired UserRepository usuRep;
	
	@GetMapping("/ticket/movieAndDate")
	public DTO getTicketByMovieAndDate(int id_movie, String datetimeSTR) {
		
		System.out.println(datetimeSTR);
		
		DTO result = new DTO();
		
		//DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"); 
		long datetimeLong = Long.parseLong(datetimeSTR);
		Date datetime = new Date(datetimeLong);

		DateFormat df = new SimpleDateFormat("dd:MM:yy:HH:mm:ss");
		System.out.println(df.format(datetime));
		List<Ticket> ticketList = new ArrayList<Ticket>();
		
		List<DTO> ticketListDTO = new ArrayList<DTO>();
		
		try {
			
			ticketList =  (List<Ticket>) ticketRepository.findByid_movieAndDate(id_movie, datetime);
			
			for (Ticket tic : ticketList) {
				ticketListDTO.add(getDTOFromTicket(tic));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result.put("ticket", ticketListDTO);
		return result;
	}
	
	@GetMapping("/ticket/byUser")
	public DTO getTicketById(HttpServletRequest request) {
		DTO dto = new DTO();
		
		int idUser = AutenticadorJWT.getIdUsuarioDesdeJwtIncrustadoEnRequest(request);
		
		
		
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<DTO> ticketListDTO = new ArrayList<DTO>();
		
		try {
			
			ticketList = ticketRepository.findById_User(idUser);
			
			for (Ticket t : ticketList) {
				ticketListDTO.add(getDTOFromTicket(t));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dto.put("tickets", ticketListDTO);

		return dto;
		
	}
	
	@GetMapping("/dates/byMovie")
	public DTO getHorariosByMovie(int id_movie){
		DTO result = new DTO();
		
		List<Date> dateList = new ArrayList<Date>();
		
		List<DTO> dateListDTO = new ArrayList<DTO>();
		
		try {
			
			dateList = ticketRepository.getDateSesionesByMovie(id_movie);
			
			for (Date date : dateList) {
				
				dateListDTO.add(getDTOFromDate(date));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result.put("dates", dateListDTO);
		return result;
	}
	
	private DTO getDTOFromDate(Date date) {
		DTO dto = new DTO(); // Voy a devolver un dto
		if (date != null) {
			dto.put("datetime", date);

		}
		return dto;
	}
	
	private DTO getDTOFromTicket(Ticket ticket) {
		DTO dto = new DTO(); // Voy a devolver un dto
		if (ticket != null) {
			dto.put("id", ticket.getId());
			dto.put("seat_id", ticket.getSeat().getId());
			dto.put("movie_id", ticket.getMovie().getId());
			//If user == null it means the seat hasn't been bought yet
			if(ticket.getUser() == null) {
				dto.put("user_id", null);
				dto.put("selected", false);
			}
			else {
				dto.put("user_id", ticket.getUser().getId());
				dto.put("selected", true);
			}
			
			dto.put("date", ticket.getDatetime());
			dto.put("price", ticket.getPrice());
			

		}
		return dto;
	}
	/**
	 * Se crean una cantidad de tickets para una pelicula y una fecha en funcion de los asientos de la sala
	 * @return
	 */
	@PostMapping("/tickets/newShow")
	public DTO createShow(@RequestBody DatosNuevoShow datosNuevoShow) {

		DTO dto = new DTO();
		dto.put("result", "fail");
		
		try {
			
			Theatre theatre = theatreRep.findById(datosNuevoShow.id_theatre);
			Movie movie = movieRep.findById(datosNuevoShow.id_movie);
			Date datetime = new Date(datosNuevoShow.datetime);
			
			
			for (int i = 1; i <= theatre.getSeats(); i++) {
				Ticket t = new Ticket();
				
				t.setSeat(seatRep.findById(i));
				t.setMovie(movie);
				t.setUser(null);
				t.setDatetime(datetime);
				t.setPrice(4);
				
				ticketRepository.save(t);
				System.out.println("ticket guardado");
			}
			dto.put("result", "ok");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fallo al guardar tickets");
		}
		
		
		return dto;
	}
	
	/**
	 * Se crean una cantidad de tickets para una pelicula y una fecha en funcion de los asientos de la sala
	 * @return
	 */
	@PutMapping("/tickets/buy")
	public DTO buyTicket(@RequestBody DatosBuyTicket datosBuyTicket) {
		

		
		DTO dto = new DTO();
		dto.put("result", "fail");
		
		try {
			
			Ticket ticket = ticketRepository.findById(datosBuyTicket.id_ticket).get();
			User user = usuRep.findById(datosBuyTicket.id_user).get();
			
			System.out.println("ticket "+ticket.getId());
			System.out.println("user "+user.getId());
			
			ticket.setUser(user);
			ticketRepository.save(ticket);
			
			dto.put("result", "ok");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fallo al actualizar tickets");
		}
		
		return dto;
	}
	
	
static class DatosBuyTicket{
	
	int id_ticket;
	int id_user;
	
	public DatosBuyTicket(int id_ticket, int id_user) {
		this.id_ticket = id_ticket;
		this.id_user = id_user;
	}
	
}
	
	
static class DatosNuevoShow{
	int id_movie;
	int id_theatre;
	long datetime;
	
	public DatosNuevoShow(int id_movie, int id_theatre, long datetime) {
		this.id_movie = id_movie;
		this.id_theatre = id_theatre;
		this.datetime = datetime;
	}
}
}



