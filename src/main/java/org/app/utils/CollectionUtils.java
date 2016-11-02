package org.app.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class CollectionUtils {

	public List<Integer> generateShuffledIds(int size) {
		List<Integer> ids = IntStream.rangeClosed(1, size)
			.boxed()
			.collect(Collectors.toList());
		Collections.shuffle(ids);
		return ids;
	}
}
