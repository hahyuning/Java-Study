## 1. 중복 확인
- 고려사항: ASCII 문자열인지, 유니코드 문자열인지

#### 문자열의 각 문자가 몇 번 등장하는지 확인
- ASCII 문자열이라고 가정할 경우, 문자열의 길이가 문자 집합의 크기인 128보다 큰 경우에도 중복 존재
- 시간 복잡도와 공간 복잡도 모두 O(1)
```java
class solution {
    boolean isUniqueChars(String str) {
        if (str.length() > 128) return flase;

        boolean[] char_set = new boolean[128];
        for (int i = 0; i < str.length(); i++) {

            int val = str.charAt(i);
            if (char_set[val]) {
                return false;
            }
            char_set[val] = true;
        }
        return true;
    }
}
```

#### 비트 벡터를 사용한 풀이
- 가정: 문자열은 a 부터 z 까지로 구성
```java
class solution {
    boolean isUniqueChars(String str) {
        int checker = 0;

        for (int i = 0; i < str.length(); i++) {

            int val = str.charAt(i) - 'a';
            if (checker & (1 << val) > 0) {
                return false;
            }
            checker |= (1 << val);
        }
        return true;
    }
}
```
**자료구조를 추가로 사용할 수 없는 경우**
- 문자열 내의 각 문자를 다른 모든 문자와 비교 (시간 복잡도는 O(N^2), 공간 복잡도는 O(1))
- 입력 문자열을 수정해도 된다면, O(NlogN) 시간에 문자열을 정렬한 뒤 문자열을 처음부터 훑어 나가면서 인접한 문자가 동일한지 검사

<br>

## 2.순열 확인
- 고려사항: 대소문자 구별 여부, 공백 처리
- 문자열의 길이가 다르면 서로 순열 관계에 있을 수 없다.

#### 정렬
- 두 문자열이 서로 순열 관계라면, 문자열을 따라서 정렬한 결과가 동일해야 한다.
```java
class solution {
    public String sort(String s) {
        char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }

    public boolean permutation(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        return sort(s).equlas(sort(t));
    }
}
```

#### 문자열에 포함된 문자의 출현 횟수 검사
- 배열을 두 개 사용해서 각 문자열 내의 문자 출현 횟수 기록 후 비교
```java
class solution {
    boolean permutaion(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] letters = new int[128];

        char[] s_arr = s.toCharArray();
        for (char c : s_array) {
            letters[c]++;
        }

        for (int i = 0; i < t.length(); i++) {
            int c = (int) t.charAt(i);
            letters[c]--;
            if (letters[c] < 0) {
                return false;
            }
        }
        return true;
    }
}
```
<br>

## 3. URL화
- 문자열 내에 얼마나 많은 공백 문자가 있는지 확인 후, 최종 문자열에 추가 공간이 얼마나 필요한지 계산
- 역방향으로 진행하면서 실제로 문자열을 편집 (공백을 만나면, 다음 위치에 %20을 복사)
```java
class solution {
    void replaceSpaces(char[] str, int trueLength) {
        int spaceCount = 0;
        for (int i = 0; i < trueLength; i++){
            if (str[i] == ' ') {
                spaceCount++;
            }
        }

        int index = trueLength + spaceCount * 2;
        if (trueLength < str.length) str[trueLength] = '\0';

        for (int i = trueLength - 1; i >= 0; i--) {
            if (str[i] == ' ') {
                str[index - 1] = '0';
                str[index - 1] = '2';
                str[index - 3] = '%';
                index = index - 3;
            } else {
                str[index--] = str[i];
            }
        }
    }
}
```
- String 수정이 불가능하기 때문에 문자배열 사용
- String을 직접 사용할 경우 String을 새로 복사하여 사용하면, 한 번만 훑어서 결과를 반환할 수 있다.

<br>

## 4. 회문 순열
- 문자열의 길이가 짝수일 때는 모든 문자의 개수가 반드시 짝수여야 한다.
- 문자열의 길이가 홀수일 때는 문자 하나는 홀수 개 존재해도 괜찮다.

