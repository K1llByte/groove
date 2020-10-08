package Model.Utils.option;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import Model.Utils.panic.Panic;
import Model.Utils.result.Err;
import Model.Utils.result.Ok;
import Model.Utils.result.Result;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Option<T> {
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> Option<T> Some(T value) {
        return new Option<>(value);
    }

    public static final Option None = new Option<>();

    @Contract(pure = true)
    public final boolean isSome() {
        return this.value != null;
    }

    @Contract(pure = true)
    public final boolean isNone() {
        return this.value == null;
    }

    @Contract(pure = true)
    public final boolean contains(T x) {
        return (this.value != null) && (this.value.equals(x));
    }

    public final T expect(String msg) {
        if (this.value != null) {
            return this.value;
        }
        throw new Panic(msg);
    }

    public final T unwrap() {
        if (this.value != null) {
            return this.value;
        }
        throw new Panic(
            "called `Option.unwrap()` on a `None` value"
        );
    }

    @Contract(pure = true)
    public final T unwrapOr(T def) {
        return this.value != null ? this.value : def;
    }

    public final T unwrapOrElse(Supplier<? extends T> f) {
        return this.value != null ? this.value : f.get();
    }

    public final <U> Option<U> map(Function<? super T, ? extends U> f) {
        return this.value != null ? Some(f.apply(this.value)) : None;
    }

    public final <U> U mapOr(U def, Function<? super T, ? extends U> f) {
        return this.value != null ? f.apply(this.value) : def;
    }

    public final <U> U mapOrElse(
        Supplier<? extends U> def,
        Function<? super T, ? extends U> f
    ) {
        return this.value != null ? f.apply(this.value) : def.get();
    }

   /* public final <E> Result<T, E> okOr(E err) {
        return this. value != null ? Ok(this.value) : Err(err);
    }

    public final <E> Result<T, E> okOrElse(Supplier<? extends E> err) {
        return this.value != null ? Ok(this.value) : Err(err.get());
    }*/

    @Contract(pure = true)
    public final <U> Option<U> and(Option<U> optb) {
        return this.value != null ? optb : None;
    }

    public final <U> Option<U> andThen(Function<? super T, Option<U>> f) {
        return this.value != null ? f.apply(this.value) : None;
    }

    public final Option<T> filter(Predicate<? super T> predicate) {
        return (this.value != null) && (predicate.test(this.value))
            ? Some(this.value)
            : None;
    }

    @Contract(pure = true)
    public final Option<T> or(Option<T> optb) {
        return this.value != null ? this : optb;
    }

    public final Option<T> orElse(Supplier<Option<T>> f) {
        return this.value != null ? this : f.get();
    }

    public final Option<T> xor(@NotNull Option<T> optb) {
        final var some_b = optb.value != null;
        if (this.value != null) {
            return some_b ? None : Some(this.value);
        }
        return (some_b) ? Some(optb.value) : None;
    }

    public final T getOrInsert(T v) {
        if (this.value == null) {
            this.value = v;
        }
        return this.value;
    }

    public  final T getOrInsertWith(Supplier<? extends T> f) {
        if (this.value == null) {
            this.value = f.get();
        }
        return this.value;
    }

    @NotNull
    public final Option<T> replace(T value) {
        final var old_value = this.value;
        this.value = value;
        return old_value != null ? Some(old_value) : None;
    }

    public final void expectNone(String msg) {
        if (this.value != null) {
            throw new Panic(msg);
        }
    }

    public final void unwrapNone() {
        if (this.value != null) {
            throw new Panic(
                "called `Option.unwrapNone()` on a `Some` value: " + this.value
            );
        }
    }

    @Override
    @Contract(value = "null -> false", pure = true)
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        return Objects.equals(this.value, ((Option<?>)o).value);
    }

    @Override
    public int hashCode() {
        return this.value != null ? this.value.hashCode() : 0;
    }

    @Contract(pure = true)
    private Option() {
        this.value = null;
    }

    @Contract(pure = true)
    private Option(T value) {
        this.value = value;
    }

    private T value;
}
