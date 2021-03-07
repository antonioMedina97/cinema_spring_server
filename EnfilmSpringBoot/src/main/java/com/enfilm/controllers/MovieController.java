package com.enfilm.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enfilm.model.entities.Movie;
import com.enfilm.model.entities.User;
import com.enfilm.model.repositories.MovieRepository;
import com.enfilm.model.repositories.TicketRepository;

@CrossOrigin
@RestController
public class MovieController {

	@Autowired MovieRepository movieRep;
	@Autowired TicketRepository ticketRepository;
	
	@GetMapping("/movie/all")
	public List<Movie> findAllMovies() {
		return (List<Movie>) this.movieRep.findAll();
	}
	
	@GetMapping("/movie/byId")
	public DTO getMovieById( int id) {
		DTO dto = new DTO();
		
		dto.put("movie", movieRep.findById(id));
		
		return dto;
		
		
	}
	
	@GetMapping("/movie/list")
	public DTO getMovieList() {
		DTO dtoResult = new DTO();
		
		//Here goes the movie list in json format
		List<DTO> movieListDTO = new ArrayList<DTO>();
		
		List<Movie> movieList = new ArrayList<Movie>();
		
		try {
			
			movieList = (List<Movie>) this.movieRep.findAll();
			
			for (Movie movie : movieList) {
				
				movieListDTO.add(getDTOFromMovie(movie));
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dtoResult.put("movies", movieListDTO);
		
		return dtoResult;
	}
	
	
	
	
	private DTO getDTOFromMovie(Movie mov) {
		DTO dto = new DTO(); // Voy a devolver un dto
		if (mov != null) {
			dto.put("id", mov.getId());
			dto.put("title", mov.getTitle());
			dto.put("image", mov.getImage());
			dto.put("rate", mov.getRate());
			dto.put("dates", ticketRepository.getDateSesionesByMovie(mov.getId()));
		}
		return dto;
	}
	
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
	
	

}
