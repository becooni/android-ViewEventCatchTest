# View Event Catch Test
Find and catch View's event test in Android

# Environment
- minSdk - 19
- targetSdk - 27
- Test Device - Galaxy S6 Edge (6.0)

# Quiz
### 1. Activity에 2000개의 View를 가진 App을 생성하고 View와 Listener 출력
- 조건
  - Button, TextView 등 랜덤하게 View 생성
  - View의 크기와 위치는 랜덤하게 생성 (서로 겹쳐도 무관)
  - 각 View에 중복되지 않는 텍스트를 지정
  - 전체 View 중 30%(600개)만 Click Listener 설정

### 2. Click Listener 설정된 View를 찾고, Touch event 발생시 해당 좌표에 위치한 View 찾기

 > 2번 문제는 1번에서 생성한 App의 View를 모른다고 가정함

# Solution
### 1. App 생성
 - 0~1 범위의 난수 생성하여 0이면 TextView, 1이면 Button -> 2000번의 반복문을 통해 생성
 - Device의 해상도를 고려해 View의 크기와 위치 생성 제한
 - 텍스트 지정 -> "{view name} + {index}" View 
 - 랜덤한 Click listener 설정
   - 0~1999 범위의 난수를 생성
   - 생성된 난수에 위치한 View의 listener가 null일 경우 listener 설정
   - listener 설정을 전체 View의 30%(600번) 반복
   
> 출제자에게 문의한 결과, App을 외부에서 타앱으로 접근한다고 가정하는데, 이는 불가능하기 때문에 자사 서비스와 비슷한 구현을 원하는 것으로 판단하여
> Acitivty를 파라미터로 받는 메소드를 구현하여 진행

### 2. 외부에서 접근하기
#### Click Listener 설정된 View 찾기
 - Activity#getWindow()#getDecorView()
 - Activity의 ViewGroup 구조
 ```
 - com.android.internal.policy.PhoneWindow$DecorView
   - android.widget.LinearLayout
     - android.view.ViewStub
     - android.widget.FrameLayout
       - android.support.v7.widget.ActionBarOverlayLayout
       - android.support.v7.widget.ContentFrameLayout
```
> ContentFrameLayout 하위에 Activity#setContentView()로 Set한 View가 존재하는 것을 확인
 - ViewGroup 트리를 탐색하는 재귀함수 호출
 - ContentFrameLayout를 찾으면 Layout View(Container)의 Child View의 Listener를 체크 View#hasOnClickListeners()
 
 #### Touch event 발생 찾기
  - 직접 접근하지 않고 동시에 Touch 좌표와 View 상호작용 하는 것은 기본적으로는 **불가능**함
    - Service에서 WindowManger 클래스를 이용해 투명한 최상위 View를 띄워 Touch event 감지 가능
      - 그러나 Activity에게 이벤트가 전달되지 않음
      > 이벤트의 위치는 뷰 계층 구조의 경계 밖에 있으므로 기본적으로 ViewGroup의 하위 노드로 전달되지 않음
      > https://developer.android.com/reference/android/view/MotionEvent#ACTION_OUTSIDE
      > https://stackoverflow.com/questions/6742177/getting-the-view-that-is-receiving-all-the-touch-events
      > https://stackoverflow.com/questions/9085022/android-overlay-to-grab-all-touch-and-pass-them-on?
      
