package org.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.app.TestBase;
import org.app.exception.TicketNotFoundException;
import org.app.exception.TicketsSoldOutException;
import org.app.model.Ticket;
import org.app.model.TicketDto;
import org.app.service.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * TicketController unit test.
 */
public class TicketControllerTest extends TestBase {

	@Mock
	private TicketService ticketService;

	@InjectMocks
	private TicketController ticketController;

	private Optional<Ticket> ticket;

	@Before
	public void setup() {
		ticket = Optional.ofNullable(new Ticket(1, "name"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetById_missingId() {
		ticketController.getById(null);
	}

	@Test(expected = TicketNotFoundException.class)
	public void testGetById_nonExistingTicket() {
		when(ticketService.getById(1)).thenReturn(Optional.empty());
		ticketController.getById(1);
	}

	@Test
	public void testGetById_success() {
		when(ticketService.getById(1)).thenReturn(ticket);
		Ticket result = ticketController.getById(1);
		assertThat(result).isEqualTo(ticket.get());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPurchaseTicket_missingName() {
		ticketController.purchaseTicket(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPurchaseTicket_bodyNull() {
		ticketController.purchaseTicket(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPurchaseTicket_emptyName() {
		ticketController.purchaseTicket(new TicketDto());
	}

	@Test(expected = TicketsSoldOutException.class)
	public void testPurchaseTicket_allTicketsSold() {
		when(ticketService.purchase("name")).thenReturn(Optional.empty());
		ticketController.purchaseTicket(new TicketDto("name"));
	}

	@Test
	public void testPurchaseTicket_success() {
		TicketDto requestBody = new TicketDto("name");
		when(ticketService.purchase(requestBody.getFirstName())).thenReturn(ticket);
		TicketDto result = ticketController.purchaseTicket(requestBody);
		assertThat(result.getTicketId()).isEqualTo(ticket.get().getId());
	}
}