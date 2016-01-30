package pl.edu.radomski.navigator.utils.tuple;

public class Tuple4<T1, T2, T3, T4> extends Tuple3<T1, T2, T3> {

    public final T4 fourth;

    Tuple4(T1 first, T2 second, T3 third, T4 fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }
}
