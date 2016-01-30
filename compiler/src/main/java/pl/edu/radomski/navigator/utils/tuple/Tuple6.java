package pl.edu.radomski.navigator.utils.tuple;


public class Tuple6<T1, T2, T3, T4, T5, T6> extends Tuple5<T1, T2, T3, T4, T5> {

    public final T6 sixth;

    Tuple6(T1 first, T2 second, T3 third, T4 fourth, T5 fifth, T6 sixth) {
        super(first, second, third, fourth, fifth);
        this.sixth = sixth;
    }
}
