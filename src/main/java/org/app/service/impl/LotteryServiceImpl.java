package org.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.app.model.Ticket;
import org.app.repository.impl.TicketRepositoryImpl;
import org.app.service.LotteryService;
import org.app.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * LotteryService implementation.
 */
@Service
public class LotteryServiceImpl implements LotteryService {

	@Value("${coefficients}")
	private int[] coefficients;

	@Autowired
	private CollectionUtils collectionUtils;
	@Autowired
	private TicketRepositoryImpl ticketRepository;

	@Override
	public List<Integer> runDraw() {
		// get 1st 3 balls from the shuffled collection of 50
		List<Integer> selection = collectionUtils
				.generateShuffledIds(ticketRepository.getSize())
				.subList(0, 3);

		// find purchased tickets with corresponding IDs, if any
		List<Ticket> tickets = ticketRepository.loadTicketsByIds(selection)
			.stream()
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toList());

		// calculate prize for each ticket
		Double prizeBank = 0.5 * ticketRepository.getBank();
		tickets.forEach(ticket -> ticket.setPrize(prizeBank * coefficients[tickets.indexOf(ticket)] * 0.01));

		// clear previous and save new draw winners
		ticketRepository.saveWinners(tickets);

		// prepare repository for the next draw
		ticketRepository.reset();

		// return selected ball IDs
		return selection;
	}

	@Override
	public List<Ticket> getWinners() {
		return ticketRepository.getWinners();
	}

	public void setCoefficients(int[] coefficients) {
		this.coefficients = coefficients;
	}
}
