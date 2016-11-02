package org.app.repository.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.app.model.Ticket;
import org.app.repository.TicketRepository;
import org.app.utils.CollectionUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

	@Value("${size}")
	private int size;

	@Value("${bank}")
	private double bank;

	@Autowired
	private CollectionUtils collectionUtils;

	// holds a shuffled collection of ticket IDs available for purchase
	private Queue<Integer> ticketIds = new LinkedList<Integer>();

	// holds a collection of purchased tickets
	private Map<Number, Ticket> purchasedTicketsMap = new LinkedHashMap<Number, Ticket>();

	// holds a collection of winner tickets from the last draw
	private List<Ticket> winners = new LinkedList<>();

	public TicketRepositoryImpl() {}

	@Override
	@PostConstruct
	public void reset() {
		reset(size, bank);
	}

	@Override
	public void reset(int size, double bank) {
		this.size = size;
		this.bank = bank;
		ticketIds.clear();
		purchasedTicketsMap.clear();
		ticketIds.addAll(collectionUtils.generateShuffledIds(size));
	}

	@Override
	public Optional<Ticket> createTicket(@NotEmpty String name, double price) {
		Ticket ticket = ticketIds.isEmpty() ? null : new Ticket(ticketIds.poll(), name);
		if (ticket != null) {
			ticket.setPrice(price);
			purchasedTicketsMap.put(ticket.getId(), ticket);
		}
		return Optional.ofNullable(ticket);
	}

	@Override
	public Optional<Ticket> loadTicketById(int id) {
		return Optional.ofNullable(purchasedTicketsMap.get(id));
	}

	@Override
	public List<Optional<Ticket>> loadTicketsByIds(List<Integer> ids) {
		return ids.stream()
			.map(id -> loadTicketById(id))
			.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> loadPurchasedTickets() {
		return new ArrayList<Ticket>(purchasedTicketsMap.values());
	}

	@Override
	public void saveWinners(List<Ticket> tickets) {
		if (!tickets.isEmpty()) {
			winners.clear();
			winners.addAll(tickets);
		}
	}

	@Override
	public List<Ticket> getWinners() {
		return winners;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public Double getBank() {
		return this.bank;
	}
}
