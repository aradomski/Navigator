package pl.edu.radomski.navigator.utils.tuple;


public class Tuple5<T1, T2, T3, T4, T5> extends Tuple4<T1, T2, T3, T4> {

    public final T5 fifth;

    Tuple5(T1 first, T2 second, T3 third, T4 fourth, T5 fifth) {
        super(first, second, third, fourth);
        this.fifth = fifth;
    }
}
