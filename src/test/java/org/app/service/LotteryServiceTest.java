package org.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.app.TestBase;
import org.app.model.Ticket;
import org.app.repository.impl.TicketRepositoryImpl;
import org.app.service.impl.LotteryServiceImpl;
import org.app.utils.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * LotteryService unit test.
 */
public class LotteryServiceTest extends TestBase {

	@Mock
	private CollectionUtils collectionUtils;

	@Mock
	private TicketRepositoryImpl ticketRepository;

	@InjectMocks
	private LotteryServiceImpl lotteryService;

	private final int[] coefficients = { 60, 30, 10 };
	private int size;
	private double bank;
	private List<Integer> shuffledIds;
	private List<Integer> sublistIds;
	private List<Optional<Ticket>> tickets;

	@Before
	public void setup() {
		lotteryService.setCoefficients(coefficients);
		size = 5;
		bank = 200.0;
		shuffledIds = Arrays.asList(2, 1, 3, 7, 4);
		sublistIds = shuffledIds.subList(0, 3);

		when(ticketRepository.getSize()).thenReturn(size);
		when(ticketRepository.getBank()).thenReturn(bank);
		when(collectionUtils.generateShuffledIds(size)).thenReturn(shuffledIds);
	}

	@Test
	public void testRunDraw_completeListOfWinners() {
		tickets = sublistIds.stream()
			.map(id -> Optional.of(new Ticket(id, "name" + id)))
			.collect(Collectors.toList());

		when(ticketRepository.loadTicketsByIds(sublistIds)).thenReturn(tickets);

		List<Integer> result = lotteryService.runDraw();

		verify(collectionUtils, times(1)).generateShuffledIds(size);
		verify(ticketRepository, times(1)).loadTicketsByIds(sublistIds);
		verify(ticketRepository, times(1)).saveWinners(any());
		verify(ticketRepository, times(1)).reset();

		assertThat(result.size()).isEqualTo(tickets.size());
		assertThat(result).containsAll(Arrays.asList(2, 1, 3));
		List<Double> prizes = tickets.stream()
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(Ticket::getPrize)
			.collect(Collectors.toList());
		assertThat(prizes).containsExactly(60.0, 30.0, 10.0);
	}

	@Test
	public void testRunDraw_partialListOfWinners() {
		tickets = Arrays.asList(Optional.empty(), Optional.of(new Ticket(1, "first")), Optional.of(new Ticket(3, "second")));

		when(ticketRepository.loadTicketsByIds(sublistIds)).thenReturn(tickets);

		List<Integer> result = lotteryService.runDraw();

		verify(collectionUtils, times(1)).generateShuffledIds(size);
		verify(ticketRepository, times(1)).loadTicketsByIds(sublistIds);
		verify(ticketRepository, times(1)).saveWinners(any());
		verify(ticketRepository, times(1)).reset();

		assertThat(result).containsAll(Arrays.asList(2, 1, 3));
		List<Double> prizes = tickets.stream()
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(Ticket::getPrize)
			.collect(Collectors.toList());
		assertThat(prizes).containsExactly(60.0, 30.0);
	}

	@Test
	public void testRunDraw_emptyListOfWinners() {
		List<Optional<Ticket>> tickets = Collections.nCopies(3, Optional.empty());

		when(ticketRepository.loadTicketsByIds(sublistIds)).thenReturn(tickets);

		List<Integer> result = lotteryService.runDraw();

		verify(collectionUtils, times(1)).generateShuffledIds(size);
		verify(ticketRepository, times(1)).loadTicketsByIds(sublistIds);
		verify(ticketRepository, times(1)).saveWinners(any());
		verify(ticketRepository, times(1)).reset();

		assertThat(result).containsAll(Arrays.asList(2, 1, 3));
		List<Double> prizes = tickets.stream()
			.filter(Optional::isPresent)
			.map(Optional::get)
			.map(Ticket::getPrize)
			.collect(Collectors.toList());
		assertThat(prizes).isEmpty();
	}

	@Test
	public void testGetWinners_nonEmptyWinners() {
		Ticket ticket = new Ticket(1, "winner");
		when(ticketRepository.getWinners()).thenReturn(Arrays.asList(ticket));

		List<Ticket> result = lotteryService.getWinners();

		verify(ticketRepository, times(1)).getWinners();
		assertThat(result).contains(ticket);
	}
}
