import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<E> {

    private final Comparator<? super E> comparator;
    private static final int DEFAULT_CAPACITY = 10;

    private int size;
    private Object[] arr;

    public Heap() {
        this(null);
    }

    public Heap(Comparator<? super E> comparator) {
        this.arr = new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    public Heap(int capacity) {
        this(capacity, null);
    }

    public Heap(int capacity, Comparator<? super E> comparator) {
        this.arr = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    // 부모 노드 인덱스 반환
    private int getParentIdx(int index) {
        return index / 2;
    }

    // 왼쪽 자식 노드 인덱스 반환
    private int getLeftChildIdx(int index) {
        return index * 2;
    }

    // 오른쪽 자식 노드 인덱스 반환
    private int getRightChildIdx(int index) {
        return index * 2 + 1;
    }

    private void resize(int newCapacity) {
        Object[] newArr = new Object[newCapacity];

        for (int i = 1; i <= size; i++) {
            newArr[i] = arr[i];
        }

        this.arr = newArr;
    }

    public void insert(E value) {

        if (size + 1 == arr.length) {
            resize(arr.length * 2);
        }

        // 추가될 위치와 넣을 값을 넘겨준다.
        shiftUp(size + 1, value);
        size++;
    }

    private void shiftUp(int index, E target) {
        if (comparator != null) {
            shiftUpComparator(index, target, comparator);
        }
        else
            shiftUpComparable(index, target);
    }

    @SuppressWarnings("unchecked")
    private void shiftUpComparator(int index, E target, Comparator<? super E> comparator) {

        while (index > 1) {
            int parent = getParentIdx(index);
            Object parentVal = arr[parent];

            // 타겟 노드 값이 부모노드보다 크면 반복문 종료
            if (comparator.compare(target, (E) parentVal) >= 0) {
                break;
            }

            // 부모 노드와 현재 노드를 바꾼다.
            arr[index] = parentVal;
            index = parent;
        }
        // 최종 위치에 타겟 노드값 저장
        arr[index] = target;
    }

    @SuppressWarnings("unchecked")
    public void shiftUpComparable(int index, E target) {
        Comparable<? super E> comparable = (Comparable<? super E>) target;

        while (index > 1) {
            int parentIdx = getParentIdx(index);
            Object parentVal = arr[parentIdx];

            if (comparable.compareTo((E) parentVal) >= 0) {
                break;
            }

            arr[index] = parentVal;
            index = parentIdx;
        }

        arr[index] = target;
    }

    @SuppressWarnings("unchecked")
    public E getMin() {
        if (arr[1] == null) {
            throw new NoSuchElementException();
        }

        E result = (E) arr[1];
        E target = (E) arr[size];
        arr[size] = null;

        // 루트 노드의 인덱스와 재배치할 타겟 노드를 넘겨준다.
        shiftDown(1, target);
        return result;
    }

    public void shiftDown(int index, E target) {
        if (comparator != null) {
            shiftDownComparator(index, target, comparator);
        }
        else {
            shiftDownComparable(index, target);
        }
    }

    @SuppressWarnings("unchecked")
    private void shiftDownComparator(int index, E target, Comparator<? super E> comparator) {
        arr[index] = null;
        size--;

        int parent = index;
        int child;

        // 왼쪽 자식 노드의 인덱스가 요소의 개수보다 작을때까지 반복
        while ((child = getLeftChildIdx(parent)) <= size) {

            int right = getRightChildIdx(parent);
            Object childVal = arr[child];

            // 오른쪽 자식 인덱스가 size를 넘지 않으면서, 왼쪽 자식이 오른쪽 자식보다 큰 경우
            // 자식 노드를 오른쪽 자식 노드로 바꿔준다.
            if (right <= size && comparator.compare((E) childVal, (E) arr[right]) > 0) {
                child = right;
                childVal = arr[child];
            }

            // 타겟 노드가 자식 노드보다 작을 경우 반복문 종료
            if (comparator.compare(target, (E) childVal) <= 0) {
                break;
            }

            // 부모 노드와 자식 노드를 바꾼다.
            arr[parent] = childVal;
            parent = child;
        }

        // 최종 위치에 타겟값을 넣어준다.
        arr[parent] = target;

        if (arr.length > DEFAULT_CAPACITY && size < arr.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, arr.length / 2));
        }
    }

    @SuppressWarnings("unchecked")
    private void shiftDownComparable(int index, E target) {

        Comparable<? super E> comparable = (Comparable<? super E>) target;

        arr[index] = null;
        size--;

        int parentIdx = index;
        int childIdx;

        while((childIdx = getLeftChildIdx(parentIdx)) <= size) {

            int rightIdx = getRightChildIdx(parentIdx);

            Object childVal = arr[childIdx];

            if(rightIdx <= size && ((Comparable<? super E>)childVal).compareTo((E)arr[rightIdx]) > 0) {
                childIdx = rightIdx;
                childVal = arr[childIdx];
            }

            if(comparable.compareTo((E) childVal) <= 0){
                break;
            }
            arr[parentIdx] = childVal;
            parentIdx = childIdx;

        }
        arr[parentIdx] = target;

        if(arr.length > DEFAULT_CAPACITY && size < arr.length / 4) {
            resize(Math.max(DEFAULT_CAPACITY, arr.length / 2));
        }
    }
}
