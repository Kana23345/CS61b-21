package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> com;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.com = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        T t;
        for (int i = 1; i < size(); i++) {
            t = get(i);
            if (com.compare(max, t) < 0) {
                max = t;
            }
        }
        return max;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        T t;
        for (int i = 1; i < size(); i++) {
            t = get(i);
            if (c.compare(max, t) < 0) {
                max = t;
            }
        }
        return max;
    }
}
