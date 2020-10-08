package Model.Utils.result;

import Model.Utils.option.Option;
import Model.Utils.panic.Panic;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import static Model.Utils.option.Option.None;
import static Model.Utils.option.Option.Some;

public final class Ok<T, E> implements Result<T, E> {
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T, E> Result<T, E> Ok(T value) {
        return new Ok<>(value);
    }

    @Contract(pure = true)
    public final boolean isOk() {
        return true;
    }

    @Contract(pure = true)
    public final boolean isErr() {
        return false;
    }

    @Contract(value = "null -> false", pure = true)
    public final boolean contains(T x) {
        return this.value.equals(x);
    }

    @Contract(pure = true)
    public final boolean containsErr(E _e) {
        return false;
    }

    @NotNull
    @Contract(pure = true)
    public final Option<T> ok() {
        return Some(this.value);
    }

    @Contract(pure = true)
    public final Option<E> err() {
        return None;
    }

    @NotNull
    public final <U> Result<U, E> map(
        @NotNull Function<? super T, ? extends U> op
    ) {
        return Ok(op.apply(this.value));
    }

    public final <U> U mapOrElse(
        Function<? super E, ? extends U> _fallback,
        @NotNull Function<? super T, ? extends U> map
    ) {
        return map.apply(this.value);
    }

    @NotNull
    @Contract(pure = true)
    public final <F> Result<T, F> mapErr(
        Function<? super E, ? extends F> _op
    ) {
        return Ok(this.value);
    }

    @Contract(value = "_ -> param1", pure = true)
    public final <U> Result<U, E> and(Result<U, E> res) {
        return res;
    }

    public final <U> Result<U, E> andThen(
        @NotNull Function<? super T, Result<U, E>> op
    ) {
        return op.apply(this.value);
    }

    @NotNull
    @Contract(pure = true)
    public final <F> Result<T, F> or(Result<T, F> _res) {
        return Ok(this.value);
    }

    @NotNull
    @Contract(pure = true)
    public final <F> Result<T, F> orElse(
        Function<? super E, Result<T, F>> _op
    ) {
        return Ok(this.value);
    }

    @Contract(pure = true)
    public final T unwrapOr(T _optb) {
        return this.value;
    }

    @Contract(pure = true)
    public final T unwrapOrElse(Function<? super E, ? extends T> _op) {
        return this.value;
    }

    @Contract(pure = true)
    public final T unwrap() {
        return this.value;
    }

    @Contract(pure = true)
    public final T expect(String s) {
        return this.value;
    }

    @Contract(" -> fail")
    public final E unwrapErr() {
        throw new Panic(
            "called `Result.unwrapErr()` on an `Ok` value: " + this.value
        );
    }

    @Contract("_ -> fail")
    public final E expectErr(String msg) {
        throw new Panic(msg);
    }

    @Contract("_ -> this")
    public final Result<T, E> andDo(Runnable op) {
        op.run();
        return this;
    }

    @Override
    @Contract(value = "null -> false", pure = true)
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final var that = (Result<?, ?>)o;
        return that.isOk() && this.value.equals(that.unwrap());
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Contract(pure = true)
    private Ok(T value) {
        this.value = value;
    }

    private final T value;
}
