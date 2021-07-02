package io.github.jevanlingen.luwak;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import io.github.jevanlingen.luwak.function.ℙ;
import io.github.jevanlingen.luwak.util.Do;

public class DoTest {
	@Test
	void testMapList() {
		final var list = List.of(new SubTester("A"), new SubTester("B"));

		final var old = list.stream().map(SubTester::getName).collect(Collectors.toUnmodifiableList());;
		final var now = Do.map(list, SubTester::getName);

		assertEquals(old, now);
	}

	@Test
	void testMapListTwice() {
		final var list = List.of(new Tester(new SubTester("A")), new Tester(new SubTester("B")));

		final var old = list.stream().map(Tester::getSubTester).map(SubTester::getName).collect(Collectors.toUnmodifiableList());;
		final var now =  Do.map(Do.map(list, Tester::getSubTester), SubTester::getName);

		assertEquals(old, now);
	}

	@Test
	void testFilterList() {
		final var list = List.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "A".equals(a.getName());
		final var old = list.stream().filter(predicate).collect(Collectors.toUnmodifiableList());;
		final var now = Do.filter(list, predicate);

		assertEquals(old, now);
	}

	@Test
	void testHasAnyMatchOnList() {
		final var list = List.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "A".equals(a.getName());
		final var old = list.stream().anyMatch(predicate);
		final var now = Do.anyMatch(list, predicate);

		assertEquals(old, now);
	}

	@Test
	void testHasNotAnyMatchOnList() {
		final var list = List.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "C".equals(a.getName());
		final var old = list.stream().anyMatch(predicate);
		final var now = Do.anyMatch(list, predicate);

		assertEquals(old, now);
	}

	@Test
	void testFindAnyMatchOnList() {
		final var list = List.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "A".equals(a.getName());
		final var old = list.stream().filter(predicate).findAny();
		final var now = Do.findAny(list, predicate);

		assertTrue(old.isPresent());
		assertTrue(now.isPresent());
		assertEquals(old.get(), now.get());
	}

	@Test
	void testDoNotFindAnyMatchOnList() {
		final var list = List.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "C".equals(a.getName());
		final var old = list.stream().filter(predicate).findAny();
		final var now = Do.findAny(list, predicate);

		assertTrue(old.isEmpty());
		assertTrue(now.isEmpty());
	}

	@Test
	void testMapSet() {
		final var set = Set.of(new SubTester("A"), new SubTester("B"));

		final var old = set.stream().map(SubTester::getName).collect(Collectors.toUnmodifiableSet());
		final var now = Do.map(set, SubTester::getName);

		assertEquals(old, now);
	}

	@Test
	void testMapSetTwice() {
		final var set = Set.of(new Tester(new SubTester("A")), new Tester(new SubTester("B")));

		final var old = set.stream().map(Tester::getSubTester).map(SubTester::getName).collect(Collectors.toUnmodifiableSet());
		final var now =  Do.map(Do.map(set, Tester::getSubTester), SubTester::getName);

		assertEquals(old, now);
	}

	@Test
	void testFilterSet() {
		final var set = Set.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "A".equals(a.getName());
		final var old = set.stream().filter(predicate).collect(Collectors.toUnmodifiableSet());;
		final var now = Do.filter(set, predicate);

		assertEquals(old, now);
	}

	@Test
	void testHasAnyMatchOnSet() {
		final var set = Set.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "A".equals(a.getName());
		final var old = set.stream().anyMatch(predicate);
		final var now = Do.anyMatch(set, predicate);

		assertEquals(old, now);
	}

	@Test
	void testHasNotAnyMatchOnSet() {
		final var set = Set.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "C".equals(a.getName());
		final var old = set.stream().anyMatch(predicate);
		final var now = Do.anyMatch(set, predicate);

		assertEquals(old, now);
	}

	@Test
	void testFindAnyMatchOnSet() {
		final var set = Set.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "A".equals(a.getName());
		final var old = set.stream().filter(predicate).findAny();
		final var now = Do.findAny(set, predicate);

		assertTrue(old.isPresent());
		assertTrue(now.isPresent());
		assertEquals(old.get(), now.get());
	}

	@Test
	void testDoNotFindAnyMatchOnSet() {
		final var set = Set.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "C".equals(a.getName());
		final var old = set.stream().filter(predicate).findAny();
		final var now = Do.findAny(set, predicate);

		assertTrue(old.isEmpty());
		assertTrue(now.isEmpty());
	}

	@Test
	void testFindAnyMatchOnStream() {
		final var stream = Stream.of(new SubTester("A"), new SubTester("B"));
		final var stream2 = Stream.of(new SubTester("A"), new SubTester("B"));

		final ℙ<SubTester> predicate = a -> "A".equals(a.getName());
		final var old = stream.filter(predicate).findAny();
		final var now = Do.findAny(stream2, predicate);

		assertTrue(old.isPresent());
		assertTrue(now.isPresent());
		assertEquals(old.get(), now.get());
	}

	private class Tester {
		private final SubTester subTester;

		public Tester(final SubTester subTester) {
			this.subTester = subTester;
		}

		public SubTester getSubTester() {
			return subTester;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			return subTester.equals(((Tester) o).subTester);
		}

		@Override
		public int hashCode() {
			return Objects.hash(subTester);
		}
	}

	private class SubTester {
		private final String name;

		public SubTester(final String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			return name.equals(((SubTester) o).name);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name);
		}
	}
}