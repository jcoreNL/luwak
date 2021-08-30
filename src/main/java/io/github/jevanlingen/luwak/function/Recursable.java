package io.github.jevanlingen.luwak.function;

import java.util.Objects;
import java.util.function.BiFunction;

import io.github.jevanlingen.luwak.util.TailCall;

@FunctionalInterface
public interface Recursable<T, U> {
	U apply(T t, Recursable<T, U> r);

	/**
	 * <p>Make a recursive lambda.</p>
	 * <br>
	 * <p>Factorial example:</p>
	 * <pre>{@literal ƒ<Integer, Integer>} fact = recurse((i, f) -> 0 == i ? 1 : i * f.apply(i - 1, f));</pre>
	 *
	 * @param <T> the type of the input of the function
	 * @param <U> the type of the result of the function
	 * @param function a BiFunction to create the recursive lambda
	 * @return a recursive function
	 * @throws NullPointerException if function is {@code null}
	 */
	static <T, U> ƒ<T, U> recurse(Recursable<T, U> function) {
		Objects.requireNonNull(function);
		return t -> function.apply(t, function);
	}

	/**
	 * <p>Make a tail recursive lambda.</p>
	 * <br>
	 * <p>Factorial example:</p>
	 * <pre>{@literal ƒ<Integer, Integer>} fact = tailRecurse((input, accumulator) ->
	 *     0 == accumulator ? ret(1) :
	 *     1 == accumulator ? ret(input) :
	 *     next(() -> input * (accumulator - 1), () -> accumulator - 1));
	 * </pre>
	 *
	 * @param <T> the type of the function
	 * @param function a BiFunction to create the tail recursive lambda
	 * @return a recursive function
	 * @throws NullPointerException if function is {@code null}
	 */
	static <T> ƒ<T, T> tailRecurse(BiFunction<T, T, TailCall<T>> function) {
		Objects.requireNonNull(function);
		return t -> _tailRecurse(function).apply(t, t);
	}

	/**
	 * <p>Make a tail recursive lambda with a provided input.</p>
	 * <br>
	 * <p>Factorial example:</p>
	 * <pre>{@literal ƒ<Double, Double>} f = tailRecurse(0.0, (input, accumulator) ->
	 *     accumulator > 20.0 ? ret(input + 1.0) :
	 *     next(() -> 0.25 + input, () -> accumulator + 1.0));
	 * </pre>
	 *
	 * @param <T> the type of the function
	 * @param identity the input starting value
	 * @param function a BiFunction to create the tail recursive lambda
	 * @return a recursive function
	 * @throws NullPointerException if either identity or function are null
	 */
	static <T> ƒ<T, T> tailRecurse(T identity, BiFunction<T, T, TailCall<T>> function) {
		Objects.requireNonNull(identity);
		Objects.requireNonNull(function);
		return accumulator -> _tailRecurse(function).apply(identity, accumulator);
	}

	/**
	 * <p>Make a tail recursive lambda with a provided input.</p>
	 * <br>
	 * <p>Example:</p>
	 * <pre>{@literal ƒ<Integer, Double>} f = tailRecurse(0.0, (input, accumulator) ->
	 *     accumulator > 20.0 ? ret(input + 1.0) :
	 *     next(() -> 0.25 + input, () -> accumulator + 1.0), Double::valueOf);
	 * </pre>
	 *
	 * @param <T> the type of the input of the function
	 * @param <U> the type of the result of the function
	 * @param identity the input starting value
	 * @param function a BiFunction to create the tail recursive lambda
	 * @param mapper a function to map the provided accumulator to the right type
	 * @return a recursive function
	 * @throws NullPointerException if either identity or function are null
	 */
	static <T, U> ƒ<T, U> tailRecurse(U identity, BiFunction<U, U, TailCall<U>> function, ƒ<T, U> mapper) {
		Objects.requireNonNull(identity);
		Objects.requireNonNull(function);
		Objects.requireNonNull(mapper);
		return t -> _tailRecurse(function).apply(identity, mapper.apply(t));
	}

	/**
	 * <p>Make a tail recursive lambda with a provided accumulator.</p>
	 * <br>
	 * <p>Example:</p>
	 * <pre>{@literal ƒ<Integer, Double>} f = tailRecurse((input, accumulator) ->
	 *     accumulator > 20.0 ? ret(input + 1.0) :
	 *     next(() -> 0.25 + input, () -> accumulator + 1.0), 0.0);
	 * </pre>
	 *
	 * @param <T> the type of the function
	 * @param function a BiFunction to create the tail recursive lambda
	 * @param accumulator the accumulator starting value
	 * @return a recursive function
	 * @throws NullPointerException if either identity or function are null
	 */
	static <T> ƒ<T, T> tailRecurse(BiFunction<T, T, TailCall<T>> function, T accumulator) {
		Objects.requireNonNull(function);
		Objects.requireNonNull(accumulator);
		return t -> _tailRecurse(function).apply(t, accumulator);
	}

	/**
	 * <p>Make a tail recursive lambda with a provided accumulator.</p>
	 * <br>
	 * <p>Example:</p>
	 * <pre>{@literal ƒ<Integer, Double>} f = tailRecurse((input, accumulator) ->
	 *     accumulator > 20.0 ? ret(input + 1.0) :
	 *     next(() -> 0.25 + input, () -> accumulator + 1.0), 0.0, Double::valueOf);
	 * </pre>
	 *
	 * @param <T> the type of the input of the function
	 * @param <U> the type of the result of the function
	 * @param function a BiFunction to create the tail recursive lambda
	 * @param accumulator the accumulator starting value
	 * @param mapper a function to map the provided input to the right type
	 * @return a recursive function
	 * @throws NullPointerException if either identity or function are null
	 */
	static <T, U> ƒ<T, U> tailRecurse(BiFunction<U, U, TailCall<U>> function, U accumulator, ƒ<T, U> mapper) {
		Objects.requireNonNull(function);
		Objects.requireNonNull(accumulator);
		Objects.requireNonNull(mapper);
		return t -> _tailRecurse(function).apply(mapper.apply(t), accumulator);
	}

	private static <T> BiFunction<T, T, T> _tailRecurse(BiFunction<T, T, TailCall<T>> function) {
		return (identity, accumulator) -> {
			var tc = function.apply(identity, accumulator);
			while (tc.hasNext()) {
				tc = function.apply(tc.eval(), tc.evalAccumulator());
			}

			return tc.eval();
		};
	}
}