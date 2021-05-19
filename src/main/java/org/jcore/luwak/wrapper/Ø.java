package org.jcore.luwak.wrapper;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.jcore.luwak.function.$;
import org.jcore.luwak.function.ƒ;
import org.jcore.luwak.function.₵;
import org.jcore.luwak.function.ℙ;
import org.jcore.luwak.function.ℝ;

/**
 * A container object which may or may not contain a non-{@code null} value.
 * If a value is present, {@code isPresent()} returns {@code true}. If no
 * value is present, the object is considered <i>empty</i> and
 * {@code isPresent()} returns {@code false}.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(Object) orElse()}
 * (returns a default value if no value is present) and
 * {@link #ifPresent(₵) ifPresent()} (performs an
 * action if a value is present).
 *
 * @param <T> the type of value
 */
public class Ø<T> {
	private static final Ø<?> EMPTY = new Ø<>();

	private final Optional<T> value;

	private Ø() {
		this.value = Optional.empty();
	}

	private Ø(T value) {
		this.value = Optional.of(value);
	}

	/**
	 * Returns an empty {@code Ø} instance. No value is present for this optional.
	 *
	 * @param <T> The type of the non-existent value
	 * @return an empty {@code Ø}
	 */
	public static <T> Ø<T> empty() {
		return (Ø<T>) EMPTY;
	}

	/**
	 * Returns an {@code Ø} describing the given non-{@code null} value.
	 *
	 * @param value the value to describe, which must be non-{@code null}
	 * @param <T>   the type of the value
	 * @return an {@code Ø} with the value present
	 * @throws NullPointerException if value is {@code null}
	 */
	public static <T> Ø<T> of(T value) {
		return new Ø<>(value);
	}

	/**
	 * Returns an {@code Ø} describing the given non-{@code null} optional.
	 *
	 * @param optional the optional to describe, which must be non-{@code null}
	 * @param <T>      the type of the value
	 * @return an {@code Ø} with a present value if the specified optional
	 * is present, otherwise an empty {@code Ø}
	 * @throws NullPointerException if optional is {@code null}
	 */
	public static <T> Ø<T> of(Optional<T> optional) {
		Objects.requireNonNull(optional);
		return optional.map(Ø::of).orElseGet(Ø::empty);
	}

	/**
	 * Returns an {@code Ø} describing the given value, if
	 * non-{@code null}, otherwise returns an empty {@code Ø}.
	 *
	 * @param value the possibly-{@code null} value to describe
	 * @param <T>   the type of the value
	 * @return an {@code Ø} with a present value if the specified value
	 * is non-{@code null}, otherwise an empty {@code Ø}
	 */
	public static <T> Ø<T> ofNullable(T value) {
		return value == null ? empty() : of(value);
	}

	/**
	 * Translates the alias back to a native {@link Optional}.
	 *
	 * @return an {@code Optional} with a present value if the {@code Ø} value
	 * is non-{@code null}, otherwise an empty {@code Optional}
	 */
	public Optional<T> toOptional() {
		return this.value;
	}

	/**
	 * If a value is present, returns the value, otherwise throws {@link NoSuchElementException}.
	 *
	 * @return the non-{@code null} value described by this {@code Ø}
	 * @throws NoSuchElementException if no value is present
	 * @deprecated The original {@link Optional#get()} method is considered a code smell nowadays,
	 * the preferred alternative to this method is {@link #orElseThrow()}.
	 */
	@Deprecated
	public T get() {
		return value.get();
	}

	/**
	 * If a value is present, returns {@code true}, otherwise {@code false}.
	 *
	 * @return {@code true} if a value is present, otherwise {@code false}
	 */
	public boolean isPresent() {
		return value.isPresent();
	}

	/**
	 * If a value is not present, returns {@code true}, otherwise {@code false}.
	 *
	 * @return {@code true} if a value is not present, otherwise {@code false}
	 */
	public boolean isEmpty() {
		return value.isEmpty();
	}

	/**
	 * If a value is present, performs the given action with the value, otherwise does nothing.
	 *
	 * @param action the action to be performed, if a value is present
	 * @throws NullPointerException if value is present and the given action is {@code null}
	 */
	public void ifPresent(₵<? super T> action) {
		value.ifPresent(action);
	}

	/**
	 * If a value is present, performs the given action with the value,
	 * otherwise performs the given empty-based action.
	 *
	 * @param action      the action to be performed, if a value is present
	 * @param emptyAction the empty-based action to be performed, if no value is present
	 * @throws NullPointerException if a value is present and the given action
	 *                              is {@code null}, or no value is present and the given empty-based
	 *                              action is {@code null}.
	 */
	public void ifPresentOrElse(₵<? super T> action, ℝ emptyAction) {
		value.ifPresentOrElse(action, emptyAction);
	}

	/**
	 * If a value is present, and the value matches the given predicate,
	 * returns an {@code Ø} describing the value, otherwise returns an
	 * empty {@code Ø}.
	 *
	 * @param predicate the predicate to apply to a value, if present
	 * @return an {@code Ø} describing the value of this
	 * {@code Ø}, if a value is present and the value matches the
	 * given predicate, otherwise an empty {@code Ø}
	 * @throws NullPointerException if the predicate is {@code null}
	 */
	public Ø<T> filter(ℙ<? super T> predicate) {
		return of(value.filter(predicate));
	}

