package pl.edu.radomski.navigator.utils.tuple;

public class Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> {

    public final T9 ninth;

    public Tuple9(T1 first, T2 second, T3 third, T4 fourth, T5 fifth, T6 sixth, T7 seventh, T8 eighth, T9 ninth) {
        super(first, second, third, fourth, fifth, sixth, seventh, eighth);
        this.ninth = ninth;
    }
}
