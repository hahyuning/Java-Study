## 1. 배열 한 개로 스택 세 개
#### 고정 크기 할당
- 스택1: [0, n/3)
- 스택2: [n/3, 2n/3)
- 스택3: [2n/3, n)
```java
class FixedMultiStack {
    private int numberOfStack = 3;
    private int stackCapacity;
    private int[] values;
    private int[] sizes;
    
    public FixedMultiStack(int stackSize) {
        stackCapacity = stackSize;
        values = new int[stackSize * numberOfStack];
        sizes = new int[numberOfStack];
    }
    
    public void push(int stackNum, int value) throws FullStackException {
        if (isFull(stackNum)) {
            throw new FullStackException();
        }
        
        sizes[stackNum]++;
        values[indexOfTop(stackNum)] = value;
    }
    
    public int pop(int stackNum) {
        if (isEmpty(stackNum)) {
            throw new FullStackException();
        }
        
        int topIndex = indexOfTop(stackNum);
        int value = values[topIndex];
        value[topIndex] = 0;
        sizes[stackNum]--;
        return value;
    }
    
    public int peek(int stackNum) {
        if (isEmpty(stackNum)) {
            throw new FullStackException();
        }
        return values[indexOfTop(stackNum)];
    }
    
    public boolean inEmpty(int stackNum) {
        return sizes[stackNum] == 0;
    }
    
    public boolean isFull(int stackNum) {
        return sizes[stackNum] == stackCapacity;
    }
    
    private int indexOfTop(int stackNum) {
        int offset = stackNum * stackCapacity;
        int size = sizes[stackNum];
        return offset + size - 1;
    }
}
```

#### 유연한 공간 분할
- 각 스택에 할당되는 공간 크기가 변하는 방법
- 한 스택이 최초에 설정한 용량 이상으로 커지면, 가능한 만큼 용량을 늘려주고 필요에 따라 원소 이동
- 배열은 circular로 설계하여, 마지막 스택이 배열 맨 끝에서 시작해서 처음으로 연결될 수 있도록 한다.

<br>

## 2. 스택 최솟값
- 스택의 각 상태마다 최솟값을 함께 기록
- 원소를 스택에 쌓을 때, 현재값과 자신보다 아래 위치한 원소들 중 최솟값 중에서 더 작은 최솟값으로 기록
```java
import java.util.Stack;

public class StackWithMin extends Stack<E> {
    
    public void push(int value) {
        int newMin = Math.min(value, min());
        super.push(new NodeWithMin(value, newMin));
    }

    public int min() {
        if (this.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        else {
            return peek().min;
        }
    }
}

class NodeWithMin {
public int value;
public int min;

    public NodeWithMin(int value, int min) {
        this.value = value;
        this.min = min;
    }
}
```

- 이 방법의 문제점은, 스택이 커지면 각 원소마다 min을 기록하느라 공간이 낭비된다.
- 이를 해결하기 위해 min 값들을 기록하는 스택을 하나 더 둔다.
```java
import java.util.Stack;

public class StackWithMin extends Stack<E> {
    
    Stack<E> s;
    public StackWithMin() {
        s = new Stack<E>();
    }
    
    public void push(int value) {
        if (value <= min()) {
            s.push(value);
        }
        super.push(value);
    }
    
    public Integer pop() {
        int value = super.pop();
        if (value == min()) {
            s.pop();
        }
        return value;
    }
    
    public int min() {
        if (this.isEmpty()) {
            return Integer.MAX_VALUE;
        }
        else {
            return peek().min;
        }
    }
}

class NodeWithMin {
    public int value;
    public int min;
    
    public NodeWithMin(int value, int min) {
        this.value = value;
        this.min = min;
    }
}
```

## 3. SetOfStacks
- 여러 개의 스택으로 구성되어 있고, 이전 스택이 지정된 용량을 초과하는 경우 새로운 스택을 만드는 SetOfStacks 구현

```java
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

class SetOfStacks {

    ArrayList<E> stacks = new ArrayList<Object>();

    public void push(E v) {
        Stack<E> last = getLastStack();
        if (last != null && !last.isFull) {
            last.push(v);
        } else {
            Stack<E> stack = new Stack<>();
            stack.push(v);
            stacks.add(stack);
        }
    }

    public int pop() {
        Stack<E> last = getLastStack();
        if (last == null) throw new EmptyStackException();
        
        E value = last.pop();
        if (last.size == 0) {
            stacks.remove(stacks.size() - 1);
        }
        return value;
    }
}
```

