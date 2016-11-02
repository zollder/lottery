package org.app.controller;

import java.util.List;

import org.app.model.Ticket;
import org.app.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

	@Autowired
	private LotteryService loterryService;

	/**
	 * Starts a new draw.
	 * Returns draw results as a collection of selected ball IDs.
	 *
	 * @return selected ball IDs
	 */
	@RequestMapping(value = "/draw", method = RequestMethod.GET)
	public List<Integer> startDraw() {
		return loterryService.runDraw();
	}

	/**
	 * Loads and returns a collection of winner {@link Ticket}s from the last draw.
	 *
	 * @return winner tickets from the last draw
	 */
	@RequestMapping(value = "/winners", method = RequestMethod.GET)
	public List<Ticket> getWinners() {
		return loterryService.getWinners();
	}
}
