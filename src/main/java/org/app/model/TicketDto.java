package org.app.model;

import java.io.Serializable;

public class TicketDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer ticketId;
	private String firstName;

	public TicketDto() {}

	public TicketDto(String name) {
		this.ticketId = null;
		this.firstName = name;
	}

	public TicketDto(Integer id, String name) {
		this.ticketId = id;
		this.firstName = name;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
