import java.lang.reflect.Array;
import java.util.*;

public class LinkedList<E> implements List<E> {

    static class Node<E> {
        E data;
        Node<E> prev;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // 특정 위치의 노드를 반환
    private Node<E> search(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index > size / 2) {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        } else {
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.next = head;

        // head 노드가 없는 경우에는 NullPointException
        if (head != null) {
            head.prev = newNode;
        }

        head = newNode;
        size++;

        // 요소가 새 노드밖에 없는 경우 새 노드는 head이자 tail
        if (head.next == null) {
            tail = head;
        }
    }

    @Override
    public boolean add(E value) {
        if (size == 0) {
            addFirst(value);
        } else {
            addLast(value);
        }
        return true;
    }

    public void addLast(E value) {
        Node<E> newNode = new Node<>(value);

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }

    @Override
    public void add(int index, E value) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(value);
            return;
        }

        if (index == size) {
            addLast(value);
            return;
        }

        Node<E> prevNode = search(index - 1);
        Node<E> nextNode = prevNode.next;
        Node<E> newNode = new Node<>(value);

        prevNode.next = newNode;
        nextNode.prev = newNode;

        newNode.prev = prevNode;
        newNode.next = nextNode;

        size++;
    }

    public E remove() {
        Node<E> headNode = head;

        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E result = headNode.data;

        Node<E> nextNode = headNode.next;
        if (nextNode != null) {
            nextNode.prev = null;
        }

        head = nextNode;
        size--;

        if (size == 0) {
            tail = null;
        }

        return result;
    }

    @Override
    public E remove(int index) {

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return remove();
        }

        Node<E> prevNode = search(index - 1);
        Node<E> node = prevNode.next;
        Node<E> nextNode = node.next;

        E result = node.data;

        prevNode.next = nextNode;

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
        size--;

        return result;
    }

    @Override
    public boolean remove(Object value) {
        Node<E> prevNode = head;
        Node<E> now = head;

        while (now != null) {
            if (value.equals(now.data)) {
                break;
            }
            prevNode = now;
            now = now.next;
        }

        if (now == null) {
            return false;
        }

        if (now.equals(head)) {
            remove();
        }
        else {
            Node<E> nextNode = now.next;
            prevNode.next = nextNode;

            if (nextNode != null) {
                nextNode.prev = prevNode;
            }
            else {
                tail = prevNode;
            }

            size--;
        }
        return true;
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public E set(int index, E value) {
        Node<E> node = search(index);
        node.data = value;
        return value;
    }

    @Override
    public int indexOf(Object value) {
        int index = 0;
        Node<E> now = head;

        while (now != null) {
            if (value.equals(now.data)) {
                return index;
            }
            index++;
            now = now.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        int index = size;
        Node<E> now = tail;

        while (now != null) {
            index--;
            if (value.equals(now.data)) {
                return index;
            }
            now = now.prev;
        }
        return -1;
    }

    @Override
    public boolean contains(Object item) {
        return indexOf(item) >= 0;
    }

    // 연결리스트에 있는 요소의 수만큼 배열의 크기가 할당되어 반환
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        int index = 0;

        Node<E> node = head;
        while (node != null) {
            arr[index++] = (E) node.data;
            node = node.next;
        }
        return arr;
    }

    // 객체 클래스로 상속관계에 있는 타입이거나 데이터 타입을 유연하게 캐스팅 가능
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        int index = 0;
        // 얕은 복사
        Object[] result = a;

        Node<E> node = head;
        while (node != null) {
            // 타입 때문에 a에 바로 값을 할당할 수 없으므로, 얕은 복사 이용
            result[index++] = node.data;
            node = node.next;
        }
        return a;
    }

    @Override
    public void clear() {
        Node<E> now = head;

        while (now != null) {
            now = now.next;
        }

        head = tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
