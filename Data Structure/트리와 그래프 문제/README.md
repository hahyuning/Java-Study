## 1. 노드 사이의 경로
- 두 노드 가운데 하나를 고른 뒤 탐색 도중 다른 노드가 발견되는지 검사
- 사이클에 대비하고, 중복되는 일을 피하기 위해서 이미 방문한 노드는 방문 처리를 해두어야 한다.
#### BFS
```java
import java.util.LinkedList;

class Solution {
    enum State {Unvisited, Visited, Visiting;}

    boolean search(Graph graph, Node start, Node end) {
        if (start == end) {
            return true;
        }

        LinkedList q = new LinkedList();
        
        for (Node u : graph.getNodes()) {
            u.state = State.Unvisited;
        }
        start.state = State.Visiting;
        q.add(start);
        
        Node u;
        while (!q.isEmpty()) {
            u = q.removeFirst();
            if (u != null) {
                for (Node v : u.getAdjacent()) {
                    if (v.state == State.Unvisited) {
                        if (v == end) {
                            return true;
                        }
                        else {
                            v.state = State.Visiting;
                            q.add(v);
                        }
                    }
                }
                u.state = State.Visited;
            }
        }
        return false;
    }
}
```
<br>

## 2. 최소 트리
- 최소 높이 트리를 생성하기 위해서는, 왼쪽 하위 트리의 노드의 개수와 오른쪽 하위 트리의 노드 개수를 가능하면 같게 맞춰야 한다.
- 로트 노드가 배열의 중앙에 오도록 해야 한다는 뜻으로, 트리 원소 가운데 절반은 루트보다 작고 나머지 절반은 루트보다 커야한다.
- 배열의 각 구획의 중간 원소를 루트로 하고, 루트의 왼쪽 절반은 왼쪽 하위 트리, 오른쪽 절반은 오른쪽 하위트리로 만든다.
    1. 배열 가운데 원소를 트리에 삽입한다.
    2. 왼쪽 하위 트리에 왼쪽 절반 배열 원소들을 삽입한다.
    3. 오른쪽 하위 트리에 오른족 절반 배열 원소들을 삽입한다.
    4. 재귀 호출을 실행한다.

```java
class Solution {
    TreeNode createMinBST(int[] arr) {
        return createMinBST(arr, 0, arr.length - 1);
    }
    
    TreeNode createMinBST(int[] arr, int start, int end) {
        if (end < start) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode n = new TreeNode(arr[mid]);
        n.left = createMinBST(arr, start, mid - 1);
        n.right = createMinBST(arr, mid + 1, end);
        return n;
    }
}
```
<br>

## 3. 깊이의 리스트
- 이진 트리가 주어졌을 때 같은 깊이에 있는 노드를 연결리스트로 연결
#### DFS

```java
import java.util.ArrayList;
import java.util.LinkedList;

class Solution {
    void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
        if (root == null) return;

        LinkedList list = null;
        if (lists.size() == level) {
            list = new LinkedList();
            lists.add(list);
        } else {
            list = lists.get(level);
        }

        list.add(root);
        createLevelLinkedList(root.left, lists, level + 1);
        createLevelLinkedList(root.right, lists, level + 1);
    }

    ArrayList<LinkedList<TreeNode>> createLinkedList(TreeNode root) {
        ArrayList<LinkedList<TreeNode>> lists = new ArrayList<>();
        createLevelLinkedList(root, lists, 0);
        return lists;
    }
}
```

- BFS와 DFS 둘 다 O(N) 시간이 소요된다.
- DFS의 경우 균형잡힌 트리의 경우 O(logN) 만큼의 재귀 호출을 필요로 하는데, 이는 새로운 깊이를 탐색할 때마다 스택을 사용한다는 뜻
- DFS는 순환적으로 구현되어 있어서 추가 공간을 요구하지 않지만, 둘 다 O(N) 만큼의 데이터를 반환해야 한다.
- 재귀적으로 구현할 때 요구되는 O(logN) 만큼의 추가적인 공간은 O(N) 크기의 데이터와 비교하면 얼마 되지 않는다.
- 따라서, DFS가 더 많은 공ㄱ나을 사용하지만 O 표기법에 비추어보면 두 해법은 공간 효율성 측면에서 동일

<br>

## 4. 균형 확인
- 트리의 루트부터 재귀적으로 하위 트리를 훑어 나가면서 그 높이를 검사한다.
- 각 노드에서 checkHeight를 사용해서 왼쪽 하위 트리와 오른족 하위 트리의 높이를 재귀적으로 구한다.
- 하위 트리가 균형 잡힌 상태일 경우 checkHeight 메서드는 해당 하위 트리의 실제 높이를 반환하고, 그렇지 않은 경우 에러를 반환
- 일반적으로 널 트리의 높이를 -1로 정의하기 때문에 에러 값으로 -1 대신 Integer.MIN_VAL 사용
- 시간 복잡도는 O(N), 공간 복잡도는 O(H)
```java
class Solution {
    int checkHeight(TreeNode root) {
        if (root == null) return -1;
        
        int leftHeight = checkHeight(root.left);
        if (leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
        
        int rightHeight = checkHeight(root.right);
        if (rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
        
        int heightDiff = Math.abs(leftHeight - rightHeight);
        if (heightDiff > 1) {
            return Integer.MIN_VALUE;
        }
        else {
            return Math.max(leftHeight, rightHeight);
        }
    }
    
    boolean isBalanced(TreeNode root) {
        return checkHeight(root) != Integer.MIN_VALUE;
    }
}
```
<br>

## 5. BST 검증
#### 중위 순회
- 중위 순회를 하면서 배열에 원소들을 복사해 넣은 뒤 이 결과가 정렬된 상태인지 확인
- 별도의 메모리가 필요하지만 대부분의 경우에 잘 작동하는 방법
- 문제점은 트리 안에 중복된 값이 있는 경우를 처리할 수 없다는 것
- 배열에서 어떤 원소와 바로 전 단계의 원소만 비교하기 때문 실제로는 배열 없이도 문제 풀이가 가능하다.
- 마지막으로 검사했던 원소만 기록해 둔 다음 그 다음 원소와 비교한다.
```java
class Solution {
    static Integer lastPrinted = null;
    
    boolean checkBST(TreeNode n) {
        if (n == null) return true;
        
        // 왼쪽을 재귀적으로 검사
        if (!checkBST(n.left)) return false;
        
        // 현재 노드 검사
        if (lastPrinted != null && n.data <= lastPrinted) {
            return false;
        }
        lastPrinted = n.data;
        
        // 오른쪽을 재귀적으로 검사
        if (!checkBST(n.right)) return false;
        
        return true;
    }
}
```

#### 최소/최대 기법
- 최소값과 최대값을 아래로 전달해 나가면서 문제를 풀어나간다.
- 왼쪽으로 분기하면 max를 갱신하고, 오른쪽으로 분기하면 min을 갱신한다.
- 언제든 범위에 어긋나는 데이터를 발견하면 트리 순회를 중단하고 false 반환
- 시간 복잡도는 O(N), 재귀를 사용하기 때문에 공간 복잡도는 균형잡힌 트리에서 O(logN)
```java
class Solution() {
    boolean checkBST(TreeNode n) {
        return checkBST(n, null, null);
    }
    
    boolean checkBST(TreeNode n, Integer min, Integer max) {
        if (n == null) return true;
        
        if ((min != null && n.data <= min) || (max != null && n.data > max)) {
            return false;
        }
        
        if (!checkBST(n.left, min, n.data) || !checkBST(n.right, n.data, max)) {
            return false;
        }
        
        return true;
    }
}
```
<br>
