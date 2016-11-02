package org.app.service;

import java.util.List;

import org.app.model.Ticket;

/**
 * LotteryService interface definition.
 */
public interface LotteryService {

	/**
	 * Starts the draw.
	 * Randomly selects 3 winning balls from the basket.
	 * Returns selected balls as a list.
	 *
	 * @return a list of winning ball IDs
	 */
	public List<Integer> runDraw();

	/**
	 * Retrieves a collection of latest draw winners.
	 *
	 * @return a collection of lucky tickets
	 */
	public List<Ticket> getWinners();
}
