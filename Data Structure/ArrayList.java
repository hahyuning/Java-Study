import java.util.*;

public class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ARRAY = {};

    Object[] arr = null;
    int capacity = 0; // 용량
    int size = 0; // 데이터 개수

    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        arr = new Object[capacity];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public void resize() {
        if (Arrays.equals(arr, EMPTY_ARRAY)) {
            arr = new Object[DEFAULT_CAPACITY];
            return;
        }

        if (size == capacity) {
            Object[] tmp = new Object[2 * capacity];
            System.arraycopy(arr, 0, tmp, 0, size);
            arr = tmp;
            capacity = 2 * capacity;
        }

        if (size < (capacity / 2)) {
            capacity = capacity / 2;

            arr = Arrays.copyOf(arr, Math.max(capacity, DEFAULT_CAPACITY));
        }
    }

    public boolean add(E value) {
        if (size == capacity) {
            resize();
        }

        arr[size++] = value;
        return true;
    }

    @Override
    public void add(int index, E value) {

        if (index >= capacity || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            add(value);
        }
        else {
            if (size == capacity) {
                resize();
            }

            if (size - index >= 0) System.arraycopy(arr, index, arr, index + 1, size - index);

            arr[index] = value;
            size++;
        }
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (E) arr[index];
    }

    // 데이터 교체
    @Override
    public E set(int index, E value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        arr[index] = value;
        return value;
    }

    public E remove(int index) {
        Object oldObj = null;

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        oldObj = arr[index];
        // 삭제하고자 하는 객체가 마지막 객체가 아닌 경우
        if (index != size - 1) {
            System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        }

        // 마지막 데이터를 null로 변경
        arr[--size] = null;
        return (E) oldObj;
    }

    public boolean remove(Object value) {
        for (int i = 0; i < size; i++) {
            if (value.equals(arr[i])) {
                remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (arr[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (arr[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void sort(Comparator c) {
        List.super.sort(c);
    }


    public void clear() {
        for (int i = 0; i < size; i++) {
            arr[i] = null;
        }
        size = 0;
        resize();
    }

    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(arr, 0, result, 0, size);

        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(arr, size, a.getClass());
        }

        System.arraycopy(arr, 0, a, 0, size);
        return a;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    public int size() {
        return size;
    }
}