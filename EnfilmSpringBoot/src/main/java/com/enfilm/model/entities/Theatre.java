package com.enfilm.model.entities;

import java.io.Serializable;
import javax.persistence.*;
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

	private int seats;

	//bi-directional many-to-one association to Seat
	@OneToMany(mappedBy="theatre")
	private List<Seat> seatsSet;

	public Theatre() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeats() {
		return this.seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public List<Seat> getSeatsSet() {
		return this.seatsSet;
	}

	public void setSeatsSet(List<Seat> seatsSet) {
		this.seatsSet = seatsSet;
	}

	public Seat addSeatsSet(Seat seatsSet) {
		getSeatsSet().add(seatsSet);
		seatsSet.setTheatre(this);

		return seatsSet;
	}

	public Seat removeSeatsSet(Seat seatsSet) {
		getSeatsSet().remove(seatsSet);
		seatsSet.setTheatre(null);

		return seatsSet;
	}

}