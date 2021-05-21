package io.github.jevanlingen.luwak;

import static java.util.stream.Collectors.toList;
import static io.github.jevanlingen.luwak.function.ƒ.__;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URLEncoder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import io.github.jevanlingen.luwak.function.$;
import io.github.jevanlingen.luwak.function.ƒ;
import io.github.jevanlingen.luwak.function.₵;
import io.github.jevanlingen.luwak.function.ℙ;
import io.github.jevanlingen.luwak.function.ℝ;
import org.junit.jupiter.api.Test;

class FunctionTest {
	@Test
	void testFunctionCanbeUsedWithƒ() {
		final Function<String, String> toHigher = String::toUpperCase;
		final ƒ<String, String> inverseChars = a -> new StringBuilder(a).reverse().toString();
		final Function<String, String> test = toHigher.andThen(inverseChars);

		assertEquals("EM WOHS", test.apply("Show me"));
	}

	@Test
	void testConvertFunctionToƒ() {
		final Function<String, String> toHigher = String::toUpperCase;
		final ƒ<String, String> inverseChars = a -> new StringBuilder(a).reverse().toString();
		final ƒ<String, String> test = ƒ.of(toHigher.andThen(inverseChars));

		assertEquals("EM WOHS", test.apply("Show me"));
	}

	@Test
	void testComposeFunctionWithƒ() {
		final ƒ<Integer, Integer> countThree = i -> i + 3;
		final ƒ<Integer, Integer> countThreeAgain = i -> i + 3;
		final ƒ<Integer, Integer> countSix = countThree.compose(countThreeAgain);

		assertEquals(9, countSix.apply(3));
	}

	@Test
	void testSimpleℙ() {
		final ƒ<ℙ<String>, String> eval = p -> List.of("A").stream().filter(p).findFirst().orElse("B");

		assertEquals("A", eval.apply("A"::equals));
		assertEquals("B", eval.apply("B"::equals));
	}

	@Test
	void testℙWithInvocation() {
		final ƒ<ℙ<String>, String> eval = x -> x.test("something...") ? "Yeah" : "Nope";

		assertEquals("Yeah", eval.apply("something..."::equals));
		assertEquals("Nope", eval.apply("else"::equals));
	}

	@Test
	void testCheckedSupplier() {
		final BiConsumer<String, String> ignore = (a, b) -> {};

		final var result = List.of("A").stream()
				.collect($.__(() -> URLEncoder.encode("items?price=gte:10&price=lte:100", "UTF-8")), ignore, ignore);

		assertEquals("items%3Fprice%3Dgte%3A10%26price%3Dlte%3A100", result);
	}

	@Test
	void testCheckedFunction() {
		final var result = List.of("items?price=gte:10&price=lte:100").stream()
				.map(__(s -> URLEncoder.encode(s, "UTF-8")))
				.collect(toList());

		assertEquals("items%3Fprice%3Dgte%3A10%26price%3Dlte%3A100", result.get(0));
	}

	@Test
	void testCheckedConsumer() {
		final var result = List.of("items?price=gte:10&price=lte:100").stream()
				.peek(₵.__(s -> URLEncoder.encode(s, "UTF-8")))
				.collect(toList());

		assertEquals("items?price=gte:10&price=lte:100", result.get(0));
	}

	@Test
	void testCheckedPredicate() {
		final var result = List.of("items?price=gte:10&price=lte:100").stream()
				.filter(ℙ.__(s -> URLEncoder.encode(s, "UTF-8").contains("%3")))
				.collect(toList());

		assertEquals("items?price=gte:10&price=lte:100", result.get(0));
	}

	@Test
	void testCheckedRunnable() {
		ℝ runnable = ℝ.__(() -> {
			final var result = URLEncoder.encode("items?price=gte:10&price=lte:100", "UTF-8");
			assertEquals("items%3Fprice%3Dgte%3A10%26price%3Dlte%3A100", result);
		});

		Thread t = new Thread(runnable);
		t.start();
		t.stop();
	}
}