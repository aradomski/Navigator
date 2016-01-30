package pl.edu.radomski.navigator.utils.tuple;


public class Tuple {

    public static <T1, T2> Tuple2<T1, T2> make(T1 first, T2 second) {
        return new Tuple2<>(first, second);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> make(T1 first, T2 second, T3 third) {
        return new Tuple3<>(first, second, third);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> make(T1 first, T2 second, T3 third, T4 fourth) {
        return new Tuple4<>(first, second, third, fourth);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> make(T1 first, T2 second, T3 third, T4 fourth, T5 fifth) {
        return new Tuple5<>(first, second, third, fourth, fifth);
    }

    public static <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> make(T1 first, T2 second, T3 third, T4 fourth, T5 fifth, T6 sixth ) {
        return new Tuple6<>(first, second, third, fourth, fifth, sixth);
    }

    public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> make(T1 first, T2 second, T3 third, T4 fourth, T5 fifth, T6 sixth, T7 seventh) {
        return new Tuple7<>(first, second, third, fourth, fifth, sixth, seventh);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> make(T1 first, T2 second, T3 third, T4 fourth, T5 fifth, T6 sixth, T7 seventh, T8 eighth) {
        return new Tuple8<>(first, second, third, fourth, fifth, sixth, seventh, eighth);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> make(T1 first, T2 second, T3 third, T4 fourth, T5 fifth, T6 sixth, T7 seventh, T8 eighth, T9 nineth) {
        return new Tuple9<>(first, second, third, fourth, fifth, sixth, seventh, eighth, nineth);
    }

    private Tuple() {
    }
}
