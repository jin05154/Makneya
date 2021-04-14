# Makneya 막내야
- 직장인들의 최대 고민 : "오늘 점심 뭐 먹지"
- 회사 막내의 고충 : 부장님의 취향저격 점심메뉴 골라서 이쁨받기
- 1시간 전부터 점심메뉴 고민, 고르고도 부장님 맘에 안 들어...

대한민국의 모든 회사 막내들을 위해 만들었다!

2021.04.01 
메인화면 아래 navigation bar를 구현하려 했으나
activity_main.xml에서 tools:context=".MainActivity" 부분이 에러가 나서 앱이 실행조차 안 되고,
에러의 원인이 무엇인지 잘 모르겠다.

=> package를 안 불러와서 문제

mainactivity는 노란 빈 화면으로.

activity가 많이 겹치게 하면 안 됨.

2021.04.07
navigation bar 구현, default menu는 home
activity main은 흰 화면에 navigation bar.
home fragment의 노란 파일 모양 구현하려는데 위의 부장 탭과 파일 본체(?)가 분리되는 것을 해결 못함

2021.04.15
Home, MyInfo fragment마다 대략적인 전체 structure 다 잡았음
버튼 누를 시 fragment to activity action 성공 ("결재받기" 버튼)
