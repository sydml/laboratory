import java.util.Objects;

/**
 * @Author: Liuyuming
 * @Date: 2020/2/5 23:06
 */
public class Tuple<T, U, K> {
    public final T _1;
    public final U _2;
    public final K _3;

    public Tuple(T _1, U _2, K _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tuple<?, ?, ?> tuple = (Tuple<?, ?, ?>) o;
        return Objects.equals(_1, tuple._1) && Objects.equals(_2, tuple._2) && Objects.equals(_3,tuple._3);
    }

    @Override
    public int hashCode() {

        return Objects.hash(_1, _2, _3);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "_1=" + _1 +
                ", _2=" + _2 +
                ", _3=" + _3 +
                '}';
    }
}

