package pl.edu.radomski.navigator.utils.tuple;


public class Tuple7<T1, T2, T3, T4, T5, T6, T7> extends Tuple6<T1, T2, T3, T4, T5, T6> {

    public final T7 seventh;

    Tuple7(T1 first, T2 second, T3 third, T4 fourth, T5 fifth, T6 sixth, T7 seventh) {
        super(first, second, third, fourth, fifth, sixth);
        this.seventh = seventh;
    }
}
