# ViewTest
View event catch test in Android


# Quiz
1. Activity에 2000개의 View를 가진 App을 생성하고 View와 Listener 출력
- 조건
  - Button, TextView 등 랜덤하게 View 생성
  - View의 크기와 위치는 랜덤하게 생성 (서로 겹쳐도 무관)
  - 각 View에 중복되지 않는 텍스트를 지정
  - 전체 View 중 30%(600개)만 Click Listener 설정

2. Click Listener가 설정되어 있는 View들을 전부 출력
3. Touch event 발생시 해당 좌표에 위치한 View를 출력

 > !!! 2,3번 문제는 1번에서 생성한 App을 외부에서 접근한다고 가정함 (직접 접근 불가)


# Solution
1. App 생성
 - 0~1 범위의 난수 생성하여 0이면 TextView, 1이면 Button -> 2000번의 반복문을 통해 생성
 - Device의 해상도를 고려해 View의 크기와 위치 생성 제한
 - 텍스트 지정 -> "{view name} + {index}" View 
 - 랜덤한 Click listener 설정
   - 0~1999 범위의 난수를 생성
   - 생성된 난수에 위치한 View의 listener가 null일 경우 listener 설정
   - listener 설정을 600번 반복
   
2. 
