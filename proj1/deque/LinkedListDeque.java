package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private final Node<T> frontSentinel;
    private final Node<T> lastSentinel;
    private int size;

    public LinkedListDeque() {
        this.frontSentinel = new Node<>(null, null, null);
        this.size = 0;
        this.lastSentinel = new Node<>(null, null, frontSentinel);
        frontSentinel.next = lastSentinel;
    }

    @Override
    public void addFirst(T item) {
        Node<T> last_temp = frontSentinel.next;
        Node<T> temp = new Node<>(item, last_temp, frontSentinel);
        last_temp.prev = temp;
        frontSentinel.next = temp;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node<T> front_temp = lastSentinel.prev;
        Node<T> temp = new Node<>(item, lastSentinel, front_temp);
        front_temp.next = temp;
        lastSentinel.prev = temp;
        size++;
    }

    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node<T> p = frontSentinel;
        for (int i = 0; i < size; i++) {
            System.out.print(p.next.item.toString() + " ");
            p = p.next;
        }
        System.out.println();
    }


    @Override
    public T removeFirst() {
        if (size == 0)
            return null;
        Node<T> temp = frontSentinel.next.next;
        T item_temp = frontSentinel.next.item;
        frontSentinel.next = temp;
        temp.prev = frontSentinel;
        size--;
        return item_temp;
    }

    @Override
    public T removeLast() {
        if (size == 0)
            return null;
        Node<T> temp = lastSentinel.prev.prev;
        T item_temp = lastSentinel.prev.item;
        lastSentinel.prev = temp;
        temp.next = lastSentinel;
        size--;
        return item_temp;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0 || size == 0) {
            return null;
        } else {
            Node<T> point = frontSentinel.next;
            for (int i = 0; i < index; i++) {
                point = point.next;

            }
            return point.item;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

    private T recursiveTool(Node<T> p, int index) {
        if (index == 0) {
            return p.item;
        }
        return recursiveTool(p.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else {
            return recursiveTool(frontSentinel.next, index);
        }
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
        private int curPos;

        DequeIterator() {
            curPos = 0;
        }

        @Override
        public boolean hasNext() {
            if (curPos < size) return true;
            return false;
        }

        @Override
        public T next() {
            return get(curPos++);
        }
    }

    private class Node<T> {
        public T item;
        public Node<T> prev;
        public Node<T> next;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

    }
}
