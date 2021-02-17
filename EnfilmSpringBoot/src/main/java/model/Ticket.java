package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tickets database table.
 * 
 */
@Entity
@Table(name="tickets")
@NamedQuery(name="Ticket.findAll", query="SELECT t FROM Ticket t")
public class Ticket implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private int price;

	//bi-directional many-to-one association to Seat
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_seat")
	private Seat seat;

	//bi-directional many-to-one association to Show
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_show")
	private Show show;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_user")
	private User user;

	public Ticket() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Seat getSeat() {
		return this.seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Show getShow() {
		return this.show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}