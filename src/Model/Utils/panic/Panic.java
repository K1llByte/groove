package Model.Utils.panic;

import org.jetbrains.annotations.Contract;

public final class Panic extends RuntimeException {
    @Contract(" -> fail")
    public static void panic() {
        throw new Panic();
    }

    @Contract("_ -> fail")
    public static void panic(String msg) {
        throw new Panic(msg);
    }

    public Panic() {
        super();
    }

    public Panic(String msg) {
        super("panicked at '" + msg + '\'');
    }
}
