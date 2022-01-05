import java.util.ArrayList;
import java.util.EmptyStackException;

public class Stack<E> extends ArrayList<E> {

    public Stack() {
        super();
    }

    public Stack(int capacity) {
        super(capacity);
    }

    public E push(E item) {
        add(item);
        return item;
    }

    public E pop() {
        // 마지막 요소 삭제, 스택이 비어있으면 EmptyStackException
        return remove(size() - 1);
    }

    public E peek() {
        int len = size();

        if (len == 0) {
            throw new EmptyStackException();
        }
        // 마지막 요소 반환
        return get(len - 1);
    }

    public boolean empty() {
        return size() == 0;
    }

    public int search(E value) {
        // 끝에서부터 객체를 찾는다.
        int i = lastIndexOf(value);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }

}
