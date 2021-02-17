package model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the shows database table.
 * 
 */
@Entity
@Table(name="shows")
@NamedQuery(name="Show.findAll", query="SELECT s FROM Show s")
public class Show implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	//bi-directional many-to-one association to Movie
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_movie")
	private Movie movy;

	//bi-directional many-to-one association to Theatre
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_theatre")
	private Theatre theatre;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="show")
	@JsonIgnore
	private List<Ticket> tickets;

	public Show() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Movie getMovy() {
		return this.movy;
	}

	public void setMovy(Movie movy) {
		this.movy = movy;
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
		ticket.setShow(this);

		return ticket;
	}

	public Ticket removeTicket(Ticket ticket) {
		getTickets().remove(ticket);
		ticket.setShow(null);

		return ticket;
	}

}