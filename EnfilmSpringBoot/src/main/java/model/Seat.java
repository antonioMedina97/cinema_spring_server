package model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the seats database table.
 * 
 */
@Entity
@Table(name="seats")
@NamedQuery(name="Seat.findAll", query="SELECT s FROM Seat s")
public class Seat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private byte taken;

	//bi-directional many-to-one association to Theatre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_theatre")
	private Theatre theatre;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="seat")
	@JsonIgnore
	private List<Ticket> tickets;

	public Seat() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getTaken() {
		return this.taken;
	}

	public void setTaken(byte taken) {
		this.taken = taken;
	}

	public Theatre getTheatre() {
		return this.theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}

	public List<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Ticket addTicket(Ticket ticket) {
		getTickets().add(ticket);
		ticket.setSeat(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setSeat(null);

		return ticket;
	}

}