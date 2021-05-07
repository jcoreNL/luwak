package org.jcore.luwak;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.jcore.luwak.wrapper.Œ.doTry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Arrays;

import org.jcore.luwak.function.ƒ;
import org.jcore.luwak.wrapper.Œ;
import org.junit.jupiter.api.Test;

class ResultTest {
	@Test
	void testBasicDoTry() {
		final var result = doTry(() -> 2 + 2);

		assertTrue(result.isSuccessFul());
		assertEquals(4, result.orElse(-1));
	}

	@Test
	void testBasicDoTryWithMap() {
		final var result = doTry(() -> 2 + 2).map(i -> i * 2);

		assertTrue(result.isSuccessFul());
		assertEquals(8, result.orElse(-1));
	}

	@Test
	void testBasicDoTryWithFlatMap() {
		final var result = doTry(() -> 2 + 2).flatMap(i -> Œ.of(i * 2));

		assertTrue(result.isSuccessFul());
		assertEquals(8, result.orElse(-1));
	}

	@Test
	void testBasicFailDoTry() {
		final var result = doTry(() -> 2 / 0);

		assertFalse(result.isSuccessFul());
		assertTrue(result.ifSuccessfulOrException(a -> {}).isPresent());
		assertEquals("/ by zero", result.ifSuccessfulOrException(a -> {}).get().getMessage());
	}

	@Test
	void testBasicFailDoTryWithMap() {
		final var result = doTry(() -> 2 / 0).map(i -> i * 2);

		assertFalse(result.isSuccessFul());
		assertTrue(result.ifSuccessfulOrException(a -> {}).isPresent());
		assertEquals("/ by zero", result.ifSuccessfulOrException(a -> {}).get().getMessage());
	}

	@Test
	void testBasicFailDoTryWithFlatMap() {
		final var result = doTry(() -> 2 / 0).flatMap(i -> Œ.of(i / 0));

		assertFalse(result.isSuccessFul());
		assertTrue(result.ifSuccessfulOrException(a -> {}).isPresent());
		assertEquals("/ by zero", result.ifSuccessfulOrException(a -> {}).get().getMessage());
	}

	@Test
	void testInitWithPredicate() {
		final var success = Œ.of(i -> i % 2 == 0, 24);
		final var failure = Œ.of(i -> i % 2 == 0, 23);

		assertTrue(success.isSuccessFul());
		assertFalse(failure.isSuccessFul());
	}

	@Test
	void testDoTryWithJdkHttpClient() {
		final ƒ<String, HttpRequest> get = url -> HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		final var client = HttpClient.newHttpClient();
		final var result = doTry(() -> client.send(get.apply("http://openjdk.java.net/"), ofString()));
		final var result2 = doTry(() -> client.send(get.apply("http://non.sense.net/"), ofString()));

		assertTrue(result.isSuccessFul());
		assertFalse(result2.isSuccessFul());
	}

	@Test
	void testValidationExample() {
		final var result = validate(
				doTry(() -> 2 + 2),
				doTry(() -> 2 / 0),
				doTry(() -> 2 / 0)
		);

		assertFalse(result);
	}

	@SafeVarargs
	public static boolean validate(Œ<Integer>... constraints) {
		return Arrays.stream(constraints).allMatch(Œ::isSuccessFul);
	}
}