#### 특정한 하위 스택에 대해서 pop

<br>

## 4. 스택 두 개로 큐 한 개
- stackNew: 새 원소가 맨 위에 있는 스택
- stackOld: 마지막 원소가 맨 위에 있는 스택
- 큐에서 제거할 때는 오래된 원소부터 제거해야 하므로, stackOld에서 원소를 제거한다.
- stackOld가 비어있을 경우에는 stackNew의 모든 원소의 순서를 뒤집어서 stackOld에 넣는다.
- 원소를 새로 삽입할 때는 stackNew에 삽입하여, 새로운 원소가 스택 최상단에 유지되도록 한다.

```java
import java.util.Stack;

class MyQueue {

    Stack<E> stackNew, stackOld;
    
    public MyQueue() {
        stackNew = new Stack<E>();
        stackOld = new Stack<E>();
    }
    
    public int size() {
        return stackNew.size() + stackOld.size();
    }
    
    public void add(E value) {
        stackNew.push(value);
    }
    
    public void shiftStacks() {
        if (stackOld.isEmpty()) {
            while (!stackNew.isEmpty()) {
                stackOld.push(stackNew.pop());
            }
        }
    }
    
    public E peek() {
        shiftStacks();
        return stackOld.peek();
    }
    
    public E remove() {
        shiftStacks();
        return stackOld.pop();
    }
}
```
<br>

## 5. 스택 정렬
- 스택1에서 꺼낸 값을 스택2에 순서대로 삽입하며 정렬
- 시간 복잡도는 O(N^2), 공간 복잡도는 O(N)

```java
import java.util.Stack;

class Solution {

    void sort(Stack<E> s) {
        Stack<E> r = new Stack<E>();
        
        while (!s.isEmpty()) {
            E tmp = s.pop();
            
            while (!r.isEmpty() && r.peek() > tmp) {
                s.push(r.pop());
            }
            r.push(tmp);
        }
        
        while (!r.isEmpty()) {
            s.push(r.pop());
        }
    }
}
```

- 스택을 무한정으로 사용할 수 있다면, 퀵정렬이나 병합정렬을 사용할 수도 있다.
- 병합 정렬을 사용할 경우
  - 추가로 스택 두 개를 더 만든 다음 원래 스택을 두 개로 쪼래어 저장한다.
  - 그런 다음 그 두 스택을 재귀적으로 정렬하고, 나중에 원래 스택으로 병합한다.
  - 재귀 호출이 일어날 때마다 두 개의 스택이 추가로 만들어진다.
- 퀵 정렬을 사용할 경우
  - 추가 스택을 두 개 만든 다음, 피벗을 기준으로 원래 스택의 원소들을 나누어 저장한다.
  - 그런 다음 두 스택을 각각 재귀적으로 정렬한 다음에 원래 스택에 합친다.
  - 재귀 호출이 일어날 때마다 두 개의 스택이 추가로 만들어진다.

<br>

## 6. 동물 보호소
- 개와 고양이를 별도의 큐로 관리하고, 두 큐를 클래스로 감싼다.
- 각 동물이 언제 들어왔는지를 알려줄 타임스탬프를 기록한다.

```java
import java.util.LinkedList;

abstract class Animal {

    private int order;
    protected String name;

    public Animal(String n) {
        name = n;
    }

    public void setOrder(int ord) {
        order = ord;
    }

    public int getOrder() {
        return order;
    }

    public boolean isOlderThan(Animal a) {
        return this.order < a.getOrder();
    }
}

class AnimalQueue {

    public class Dog extends Animal {
        public Dog(String n) {super(n);}
    }

    public class Cat extends Animal {
        public Cat(String n) {super(n);}
    }

    LinkedList<E> dogs = new LinkedList<>();
    LinkedList<E> cats = new LinkedList<>();
    private int order = 0; // 타임스탬프
    
    public void enqueue(Animal a) {
        a.setOrder(order++);
        
        if (a instanceof Dog) dogs.add((Dog) a);
        if (a instanceof Cat) cats.add((Cat) a);
    }
    
    public Animal dequeAny() {
        if (dogs.size() == 0) {
            return dequeCat();
        }
        else if (cats.size() == 0) {
            return dequeDogs();
        }
        
        Dog dog = dogs.peek();
        Cat cat = cats.peek();
        
        if (dog.isOrderThan(cat)) {
            return dequeDogs();
        }
        else {
            return dequeCat();
        }
    }
}
```