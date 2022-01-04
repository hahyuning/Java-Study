제약조건: 단방향 연결리스트

## 1. 중복 없애기
- 원소들을 추적하기 위해 해시 테이블 사용
- 연결리스트를 순회하면서 각 원소를 해시테이블에 저장하고, 중복된 원솔르 발견하면 제거
- 시간 복잡도는 O(N)
```java
import java.util.HashSet;
import java.util.LinkedList;

class solution {
    void deleteDups(LinkedList<E> linkedList) {
        HashSet<E> set = new HashSet<>();
        Node<E> prev = null;
        Node<E> now = linkedList.head;

        while (now != null) {
            if (set.contains(now.data)) {
                prev.next = now.next;
            } else {
                set.add(now.data);
                prev = now;
            }
            now = now.next;
        }
    }
}
```

#### 버퍼가 없는 경우
- 두 개의 포인터를 사용해서 문제 해결
- current 포인터는 연결리스트를 순회하고, runner 포인터는 그 뒤에 중복되는 원소가 있는지 확인
- 시간 복잡도는 O(N^2), 공간 복잡도는 O(1)
```java
import java.util.LinkedList;

class solution {
    void deleteDups(LinkedList<E> linkedList) {
        Node<E> current = linkedList.head;
        
        while (current != null) {
            Node<E> runner = current;
            
            while (runner.next != null) {
                if (runner.next.data == current.data) {
                    runner.next = runner.next.next;
                } 
                else {
                    runner = runner.next;
                }
            }
            current = current.next;
        }
    }
}
```
<br>

## 2. 뒤에서 k번째 원소 구하기
#### 연결리스트의 길이를 아는 경우
- 맨 마지막 원소에서 k번재 원소는 (length - k) 번째 원소가 된다.
- 연결리스트를 순회해서 (length - k) 번째 원소를 찾는다.

#### 재귀적 방법
- 연결리스트를 재귀적으로 순회해서 마지막 원소를 만나면 0으로 설정된 카운터 값을 반환한다.
- 재귀적으로 호출된 부모 메서드는 그 값에 1을 더하고, 카운터 값이 k가 되는 순간 해당 원소가 k번째 원소가 된다.
- 일반적으로 노드와 카운터 값을 동시에 반환할 수 없으므로, Wrapper 클래스 구현해서 참조를 통한 값 전달 구현
```java
import java.util.LinkedList;

class solution {

    class Index {
        public int value = 0;
    }

    Node<E> kthToLast(LinkedList<E> linkedList, int k) {
        
        Index index = new Index();
        Node<E> head = linkedList.head;
        return kthToLast(head, k, index);
    }

    Node<E> kthToLast(Node<E> head, int k, Index index) {
        
        if (head == null) {
            return null;
        }
        
        Node<E> node = kthToLast(head.next, k, index);
        index.value = index.value + 1;
        
        if (index == k) {
            return head;
        }
        return node;
    }
}
```

#### 순환적 방법
- 두 개의 포인터 p1, p2 사용
- p2는 연결리스트의 시작 노드를 가리키고, p1은 k 노드만큼 움직여서 p1과 p2가 k 노드만큼 떨어져 있도록 만든다.
- p1과 p2를 함께 이동시키면 p1은 (length - k) 번 후에 연결리스트의 맨 마지막 노드에 도달하고, p2는 (length - k) 번 노드를 가리키게 된다.
- 시간 복잡도는 O(N), 공간 복잡도는 O(1)
```java
import java.util.LinkedList;

class solution {

    Node<E> nthToLast(LinkedList<E> linkedList, int k) {
        Node<E> p1 = linkedList.head;
        Node<E> p2 = linkedList.head;

        for (int i = 0; i < k; i++) {
            if (p1 == null) return null;
            p1 = p1.next;
        }
        
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }
}
```
<br>

## 3. 중간 노드 삭제 
- 제약조건: 삭제할 노드에만 접근 가능
- 다음 노드의 데이터를 현재 노드에 복사한 다음에 다음 노드를 지운다.
- 삭제할 노드가 리스트의 마지막 노드인 경우에는 풀 수 없는 방법
```java
class solution {

    boolean deleteNode(Node<E> node) {

        if (node == null || node.next == null) {
            return false;
        }
        
        Node<E> nextNode = node.next;
        node.data = next.data;
        node.next = next.next;
        return true;
    }
}
```
<br>

## 4. 분할

<br>

## 5. 리스트의 합
- 두 연결리스트의 길이가 다를 경우 null 주의
```java
class solution {
    Node<E> addList(Node<E> l1, Node<E> l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        Node<E> result = new Node();
        int value = carry;
        if (l1 != null) {
            value += l1.data;
        }
        if (l2 != null) {
            value += l2.data;
        }
        
        result.data = value % 10;
        
        // 재귀
        if (l1 != null || l2 != null) {
            Node<E> more = addList(l1 == null ? null : l1.next,
                                   l2 == null ? null : l2.next,
                                   value >= 10 ? 1 : 0);
            result.next = more;
        }
        return result;
    }
}
```

