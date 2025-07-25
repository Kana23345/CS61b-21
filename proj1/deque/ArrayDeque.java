package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
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
        T t = array[nextFirst];
        size--;
        array[nextFirst] = null;
        return t;

    }

    @Override
    public T removeLast() {
        if (isEmpty())
            return null;
        nextLast = (nextLast - 1 + array.length) % array.length;
        size--;
        T t = array[nextLast];
        array[nextLast] = null;
        return t;

    }

    private int getRealPos(int index) {
        int realFirst = (nextFirst + 1) % array.length;
        int realPos = (realFirst + index) % array.length;
        return realPos;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0 || size == 0)
            return null;
        int realIndex = getRealPos((index % size + size) % size);
        return array[realIndex];
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Deque)) return false;
        Deque<T> other = (Deque<T>) o;
        if (other.size() != size) return false;
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(other.get(i))) return false;
        }
        return true;
    }

    private class DequeIterator implements Iterator<T> {
        private int curPos; // 表示即将访问的索引位置，初始即将访问索引0

        DequeIterator() {
            curPos = getRealPos(0);
        }


        @Override
        public boolean hasNext() {
            if (curPos % array.length == nextLast) {
                return false;
            }
            if (array[curPos % array.length] != null) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            return array[curPos++];
        }

    }
}
