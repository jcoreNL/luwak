package org.jcore.luwak;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Function;

import org.jcore.luwak.function.ƒ;
import org.jcore.luwak.function.ℙ;
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
}