package model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the theatres database table.
 * 
 */
@Entity
@Table(name="theatres")
@NamedQuery(name="Theatre.findAll", query="SELECT t FROM Theatre t")
public class Theatre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="seat_num")
	private int seatNum;

	//bi-directional many-to-one association to Seat
	@OneToMany(mappedBy="theatre")
	@JsonIgnore
	private List<Seat> seats;

	//bi-directional many-to-one association to Show
	@OneToMany(mappedBy="theatre")
	@JsonIgnore
	private List<Show> shows;

	public Theatre() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeatNum() {
		return this.seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	public List<Seat> getSeats() {
		return this.seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Seat addSeat(Seat seat) {
		getSeats().add(seat);
		seat.setTheatre(this);

		return seat;
	}

	public Seat removeSeat(Seat seat) {
		getSeats().remove(seat);
		seat.setTheatre(null);

		return seat;
	}

	public List<Show> getShows() {
		return this.shows;
	}

	public void setShows(List<Show> shows) {
		this.shows = shows;
	}

	public Show addShow(Show show) {
		getShows().add(show);
		show.setTheatre(this);

		return show;
	}

	public Show removeShow(Show show) {
		getShows().remove(show);
		show.setTheatre(null);

		return show;
	}

}