#### 해시 테이블을 사용해서 문자가 몇 번 등장했는지 확인
- 해시테이블을 훑어가면서 홀수 문자가 한 개 이상인지 확인
- 시간 복잡도 O(N)
```java
class solution {
    boolean isPermutationOfPalindrome(String phrase) {
        int[] table = buildCharFrequencyTable(phrase);
        return checkMaxOneOdd(table);
    }
    
    // 홀수 문자가 한 개 이상 존재하는지 확인
    boolean checkMaxOneOdd(int[] table) {
        boolean foundOdd = false;
        
        for (int count : table) {
            if (count % 2 == 1) {
                if (foundOdd) {
                    return false;
                }
                foundOdd = true;
            }
        }
        return true;
    }
    
    // 각 문자에 숫자를 대응
    // 문자가 아닌 경우에는 -1
    int getCharNumber(Character c) {
        int a = Character.getNumericValue('a');
        int z = Character.getNumericValue('z');
        int val = Character.getNumericValue(c);
        
        if (a <= val && val <= z) {
            return val - a;
        }
        return -1;
    }
    
    // 각 문자가 몇 번 등장했는지 세기
    int[] buildCharFrequencyTable(String phrase) {
        int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
        
        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);
            if (x != -1) {
                table[x]++;
            }
        }
        return table;
    }
}
```

#### 약간의 시간 개선
- 문자열을 훑어 나가면서 동시에 홀수의 개수를 확인하면, 순회가 끝나자마자 회문인지 알 수 있다.
```java
class solution {
    boolean isPermutationOfPalindrome(String phrase) {
        int countOdd = 0;
        int[] table = new int[Character.getNumericValue('z') - Character.getNumericValue('a') + 1];
        
        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);
            if (x != -1) {
                table[x]++;
                
                if (table[x] % 2 == 1) {
                    countOdd++;
                } else {
                    countOdd--;
                }
            }
        }
        return countOdd <= 1;
    }
}
```

#### 비트 벡터 사용
- 알파벳을 0부터 25까지의 숫자로 치환한 후 해당 문자가 등장할 때마다 치환된 위치의 비트값을 바꿔준다.
- 마지막으로 한 개 이하의 비트가 1로 세팅되어 있는지 확인
- 시간 복잡도는 O(N)

> 어떤 숫자에 1을 뺀 뒤 AND 연산을 수행했을 때 그 결과가 0이라면 해당 숫자는 정확히 한 비트만 1로 세팅되어 있다.

```java
class solution {
    boolean isPermutationOfPalindrome(String phrase) {
        int bitVector = createBitVector(phrase);
        return bitVector == 0 || checkExactlyOneBitSet(bitVector);
    }
    
    // 문자열에 대한 비트 벡터 만들기
    int createBitVector(String phrase) {
        int bitVector = 0;

        for (char c : phrase.toCharArray()) {
            int x = getCharNumber(c);
            bitVector = toggle(bitVector, x);
        }
        return bitVector;
    }
    
    // 정수의 i번째 비트값 변경
    int toggle(int bitVector, int index) {
        if (index < 0) return bitVector;
        
        int mask = 1 << index;
        if ((bitVector && mask) == 0) {
            bitVector |= mask;
        }
        else {
            bitVector &= ~mask;
        }
        return bitVector;
    }
    
    boolean checkExactlyOneBitSet(int bitVector) {
        return (bitVector & (bitVector - 1)) == 0;
    }
}
```
<br>

## 5. 하나 빼기
- 교체: 두 문자열에서 단 하나의 문자만 달라야 한다.
- 삽입: 어떤 문자열에서 특정한 위치를 빈 공간으로 남겨 두면 그 부분을 제외한 나머지 부분이 동일하다.
- 삭제: 삽입의 반대

