package io.github.jevanlingen.luwak;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import io.github.jevanlingen.luwak.wrapper.Ø;
import org.junit.jupiter.api.Test;

class OptionalTest {
	@Test
	void testØWithStream() {
		final var result = Ø.of(List.of("A", "B", "C").stream()
				.filter("A"::equals)
				.findFirst());

		assertTrue(result.isPresent());
		assertEquals("A", result.get());
	}

	@Test
	void testØWithFilterMapAndFlatMap() {
		final var result = Ø.of("A")
				.filter("A"::equals)
				.map(i -> "B")
				.flatMap(i -> i.equals("B") ? Ø.of("C") : Ø.empty());

		assertTrue(result.isPresent());
		assertEquals("C", result.get());
	}

	@Test
	void testØWithFilterMapAndFlatMapAndOr() {
		final var result = Ø.of("A")
				.filter("A"::equals)
				.map(i -> "B")
				.flatMap(i -> i.equals("C") ? Ø.of("C") : Ø.empty())
				.or(() -> Ø.of("D"));

		assertTrue(result.isPresent());
		assertEquals("D", result.get());
	}
}