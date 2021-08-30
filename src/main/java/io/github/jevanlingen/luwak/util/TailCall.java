package io.github.jevanlingen.luwak.util;

import io.github.jevanlingen.luwak.function.$;

public abstract class TailCall<T> {
	private TailCall() {}

	public abstract T eval();

	public abstract T evalAccumulator();

	public abstract boolean hasNext();
	
	private static class Return<T> extends TailCall<T> {
		private final T t;

		public Return(T t) {
			this.t = t;
		}

		@Override
		public T eval() {
			return t;
		}

		@Override
		public T evalAccumulator() {
			throw new IllegalStateException("Return does not have a next item");
		}

		@Override
		public boolean hasNext() {
			return false;
		}
	}

	private static class Next<T> extends TailCall<T> {
		private final $<T> suppliedType;
		private final $<T> accumulator;

		private Next($<T> suppliedType, $<T> accumulator) {
			this.suppliedType = suppliedType;
			this.accumulator = accumulator;
		}

		public T eval() {
			return suppliedType.get();
		}
		
		public T evalAccumulator() {
			return accumulator.get();
		}

		@Override
		public boolean hasNext() {
			return true;
		}
	}

	public static <T> TailCall<T> ret(T t) {
		return new Return<>(t);
	}

	public static <T, U> TailCall<T> next($<T> suppliedType, $<T> accumulator) {
		return new Next<>(suppliedType, accumulator);
	}
}