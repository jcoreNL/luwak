package org.jcore.luwak.wrapper;

import java.util.Objects;

import org.jcore.luwak.function.$;
import org.jcore.luwak.function.checked._$;
import org.jcore.luwak.function.ƒ;
import org.jcore.luwak.function.₵;
import org.jcore.luwak.function.ℙ;

/**
 * Result class, holds either a value or a exception.
 *
 * @param <V> The value
 */
public abstract class Œ<V> {
	@SuppressWarnings("rawtypes")
	private static Œ empty = new Empty();

	private Œ() {}

	public abstract V orElse(final V defaultValue);

	public abstract V orElseGet(final $<V> defaultValue);

	public abstract <U> Œ<U> map(final ƒ<V, U> f);

	public abstract <U> Œ<U> flatMap(final ƒ<V, Œ<U>> f);

	public abstract Œ<V> mapFailure(final String message);

	public abstract void ifExists(₵<V> action);

	public abstract void ifExistsOrThrow(₵<V> action);

	public abstract Ø<RuntimeException> ifExistsOrException(₵<V> action);

	public Œ<V> or(final $<Œ<V>> defaultValue) {
		return map(x -> this).orElseGet(defaultValue);
	}

	public Œ<V> filter(final ℙ<V> p) {
		return flatMap(x -> p.test(x)
				? this
				: failure("Condition did not match"));
	}

	public Œ<V> filter(final ℙ<V> p, String errorMessage) {
		return flatMap(x -> p.test(x)
				? this
				: failure(errorMessage));
	}

	public boolean exists() {
		return exists(a -> true);
	}

	public boolean exists(final ℙ<V> p) {
		return map(p::test).orElse(false);
	}

	private static class Empty<V> extends Œ<V> {
		@Override
		public V orElse(V defaultValue) {
			return defaultValue;
		}

		@Override
		public V orElseGet($<V> defaultValue) {
			return defaultValue.get();
		}

		@Override
		public <U> Œ<U> map(ƒ<V, U> f) {
			return empty();
		}

		@Override
		public <U> Œ<U> flatMap(ƒ<V, Œ<U>> f) {
			return empty();
		}

		@Override
		public Œ<V> mapFailure(String message) {
			return this;
		}

		@Override
		public void ifExists(₵<V> action) {
			// Do nothing
		}

		@Override
		public void ifExistsOrThrow(₵<V> action) {
			// Do nothing
		}

		@Override
		public Ø<RuntimeException> ifExistsOrException(₵<V> action) {
			return Ø.empty();
		}

		@Override
		public String toString() {
			return "Empty()";
		}
	}

	private static class Failure<V> extends Empty<V> {
		private final RuntimeException exception;

		private Failure(final String message) {
			this.exception = new IllegalStateException(message);
		}

		private Failure(final RuntimeException exception) {
			this.exception = exception;
		}

		private Failure(final Exception e) {
			this.exception = new IllegalStateException(e.getMessage(), e);
		}

		@Override
		public <U> Œ<U> map(ƒ<V, U> f) {
			return failure(exception);
		}

		@Override
		public <U> Œ<U> flatMap(ƒ<V, Œ<U>> f) {
			return failure(exception);
		}

		@Override
		public Œ<V> mapFailure(String message) {
			return failure(new IllegalStateException(message, exception));
		}

		@Override
		public void ifExistsOrThrow(₵<V> action) {
			throw exception;
		}

		@Override
		public Ø<RuntimeException> ifExistsOrException(₵<V> action) {
			return Ø.of(exception);
		}

		@Override
		public String toString() {
			return exception.getMessage() == null
					? "Failure (" + exception + ")"
					: "Failure (" + exception.getMessage() + ")";
		}
	}

	private static class Success<V> extends Œ<V> {
		private final V value;

		private Success(final V value) {
			this.value = Objects.requireNonNull(value);
		}

		@Override
		public V orElse(V defaultValue) {
			return value;
		}

		@Override
		public V orElseGet($<V> defaultValue) {
			return value;
		}

		@Override
		public <U> Œ<U> map(ƒ<V, U> f) {
			try {
				return of(f.apply(value));
			}
			catch (Exception e) {
				return failure(e);
			}
		}

		@Override
		public <U> Œ<U> flatMap(ƒ<V, Œ<U>> f) {
			try {
				return f.apply(value);
			}
			catch (Exception e) {
				return failure(e);
			}
		}

		@Override
		public Œ<V> mapFailure(String message) {
			return this;
		}

		@Override
		public void ifExists(₵<V> action) {
			action.accept(value);
		}

		@Override
		public void ifExistsOrThrow(₵<V> action) {
			action.accept(value);
		}

		@Override
		public Ø<RuntimeException> ifExistsOrException(₵<V> action) {
			ifExists(action);
			return Ø.empty();
		}

		@Override
		public String toString() {
			return "Success (" + value.toString() + ")";
		}
	}

	public static <V> Œ<V> failure(final String message) {
		return new Failure<>(message);
	}

	public static <V> Œ<V> failure(final RuntimeException exception) {
		return new Failure<>(exception);
	}

	public static <V> Œ<V> failure(final Exception exception) {
		return new Failure<>(exception);
	}

	public static <V> Œ<V> empty() {
		return empty;
	}

	public static <V> Œ<V> of(final V value) {
		return new Success<>(value);
	}

	public static <V> Œ<V> of(ℙ<V> p, final V value) {
		return of(p, value, "Condition did not match");
	}

	public static <V> Œ<V> of(ℙ<V> p, final V value, final String message) {
		try {
			return p.test(value)
					? of(value)
					: failure(message);
		}
		catch (Exception e) {
			return failure(new IllegalStateException("Exception while evaluating: " + value, e));
		}
	}

	public static <V> Œ<V> ofNullable(final V value, final String errorMessage) {
		return value == null
				? failure(errorMessage)
				: of(value);
	}

	public static <T> Œ<T> doTry(_$<T> f) {
		try {
			return of(f.get());
		} catch (Exception e) {
			return failure(e);
		}
	}
}
