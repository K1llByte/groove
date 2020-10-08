package Model.Utils.result;

import Model.Utils.option.Option;
import Model.Utils.panic.Panic;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import static Model.Utils.option.Option.None;
import static Model.Utils.option.Option.Some;

public final class Err<T, E> implements Result<T, E> {
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T, E> Result<T, E> Err(E error) {
        return new Err<>(error);
    }

    @Contract(pure = true)
    public final boolean isOk() {
        return false;
    }

    @Contract(pure = true)
    public final boolean isErr() {
        return true;
    }

    @Contract(pure = true)
    public final boolean contains(T _x) {
        return false;
    }

    @Contract(value = "null -> false", pure = true)
    public final boolean containsErr(E e) {
        return this.error.equals(e);
    }

    @Contract(pure = true)
    public final Option<T> ok() {
        return None;
    }

    @NotNull
    @Contract(pure = true)
    public final Option<E> err() {
        return Some(this.error);
    }

    @NotNull
    @Contract(pure = true)
    public final <U> Result<U, E> map(Function<? super T, ? extends U> _op) {
        return Err(this.error);
    }

    public final <U> U mapOrElse(
        @NotNull Function<? super E, ? extends U> fallback,
        Function<? super T, ? extends U> _map
    ) {
        return fallback.apply(this.error);
    }

    @NotNull
    public final <F> Result<T, F> mapErr(
        @NotNull Function<? super E, ? extends F> op
    ) {
        return Err(op.apply(this.error));
    }

    @NotNull
    @Contract(pure = true)
    public final <U> Result<U, E> and(Result<U, E> _res) {
        return Err(this.error);
    }

    @NotNull
    @Contract(pure = true)
    public final <U> Result<U, E> andThen(
        Function<? super T, Result<U, E>> _op
    ) {
        return Err(this.error);
    }

    @Contract(value = "_ -> param1", pure = true)
    public final <F> Result<T, F> or(Result<T, F> res) {
        return res;
    }

    public final <F> Result<T, F> orElse(
        @NotNull Function<? super E, Result<T, F>> op
    ) {
        return op.apply(this.error);
    }

    @Contract(value = "_ -> param1", pure = true)
    public final T unwrapOr(T optb) {
        return optb;
    }

    public final T unwrapOrElse(
        @NotNull Function<? super E, ? extends T> op
    ) {
        return op.apply(this.error);
    }

    @Contract(" -> fail")
    public final T unwrap() {
        throw new Panic(
            "called `Result.unwrap()` on an `Err` value: " + this.error
        );
    }

    @Contract("_ -> fail")
    public final T expect(String msg) {
        throw new Panic(msg);
    }

    @Contract(pure = true)
    public final E unwrapErr() {
        return this.error;
    }

    @Contract(pure = true)
    public final E expectErr(String _msg) {
        return this.error;
    }

    @Contract(value = "_ -> this", pure = true)
    public final Result<T, E> andDo(Runnable op) {
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
        return that.isErr() && this.error.equals(that.unwrapErr());
    }

    @Override
    public int hashCode() {
        return error.hashCode();
    }

    @Contract(pure = true)
    private Err(E error) {
        this.error = error;
    }

    private final E error;
}
