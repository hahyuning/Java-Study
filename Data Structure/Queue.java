import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Queue<E> extends LinkedList<E> {

    static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public Queue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean offer(E value) {
        Node<E> newNode = new Node<>(value);

        if (size == 0) {
            head = newNode;
        }
        else {
            tail.next = newNode;
        }

        tail = newNode;
        size++;

        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        E value = head.data;

        head = head.next;
        size--;

        return value;
    }

    @Override
    public E remove() {
        E value = poll();

        if (value == null) {
            throw new NoSuchElementException();
        }

        return value;
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }

        return head.data;
    }

    @Override
    public E element() {
        E element = peek();

        if (element == null) {
            throw new NoSuchElementException();
        }

        return element;
    }
}
