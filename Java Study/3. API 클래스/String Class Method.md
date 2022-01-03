## 기본 연산

## concat(String str)
- 대상 문자열 뒤에 매개변수 str 문자열을 덧붙인 새로운 문자열 반환
```java
"abc".concat("def");
// 결과: "abcdef"
```

## substring(int beginIdx)
- 대상 문자열에서 매개변수 beginIdx 위치에서 시작하는 새로운 부분 문자열 반환
```java
"abcdef".substring(3);
// 결과: "def"
```

## substring(int beginIdx, int endIdx)
- 대상 문자열에서 beginIdx 부터 endIdx - 1 위치까지의 새로운 부분 문자열 반환
```java
"abcdef".substring(3, 5);
// 결과: "de"
```

## replace(Char oldChar, Char newChar)
- 대상 문자열에서 oldChar 문자를 newChar 문자로 바꾼 새로운 문자열 반환
```java
"abcdef".replace('a', 'A');
// 결과: "Abcdef"
```

## replace(CharSequence t, CharSequence r)
- 대상 문자열에서 바꾸고 싶은 문자열 t를 문자열 r로 바꾸어 새로운 문자열 반환
```java
"abcdef".replace("abc", "ABC");
// 결과: "ABCdef"
```

## toLowerCase()/toUpperCase()
- 문자열의 문자들을 모두 소문자/대문자로 바꾸어 새로운 문자열 반환

<br>

# 문자열 

## equals(Object obj)
- 대상 문자열이 매개변수 obj와 같은지 비교해서 boolean 값 반환
```java
"abc".equals("def");
// 결과: false
```

## compareTo(String str)
- 대상 문자열이 사전적으로 앞인지 뒤인지 확인 (같으면 0, 앞이면 음수, 뒤면 양수)
```java
"a".compareTo("b");
// 결과: 음수
```

## startWith(String prefix)
- 대상 문자열이 매개변수 prefix 문자열로 시작하는지 확인해서 boolean 값 반환
```java
"abcdef".startWith("abc");
// 결과: true
```

## endWith(String suffix)
- 대상 문자열이 매개변수 suffix 문자열로 끝나는지 확인해서 boolean 값 반환
```java
"abcdef".endWith("ef");
// 결과: true
```

<br>

# 특정 문자 조회

## indexOf(char chr)/indexOf(String str)
- 주어진 문자/문자열이 문자열에 존재하는지 확인하여 위치 반환 (없으면 -1 반환)
```java
"abcdef".indexOf('c');
// 결과: 2
```

## indexOf(char chr, int pos)
- 주어진 문자 chr가 지정된 위치 pos 부터 문자열에 존재하는지 확인하여 위치 반환 (없으면 -1 반환)
```java
"abcdef".indexOf('c', 4);
// 결과: -1
```

## lastIndexOf(int ch)/lastIndexOf(String str)
- 지정된 문자/문자열을 문자열의 오른쪽 끝에서부터 찾아서 위치 반환 (없으면 -1 반환)
```java
"abcdefa".indexOf('a');
// 결과: 0

"abcdefa".lastIndexOf('a');
// 결과: 6
```

<br>

# 구분자

## split(String regex)
- 문자열을 지정된 regex로 나누어 문자열 배열에 담아 반환
```java
String animals = "hh:mm:ss";
String[] arr = animals.split(":");
// 결과 : arr = {"hh", "mm", "ss"};
```

## join(CharSequence delimiter, CharSequence... elements)
- 여러 문자열 사이에 구분자를 넣어서 결합하는 메서드
```java
List<String> list = Arrays.asList("hi", "my", "name", "is", "hahyun");
String result = String.join("! ", list);
System.out.println(result);
// 결과: hi! my! name! is! hahyun
```

<br>

# 공백 처리

## trim()
- 문자열 앞 뒤에 있는 공백 제거
```java
String trimStr = " abc def   ";
trimStr = trimStr.trim();
System.out.println(trimStr);
// 결과: "abc def"
```

## replaceAll()
- 문자열 중간 사이의 공백 제거
```java
String replaceStr = " abc def  ";
replaceStr = replaceStr.replaceAll(" ", "");
System.out.println(replaceStr);
// 결과: "abcdef"
```