#### 각 자릿수가 정상적으로 배열된 경우
- 한 리스트가 다른 리스트보다 짧은 경우 처리 필요 -> 두 리스트의 길이를 비교해서 짧은 리스트 앞에 0을 매꿔 넣는다.
- 재귀 호출시 결과와 넘김수도 같이 반환해야 한다. -> 부분합이라는 Wrapper 클래스를 만들어 해결
```java
class solution {
    
    class PartialSum {
        public Node<E> sum = null;
        public int carry = 0;
    }
    
    Node<E> addList(Node<E> l1, Node<E> l2) {
        int len1 = length(l1);
        int len2 = length(l2);
        
        // 짧은 리스트에 0을 붙인다.
        if (len1 < len2) {
            l1 = padList(l1, len2 - len1);
        }
        else {
            l2 = padList(l2, len1 - len2);
        }
        
        // 두 리스트를 더한다.
        PartialSum sum = addListHelper(l1, l2);
        
        // 넘김수가 존재한다면 리스트의 앞쪽에 삽입하고, 그렇지 않다면 연결리스트 반환
        if (sum.carry == 0) {
            return sum.sum;
        }
        else {
            Node<E> result = insertBefore(sum.sum, sum.carry);
            return result;
        }
    }
    
    PartialSum addListHelper(Node<E> l1, Node<E> l2) {
        if (l1 == null && l2 == null) {
            PartialSum sum = new PartialSum();
            return sum;
        }
        
        // 작은 자릿수를 재귀적으로 더한다.
        PartialSum sum = addListHelper(l1.next, l2.next);
        // 현재 값에 넘김수를 더한다.
        int val = sum.carry + l1.data + l2.data;
        // 현재 자릿수를 합한 결과를 삽입한다.
        Node<E> fullResult = insertBefore(sum.sum, val % 10);
        
        // 합과 넘김수를 반환한다.
        sum.sum = fullResult;
        sum.carry = val / 10;
        return sum;
    }
    
    Node<E> padList(Node<E> l, int padding) {
        Node<E> head = l;
        for (int i = 0; i < padding; i++) {
            head = insertBefore(head, 0);
        }
        return head;
    }
    
    Node<E> insertBefore(Node<E> l, int data) {
        Node<E> node = new node<>();
        if (l != null) {
            node.next = l;
        }
        return node;
    }
}
```
<br>

## 6. 회문

#### 뒤집어서 비교
- 연결리스트를 뒤집은 다음 원래 리스트와 비교
- 리스트의 절반만 비교하면 된다.
```java
class solution {
    
    boolean isPalindrome(Node<E> head) {
        Node<E> reversed = reverseAndClone(head);
        return isEqual(head, reversed);
    }

    Node<E> reverseAndClone(Node<E> node) {
        Node<E> head = null;
        
        while (node != null) {
            Node<E> now = new Node<>(node.data);
            now.next = head;
            head = now;
            node = node.next;
        }
        return head;
    }
    
    boolean isEqual(Node<E> l1, Node<E> l2) {
        while (l1 != null && l2 != null) {
            if (l1.data != l2.data) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }
    
}
```

#### 순환적 접근법
- 리스트의 앞 절반을 뒤집기 위해 스택을 사용한다.
- 연결리스트의 길이를 알고 있다면, 일반적인 for문 사용 (홀수인 경우 주의)
- 길이를 모른다면, 런너기법을 사용하여 리스트를 순회

```java
import java.util.Stack;

class solution {

    boolean isPalindrome(Node<E> head) {
        Node<E> fast = head;
        Node<E> slow = head;

        Stack stack = new Stack();
        
        while (fast != null && fast.next != null) {
            stack.push(slow.data);
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // 원소의 개수가 홀수라면 가운데 원소는 넘긴다.
        if (fast != null) {
            slow = slow.next;
        }
        
        while (slow != null) {
            int top = stack.pop().intValue();
            
            if (top != slow.data) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }
}
```

#### 재귀적 접근법

<br>

## 7. 교집합
- 각 연결리스트를 순회하면서 길이와 마지막 노드를 구한다.
- 마지막 노드를 비교한 뒤, 그 참조값이 다르다면 교집합이 없다는 의미이므로 false를 반환한다.
- 두 연결리스트의 시작점에 포인터를 놓는다.
- 길이가 더 긴 리스트의 포인터를 두 길이의 차이만큼 앞으로 움직인다.
- 두 포인터가 같아질 때까지 두 리스트를 함께 순회한다.


- 시간 복잡도는 O(N + M)
```java
class solution {

    class Result {
        public Node<E> tail;
        public int size;

        public Result(Node<E> tail, int size) {
            this.tail = tail;
            this.size = size;
        }
    }

    Node<E> findInterSection(Node<E> l1, Node<E> l2) {
        if (l1 == null || l2 == null) {
            return null;
        }

        Result result1 = getTailAndSize(l1);
        Result result2 = getTailAndSize(l2);

        if (result1.tail != result2.tail) {
            return false;
        }

        Node<E> shorter = result1.size < result2.size ? l1 : l2;
        Node<E> longer = result1.size < result2.size ? l2 : l1;

        longer = getKthNode(longer, Math.abs(result1.size - result2.size));

        while (shorter != longer) {
            shorter = shorter.next;
            longer = longer.next;
        }

        return longer;
    }

    Result getTailAndSize(Node<E> l) {
        if (l == null) return null;
        
        int size = 1;
        Node<E> current = l;
        while (current.next != null) {
            size++;
            current = current.next;
        }
        return new Result(current, size);
    }

    Node<E> getKthNode(Node<E> head, int k) {
        Node<E> current = head;
        
        while (k > 0 && current != null) {
            current = current.next;
            k--;
        }
        return current;
    }
}
```
<br>

## 8. 루프 발견 (순환 연결리스트)