#### 삽입과 삭제를 하나로 합치고, 교체 연산 별도로 비교
- 시간 복잡도는 O(min(N, M))
```java
class solution {
    boolean oneEditAway(String first, String second) {
        if (first.length() == second.length()) {
            return oneEditReplace(first, second);
        }
        else if (first.length() + 1 == second.length()) {
            return oneEditInsert(first, second);
        }
        else if (first.length() - 1 == second.length()) {
            return oneEditInsert(second, first);
        }
    }
    
    boolean oneEditReplace(String s1, String s2) {
        boolean foundDiff = false;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (foundDiff) {
                    return false;
                }
                foundDiff = true;
            }
        }
        return true;
    }
    
    boolean oneEditInsert(String s1, String s2) {
        int index1 = 0;
        int index2 = 0;
        
        while (index2 < s2.length() && index1 < s1.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {
                if (index1 != index2) {
                    return false;
                }
                index2++;
            }
            else {
                index1++;
                index2++;
            }
        }
        return true;
    }
}
```

#### 메서드 하나로 합치기
```java
class solution {
    boolean oneEditAway(String first, String second) {
        if (Math.abs(first.length() - second.length()) > 1) {
            return false;
        }
        
        String s1 = first.length() < second.length() ? first : second;
        String s2 = first.length() < second.length() ? second : first;
        
        int index1 = 0;
        int index2 = 0;
        boolean foundDiff = false;

        while (index2 < s2.length() && index1 < s1.length()) {
            if (s1.charAt(index1) != s2.charAt(index2)) {
                // 반드시 첫 번째로 다른 문자여야 한다.
                if (foundDiff) return false;
                foundDiff = true;
                
                // 교체의 경우 짧은 문자열의 포인터 증가
                if (s1.length() == s2.length()) {
                    index1++;
                }
            }
            else {
                // 동일하다면 짧은 문자열의 포인터 증가
                index1++;
            }
            // 긴 문자열의 포인터는 언제나 증가;
            index2++;
        }
        return true;
    }
    
}
```
<br>

## 6. 문자열 압축 [카카오 2020](https://programmers.co.kr/learn/courses/30/lessons/60057)

#### StringBuilder 사용
- 압축된 문자열을 만든 뒤 압축된 문자열과 입력 문자열 중 길이가 작은 문자열 반환
- StringBuilder의 크기를 미리 설정할 수 없어서 최종 크기가 실제 필요로 하는 크기보다 두 배 더 클수도 있다.
```java
class solution {
    String compress(String str) {
        StringBuilder compressed = new StringBuilder();
        int countConsecutive = 0;
        for (int i = 0; i < str.length(); i++) {
            countConsecutive++;
            
            if (i + 1 >= str.length() || (str.charAt(i) != str.charAt(i + 1))) {
                compressed.append(str.charAt(i));
                compressed.append(countConsecutive);
                countConsecutive = 0;
            }
        }
        return compressed.length() < str.length() ? compressed.toString() : str;
    }
}
```
<br>

## 7. 행렬 회전
- 레이어별로 회전하도록 구현
- 인덱스 별로 교체 작업 수행
- 시간 복잡도는 O(N^2)으로 최선의 방법
```java
class solution {
    boolean rotate(int[][] matrix) {
        if (matrix.length == 0 || matrix.length != matrix[0].length) return false;
        
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {
            
            int first = layer;
            int last = n - 1 - layer;

            for (int i = first; i < last; i++) {
                int offset = i - first;
                int top = matrix[first][i];
                
                // 왼쪽 -> 위쪽
                matrix[first][i] = matrix[last - offset][first];
                
                // 아래쪽 -> 왼쪽
                matrix[last - offset][first] = matrix[last][last - offset];
                
                // 오른쪽 -> 아래쪽
                matrix[last][last - offset] = matrix[i][last];
                
                // 위쪽 -> 오른쪽
                matrix[i][last] = top;
            }
        }
        return true;
    }
}
```
<br>

## 8. 0 행렬

<br>

## 9. 문자열 회전
- s1을 x와 y로 나눈 뒤 xy = s1이 되고 yx = s2가 되는 방법이 있는지 확인
- x와 y로 쪼개는 지점에 관계없이 yx는 언제나 xyxy의 부분 문자열이므로, s2는 언제나 s1s1의 부분 문자열이 된다.
```java
class solution {
    
    boolean isRotation(String s1, String s2) {
        int len = s1.length();
        if (len == s2.length() && len > 0) {
            String s1s1 = s1 + s1;
            return isSubstring(s1s1, s2);
        }
        return false;
    }
}
```
