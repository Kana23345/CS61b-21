package deque;

public class ArrayDeque<T> implements Deque<T> {
    private T[] array;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        this.array = (T[]) new Object[8];
        this.nextFirst = array.length - 1;
        this.nextLast = 0;
        this.size = 0;
    }

    private void resizing(int capacity) {
        T[] newArray = (T[]) (new Object[capacity]);
        for (int i = 0; i < size(); i++) {
            newArray[i] = get(i);
        }
        this.array = newArray;
        nextFirst = array.length - 1;
        nextLast = size();
    }

    private void checkFull() {
        if (nextFirst == nextLast) {
            resizing(size * 2);
        }
    }

    @Override
    public void addFirst(T item) {
        checkFull();
        array[nextFirst] = item;
        nextFirst = (nextFirst - 1 + array.length) % array.length;
        size++;

    }

    @Override
    public void addLast(T item) {
        checkFull();
        array[nextLast] = item;
        nextLast = (nextLast + 1) % array.length;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty())
            return null;
        nextFirst = (nextFirst + 1) % array.length;
        size--;
        return array[nextFirst];

    }

    @Override
    public T removeLast() {
        if (isEmpty())
            return null;
        size--;
        nextLast = (nextLast - 1 + array.length) % array.length;
        return array[nextLast];

    }

    private int getRealPos(int index) {
        int realFirst = (nextFirst + 1) % array.length;
        int realPos = (realFirst + index) % array.length;
        return realPos;
    }

    @Override
    public T get(int index) {
        if (size == 0)
            return null;
        int realIndex = getRealPos((index % size + size) % size);
        return array[realIndex];
    }
}
