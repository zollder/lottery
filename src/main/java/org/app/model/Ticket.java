package org.app.model;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;


public class Ticket {

	private Integer id;
	private String name;
	private double price;
	private Double prize;

	public Ticket(@NotNull Integer id) {
		this.id = id;
	}

	// TODO: use builder pattern
	public Ticket(@NotNull Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Double getPrize() {
		return prize;
	}

	public void setPrize(Double prize) {
		this.prize = prize;
	}

	public boolean isWinner() {
		return prize != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Ticket)) {
			return false;
		}
		Ticket source = (Ticket) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
			.append(id, source.id)
			.append(name, source.name)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
