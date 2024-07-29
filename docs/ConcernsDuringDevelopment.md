# 개발도중에 했던 고민과 공부한것들 정리


### 1. 사용자에게 입력받은값과 컴퓨터가 생성한값을 문자열과 정수중에 무엇으로 관리해야 할까?

- 정수
    - 숫자 비교의 용이성: 정수로 저장하면 두 숫자 또는 숫자 리스트를 비교할 때, 연산이 간단하고 직접적이다

- 문자열
    - 필요한 경우 문자열 조적을 통해 처리할 수 있다. 문자열에서 특정 문자를 추출하거나 수정할 수 있다.
    - 문자열로 처리하면 입력 값이 숫자가 아닌 경우도 유연하게 처리할 수 있다. 잘못된 값이 입력 되었을때 오류
  메세지를 출력하거나 다시 입력 요청하기 용이하다.

### 2. 두번째 테스트 코드를 통과하지 못하던 현상

#### Junit이란?
  - 유닛 테스트를 작성하고 실행하기 위한 프레임워크
  - 주요 기능
    1. 테스트 클래스와 테스트 메서드
      - 테스트 클래스는 일반적으로 특정 클래스 또는 모듈을 테스트하는 데 사용됩니다.
      - 각 테스트 메서드는 개별 기능이나 동작을 검증합니다.
      - 테스트 메서드는 @Test 어노테이션으로 표시됩니다.
    2. 어노테이션
     - @Test: 메서드를 테스트 메서드로 지정합니다.
     - @BeforeEach와 @AfterEach: 각 테스트 메서드가 실행되기 전후에 실행되는 메서드를 지정합니다.
     - @BeforeAll과 @AfterAll: 모든 테스트 메서드가 실행되기 전후에 한 번씩 실행되는 메서드를 지정합니다.
     - @Disabled: 특정 테스트 메서드를 비활성화합니다.
    3. Assertions
      - 다양한 assertion 메서드를 제공하여 테스트 결과를 검증합니다.
      - 예: assertEquals(expected, actual), assertTrue(condition), assertThrows(expectedType, executable) 등.
    4. 테스트 실행
      - IDE에서 직접 실행하거나, 빌드 도구(예: Maven, Gradle)를 통해 실행할 수 있다.
  - 장점
    1. 테스트를 자동화하여 반복적이고 일관된 테스트를 보장할 수 있다.
    2. 자동화된 테스트는 버그를 조기에 발견하고 수정하는 데 도움이 되어 전체 개발 속도를 향상시킨다.
    3. 테스트 코드는 클래스나 메서드의 기대 동작을 문서화하는 데 도움이 된다.
    
#### 사용자에게 받은 입력값 유효성 검사 코드
  
  - 두번째 테스트코드
    ```
    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1234"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
    ```
    - @Test: 이 어노테이션은 메서드가 JUnit 테스트 메서드임을 나타낸다.
    - assertSimpleTest: 테스트를 단순하게 실행(람다 표현식으로 전달받은 코드를)시키기 위한 메서드
    - assertThatThrownBy: 특정 코드 블럭 예외를 던지는지 확인하는 메서드
    - runException 코드
    ```
    protected final void runException(final String... args) {
        try {
            run(args);
        } catch (final NoSuchElementException ignore) {
        }
    }
    ```
    - isInstanceOf(IllegalArgumentException.class): 던져진 예외가 IllegalArgumentException인지 확인한다.
      던져진 예외가 IllegalArgumentException가 맞다면 테스트 통과.
    
  - 수정전
    ```
      try {
      validateInput(readInput);
      } catch (IllegalArgumentException e) {
      System.out.println("잘못된 입력: " + e.getMessage());
      return; // 예외를 catch 한 후 다시 던지도록 구현
      }
    ```
      
  - 수정후
    ```
    try {
    validateInput(readInput);
    } catch (IllegalArgumentException e) {
    System.out.println("잘못된 입력: " + e.getMessage());
    throw e; // 예외를 catch 한 후 다시 던지도록 구현
    }
    ```
#### 수정전 에러 이유
  - IllegalArgumentException이 발생하지 않는 이유는 runException 메서드가 NoSuchElementException을 catch 하고 있어서
    예외가 발생해도 무시되고 있기 때문이였음.
    validateInput 메서드에서 IllegalArgumentException을 던질 때, 해당 예외를 catch한 후 다시 던지도록 수정하여
    테스트 코드에서 예외가 정상적으로 포착되게 함.