	/**
	 * If a value is present, returns an {@code Ø} describing (as if by
	 * {@link #ofNullable}) the result of applying the given mapping function to
	 * the value, otherwise returns an empty {@code Ø}.
	 *
	 * <p>If the mapping function returns a {@code null} result then this method
	 * returns an empty {@code Ø}.
	 *
	 * @param mapper the mapping function to apply to a value, if present
	 * @param <U>    The type of the value returned from the mapping function
	 * @return an {@code Ø} describing the result of applying a mapping
	 * function to the value of this {@code Ø}, if a value is
	 * present, otherwise an empty {@code Ø}
	 * @throws NullPointerException if the mapping function is {@code null}
	 */
	public <U> Ø<U> map(ƒ<? super T, ? extends U> mapper) {
		return of(value.map(mapper));
	}

	/**
	 * If a value is present, returns the result of applying the given
	 * {@code Optional}-bearing mapping function to the value, otherwise returns
	 * an empty {@code Optional}.
	 *
	 * <p>This method is similar to {@link #map(ƒ)}, but the mapping
	 * function is one whose result is already an {@code Ø}, and if
	 * invoked, {@code flatMap} does not wrap it within an additional
	 * {@code Ø}.
	 *
	 * @param <U>    The type of value of the {@code Ø} returned by the mapping function
	 * @param mapper the mapping function to apply to a value, if present
	 * @return the result of applying an {@code Ø}-bearing mapping
	 * function to the value of this {@code Ø}, if a value is
	 * present, otherwise an empty {@code Ø}
	 * @throws NullPointerException if the mapping function is {@code null} or
	 *                              returns a {@code null} result
	 */
	public <U> Ø<U> flatMap(ƒ<? super T, ? extends Ø<? extends U>> mapper) {
		Objects.requireNonNull(mapper);

		if (isEmpty()) {
			return empty();
		}

		@SuppressWarnings("unchecked")
		Ø<U> r = (Ø<U>) mapper.apply(get());
		return Objects.requireNonNull(r);
	}

	/**
	 * If a value is present, returns an {@code Ø} describing the value,
	 * otherwise returns an {@code Ø} produced by the supplying function.
	 *
	 * @param supplier the supplying function that produces an {@code Ø} to be returned
	 * @return returns an {@code Ø} describing the value of this {@code Ø}, if a value is present,
	 * otherwise an {@code Ø} produced by the supplying function.
	 * @throws NullPointerException if the supplying function is {@code null} or produces a {@code null} result
	 */
	public Ø<T> or($<? extends Ø<? extends T>> supplier) {
		Objects.requireNonNull(supplier);

		if (isPresent()) {
			return this;
		}

		@SuppressWarnings("unchecked")
		Ø<T> r = (Ø<T>) supplier.get();
		return Objects.requireNonNull(r);
	}

	/**
	 * If a value is present, returns a sequential {@link Stream} containing
	 * only that value, otherwise returns an empty {@code Stream}.
	 *
	 * @return the optional value as a {@code Stream}
	 */
	public Stream<T> stream() {
		return value.stream();
	}

	/**
	 * If a value is present, returns the value, otherwise returns {@code other}.
	 *
	 * @param other the value to be returned, if no value is present. May be {@code null}.
	 * @return the value, if present, otherwise {@code other}
	 */
	public T orElse(T other) {
		return value.orElse(other);
	}

	/**
	 * If a value is present, returns the value, otherwise returns the result
	 * produced by the supplying function.
	 *
	 * @param supplier the supplying function that produces a value to be returned
	 * @return the value, if present, otherwise the result produced by the supplying function
	 * @throws NullPointerException if no value is present and the supplying function is {@code null}
	 */
	public T orElseGet($<? extends T> supplier) {
		return value.orElseGet(supplier);
	}

	/**
	 * If a value is present, returns the value, otherwise throws {@code NoSuchElementException}.
	 *
	 * @return the non-{@code null} value described by this {@code Optional}
	 * @throws NoSuchElementException if no value is present
	 */
	public T orElseThrow() {
		return value.orElseThrow();
	}

	/**
	 * If a value is present, returns the value, otherwise throws an exception
	 * produced by the exception supplying function.
	 *
	 * @param <X>               Type of the exception to be thrown
	 * @param exceptionSupplier the supplying function that produces an exception to be thrown
	 * @return the value, if present
	 * @throws X                    if no value is present
	 * @throws NullPointerException if no value is present and the exception supplying function is {@code null}
	 */
	public <X extends Throwable> T orElseThrow($<? extends X> exceptionSupplier) throws X {
		return value.orElseThrow(exceptionSupplier);
	}

	/**
	 * Indicates whether some other object is "equal to" this {@code Ø}.
	 * The other object is considered equal if:
	 * <ul>
	 * <li>it is also an {@code Ø} and;
	 * <li>both instances have no value present or;
	 * <li>the present values are "equal to" each other via {@code equals()}.
	 * </ul>
	 *
	 * @param obj an object to be tested for equality
	 * @return {@code true} if the other object is "equal to" this object otherwise {@code false}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Ø)) {
			return false;
		}

		Ø<?> other = (Ø<?>) obj;

		if (this.isEmpty() && other.isEmpty()) {
			return true;
		}

		return this.isPresent() && other.isPresent() && Objects.equals(this.get(), other.get());
	}

	/**
	 * Returns the hash code of the value, if present, otherwise {@code 0} (zero) if no value is present.
	 *
	 * @return hash code value of the present value or {@code 0} if no value is present
	 */
	@Override
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * Returns a non-empty string representation of this {@code Ø} suitable for debugging.
	 * The exact presentation format is unspecified and may vary between implementations and versions.
	 *
	 * @return the string representation of this instance
	 * @implSpec If a value is present the result must include its string representation
	 * in the result.  Empty and present {@code Optional}s must be unambiguously
	 * differentiable.
	 */
	@Override
	public String toString() {
		return value.toString();
	}
}