# 비트 조작
#### 0110 + 0110 = 1100
0110 * 2 와 같으므로, 0110을 왼쪽으로 한 번 시프트한 것과 같다.
#### 0100 * 0011 = 1100
0100은 4 와 같고 4를 곱하는 것은 왼쪽으로 두 번 시프트하는 것과 같으므로, 0011을 왼쪽으로 두 번 시프트한 것과 같다.
#### 1101 ^ (~1101) = 1111
어떤 비트와 그 비트를 부정한 값을 XOR 하면 항상 1이 되므로, a ^ (~a) 를 하면 모든 비트가 1이 된다.
#### 1011 & (~0 << 2) = 1000
- ~0 을 하면 모든 비트가 1이 되므로, ~0 << 2 를 하면 마지막 비트 두 개는 0이 되고 나머지는 모두 1이 된다.
- 이 값과 다른 값을 AND 연산하면 마지막 두 비트의 값을 삭제한 값을 얻는다.

<br>

## 기본 연산

|연산||||
|---|---|---|---|
|XOR|x ^ 0s = x|x ^ 1s = ~x|x ^ x = 0|
|AND|x & 0s = 0|x & 1s = x|x & x = x|
|OR|x &#124; 0s = x|x &#124; 1s = 1s|x &#124; x = x|

<br>

## 2의 보수와 음수
- 컴퓨터는 일반적으로 정수를 저장할 때 2의 보수 형태로 저장한다.
- 음수를 표현할 때는 그 수의 절댓값에 부호비트를 1로 세팅한 뒤 2의 보수를 취한 형태로 표현
- -K를 N비트의 2진수로 표현하면 `concat(1, 2 ^ (N - 1) - K)` 가 된다.
- 2의 보수를 표현하는 다른 방법은 양수로 표현된 2진수를 뒤집은 뒤 1을 더해주는 것

<br>

## 기본적인 비트 조작 문제
#### 비트값 확인
결과값이 0이 아니라면 i번째 비트는 1이고, 0이라면 i번째 비트는 0
```java
class bitmask {
    boolean getBit(int num, int i) {
        return ((num & (1 << i)) != 0);
    }
}
```

#### 비트값 채워넣기
```java
class bitmask {
    boolean setBit(int num, int i) {
        return num | (1 << i);
    }
}
```

#### 비트값 삭제하기
```java
class bitmask {
    boolean clearBit(int num, int i) {
        int mask = ~(1 << i);
        return num & mask;
    }
}
```

#### 최상위 비트에서 i번째 비트까지 모두 삭제
```java
class bitmask {
    boolean clearBits(int num, int i) {
        int mask = (1 << i) - 1;
        return num & mask;
    }
}
```

#### i번재 비트에서 0번째 비트까지 모두 삭제
```java
class bitmask {
    boolean clearBits(int num, int i) {
        int mask = (-1 << (i + 1));
        return num & mask;
    }
}
```

#### 비트값 바꾸기
```java
class bitmask {
    boolean updateBit(int num, int i, boolean bitIs1) {
        int value = bitIs1 ? 1: 0;
        int mask = ~(1 << i);
        return (num & mask) | (value << i);
    }
}
```