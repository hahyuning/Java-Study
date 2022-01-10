## 1. 삽입
- N의 j부터 i까지 비트를 0으로 만들고,
- M을 시프트하여 j부터 i번 비트 자리에 오도록 만들고,
- M과 N을 합인다.
```java
class Solution {
    int updateBits(int n, int m, int i, int j) {
        int allOnes = ~0;
        
        // j 앞에는 1을 두고 나머지는 0으로 설정
        int left = allOnes << (j + 1);
        // i 뒤에는 1을 두고 나머지는 0으로 설정
        int right = ((1 << i) - 1);
        
        // i와 j 사이의 비트들을 제외한 나머지는 1
        int mask = left | right;
        
        int n_cleared = n & mask;
        int m_shifted = m << i;
        return n_cleared | m_shifted;
    }
}
```

<br>

## 2. 0과 1 사이의 실수를 2진수로
```java
class Solution {
    String printBinary(double num) {
        if (num >= 1 || num <= 0) {
            return "ERROR";
        }

        StringBuilder binary = new StringBuilder();
        binary.append(".");
        while (num > 0) {
            // 길이 제한 설정
            if (binary.length() >= 32) {
                return "ERROR";
            }
            
            double r = num * 2;
            if (r >= 1) {
                binary.append(1);
                num = r - 1;
            }
            else {
                binary.append(0);
                num = r;
            }
        }
        return binary.toString();
    }
}
```
<br>

## 3. 비트 뒤집기
- 정수의 비트값을 읽어 나가면서 현재 1수열의 길이와 바로 이전 1수열의 길이 저장
- 0비트를 만나면 prevLen 갱신
  - 다음 비트가 1이면 prevLen은 currLen
  - 다음 비트가 0이면 두 수열을 합칠 수 없으므로 prevLen을 0으로 세팅
```java
class Solution {
    int filpBit(int a) {
        if (~a == 0) return Integer.BYTES * 8;
        
        int currLen = 0;
        int prebLen = 0;
        int maxLen = 0;
        
        while (a != 0) {
            // 현재 비트가 1인 경우
            if ((a & 1) == 1) {
                currLen++;
            }
            // 현재 비트가 0인 경우
            else if ((a & 1) == 0){
                prebLen = (a & 2) == 0 ? 0 : currLen;
                currLen = 0;
            }
            
            maxLen = Math.max(prebLen + currLen + 1, maxLen);
            a >>>= 1;
        }
        return maxLen;
    }
}
```

<br>

## 4. 다음 숫자

<br>

## 5. (n & (n - 1)) == 0
#### A & B == 0
- A와 B에서 1비트의 위치가 같은 곳이 없다는 뜻으로, n과 n-1에는 공통적으로 1인 비트가 없다.

#### n과 n - 1
- n의 맨 오른쪽 0 비트들이 n-1에서 1로 바뀌고, n의 맨 오른쪽 1비트는 n-1에서 0으로 바뀐다.
- if n = abcd1000, then n-1 = abcd0111

#### n & (n - 1) == 0
- n과 n-1은 같은 위치에 1인 비트가 있으면 안되므로, 위의 예제에서 abcd는 전부 0이어야 한다.
- n은 00001000이 되고, n은 2의 거듭제곱꼴이 된다.
- 즉, n이 2의 거듭제곱수인지 혹은 n이 0인지 검사하는 식

<br>

## 6. 변환
- 두 숫자가 주어졌을 때, 다른 비트를 찾아내려면 XOR 사용
- A와 B를 XOR 했을 때 1인 비트는 해당 비트의 값이 다르다는 뜻으로, A와 B 사이에 서로 다른 비트의 개수를 구하기 위해서는 A^B를 한 후 1인 비트의 개수를 세면 된다.
```java
class Solution {
    int bitSwapPequired(int a, int b) {
        int cnt = 0;
        
        // 최하위 비트를 뒤집어 나가면서 c가 0이 되는 데 얼마나 걸리는지 확인
        for (int c = a ^ b; c != 0; c = c & (c - 1)) {
            cnt++;
        }
        return cnt;
    }
}
```

<br>

## 7. 쌍끼리 맞바꾸기
- 홀수 번째 비트를 먼저 살펴본 뒤 짝수 번째 비트를 살펴본다.
- 부호비트가 0이 될 수도 있기 때문에 산술 시프트 대신 논리 시프트 사용
```java
class Solution {
    int swqpIddEvenBits(int x) {
        return (((x & 0xaaaaaaaa) >>> 1) | ((x & 0x55555555) << 1));
    }
}
```

<br>

## 8. 선 그리기