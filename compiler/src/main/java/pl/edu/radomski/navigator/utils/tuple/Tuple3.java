package pl.edu.radomski.navigator.utils.tuple;


public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {

    public final T3 third;

    Tuple3(T1 first, T2 second, T3 third) {
        super(first, second);
        this.third = third;
    }
}
