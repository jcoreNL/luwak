package org.jcore.luwak.wrapper;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.jcore.luwak.function.$;
import org.jcore.luwak.function.ƒ;
import org.jcore.luwak.function.₵;
import org.jcore.luwak.function.ℙ;
import org.jcore.luwak.function.ℝ;

public class Ø<T> {
	private static final Ø<?> EMPTY = new Ø<>();

	private final Optional<T> value;

	private Ø() {
		this.value = Optional.empty();
	}

	private Ø(T value) {
		this.value = Optional.of(value);
	}

	public static <T> Ø<T> empty() {
		return (Ø<T>) EMPTY;
	}

	public static <T> Ø<T> of(T value) {
		return new Ø<>(value);
	}

	public static <T> Ø<T> of(Optional<T> optional) {
		return optional.map(Ø::of).orElseGet(Ø::empty);
	}

	public static <T> Ø<T> ofNullable(T value) {
		return value == null ? empty() : of(value);
	}

	public Optional<T> toOptional() {
		return this.value;
	}

	public T get() {
		return value.get();
	}

	public boolean isPresent() {
		return value.isPresent();
	}

	public boolean isEmpty() {
		return value.isEmpty();
	}

	public void ifPresent(₵<? super T> action) {
		value.ifPresent(action);
	}

	public void ifPresentOrElse(₵<? super T> action, ℝ emptyAction) {
		value.ifPresentOrElse(action, emptyAction);
	}

	public Ø<T> filter(ℙ<? super T> predicate) {
		return of(value.filter(predicate));
	}

	public <U> Ø<U> map(ƒ<? super T, ? extends U> mapper) {
		return of(value.map(mapper));
	}
	
	public <U> Ø<U> flatMap(ƒ<? super T, ? extends Ø<? extends U>> mapper) {
		Objects.requireNonNull(mapper);

		if (isEmpty()) {
			return empty();
		}

		@SuppressWarnings("unchecked")
		Ø<U> r = (Ø<U>) mapper.apply(value.get());
		return Objects.requireNonNull(r);
	}

	public Ø<T> or($<? extends Ø<? extends T>> supplier) {
		Objects.requireNonNull(supplier);

		if (isPresent()) {
			return this;
		}

		@SuppressWarnings("unchecked")
		Ø<T> r = (Ø<T>) supplier.get();
		return Objects.requireNonNull(r);
	}

	public Stream<T> stream() {
		return value.stream();
	}

	public T orElse(T other) {
		return value.orElse(other);
	}

	public T orElseGet($<? extends T> supplier) {
		return value.orElseGet(supplier);
	}

	public T orElseThrow() {
		return value.orElseThrow();
	}

	public <X extends Throwable> T orElseThrow($<? extends X> exceptionSupplier) throws X {
		return value.orElseThrow(exceptionSupplier);
	}

	@Override
	public boolean equals(Object obj) {
		return value.equals(obj);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public String toString() {
		return value.toString();
	}
}