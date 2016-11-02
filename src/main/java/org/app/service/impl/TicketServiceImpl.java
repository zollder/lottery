package org.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.app.model.Ticket;
import org.app.repository.TicketRepository;
import org.app.service.TicketService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

	@Value("${price}")
	private double price;

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public Optional<Ticket> purchase(@NotEmpty String name) {
		return ticketRepository.createTicket(name, price);
	}

	@Override
	public Optional<Ticket> getById(int id) {
		return ticketRepository.loadTicketById(id);
	}

	@Override
	public List<Ticket> getPurchasedTickets() {
		return ticketRepository.loadPurchasedTickets();
	}
}
