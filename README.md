# SpyGame

# 상세 

이 플러그인은 2020년 9월 6일 일요일 트위치 스트리머 합동방송에서 방영된 
레지스탕스 아발론의 마인크래프트 플러그인 입니다. 


# 설명
이 미니게임은 레지스탕스 아발론이라는 보드게임을 마인크래프트 기반으로 제작 되었습니다.

## 레지스탕스 아발론이 뭐에요❓
중세시대를 기반으로 한 마피아게임류 입니다.

아무도 죽지 않는 마피아게임 으로써 악의 세력을 가진 사람들이 판을 망치는것을 막아야하는 게임입니다.

자세한 게임설명은 아래 영상으로 확인할수있습니다.


## 마인크래프트 플러그인이 뭐에요❓
먼저 서버를 알아야하기때문에 설명드리자면

마인크래프트 서버는 크게 2가지가 있습니다.

1. Mojang에서 배포하는 멀티플레이어 서버 프로그램 (바닐라 서버)
2. Spigot, CraftBukkit등 사설 멀티플레이어 서버 프로그램

바닐라 서버는 유저가 직접 조작을 못하고 단순히 서버를 운영하는 명령어 밖에 없습니다.

그에 반면에 Spigot이나 CraftBukkit등 사설 서버들은 유저가 서버에서 조작할수있는 JAVA 기반의 API를 제공하며

클라이언트의 행동와 인게임내 크리처들, 날씨등등 다양한 게임내 이펙트와 패킷들을 조작할수있도록 폭넓게 제공합니다.

API를 활용해서 라이브러리를 만드는데 이 라이브러리를 플러그인 라고 합니다.

![마인크래프트 구조](asset/스크린샷%202020-09-13%20오후%207.02.54.png)


## 주의사항
* 플러그인은 Library jar로써 반드시 spigot 서버를 통해 실행할수있습니다.
* Spigot 1.12.2 버전와 CatServer 에는 반드시 JDK 8로 빌드를 하셔야합니다!! 

# 빌드
```
$ gradle jar 
```

# 버전 정보
CatServer: 1.12.2 All Version
Spigot : 1.12.0 ~ 1.12.2

## 구조

아발론 게임 특성상 제한시간없이 토론이 진행되는만큼
유저가 커맨드 블록으로 플러그인 을 실행할수있게
게임 진행 단락별로 명령어가 제공됩니다.

![서버 구조](asset/스크린샷%202020-09-13%20오후%207.48.50.png)


## 명령어 목록


| 명령어        | 설명           |
| ------------- |:-------------:|
| /start      | 랜덤으로 사람들을 뽑아서 게임을 시작합니다. |
| /게임끝      | 게임이 끝났습니다. 게임을 초기화합니다. |
| /멀린암살      | 강제로 멀린을 암살하는 커맨드입니다. |
| /승리선언      | 승리를 선언하는 커맨드입니다.|
| /패배선언      | 패배를 선언하는 커맨드입니다. |
| /원정      | 원정을 시작합니다. |
| /원정종료    | 원정을 종료합니다. |
| /마차시퀸스 | 원정을 시작하고 나고 마차를 끝냅니다. |
| /투표결과공개 | 원정 투표 결과를 공개합니다. |
| /라운드체크 | 원정 투표 결과를 공개합니다. |
| /원정대장선정 <플레이어> | 원정대장을 지정하는 플레이어에게 투표 시작합니다. |
| /호수의요정선정 | 랜덤으로 플레이어에게 호수의 요정을 지정합니다. |
| /라운드별원정대원선발 | 랜덤으로 플레이어에게 호수의 요정을 지정합니다. |

## 치트 코드
개발시에 원할한 테스트와 디버깅을 위해서 게임내 치트코드가 준비되어있습니다.

| 명령어        | 설명           |
| ------------- |:-------------:|
| /투표결과      | 투표결과를 채팅창으로 보여줍니다. |
| /강제원정참여 <닉네임>      | 해당 플레이어는 강제적으로 원정에 참여합니다. |
| /강제캐릭터생성 <닉네임>      | 강제로 플레이어를 게임에 참가시킵니다. |
| /투표결과블록수집      | 투표결과 블록을 강제적으로 보여줍니다. (애니메이션으로 보여줍니다.)|
| /원정역할보기      | 원정대에 참여한 역할들을 보여줍니다. |
| /지정캐릭터추가  <닉네임> <역할>    | 플레이어에 아발론 캐릭터를 강제적으로 부여합니다.|
| /지정캐릭터게임롤추가 <닉네임> <역할>   | 플레이어에 아발론 원정 역할을 강제적으로 부여합니다. |
| /디버그모드 | 하드코딩된 플레이어 닉네임들을 플레이어로 참여를 안하도록 합니다.|
    
## Contribute
모든 기여는 언제든지 환영합니다!

장애 제보 및 버그 제보는 아래를 통해 주시면 됩니다.

* 장애 & 피드백 제보 : Github Issue 
* PR : Github Pull Request

# 저작권

```
Copyright(c) 2020.laskdjlaskdj12 all right reserved

이 프로그램의 모든 저작권은 laskdjlaskdj12 에게 있습니다.
```

# 크레딧
이 플러그인을 만들때 도움을 주신 분

### 기술적으로 도와주셨던 분
- 다뉴엘
- RanolP
- BasiX


### QA & 도와주신분들
 - [실피아](https://www.twitch.tv/leegw15)
 - 개인지도
 - [겜닭](https://www.twitch.tv/poi124)
 - 광곰
 - [김기랑](https://www.twitch.tv/kimgirang)
 - 늘호
 - 도리
 - 잉여남작
 - 체리_
 - 트리비
 - 판다라고 불러줘
 - 탱탱한 푸탱
 - 맥남
 - 시윤

### 스트리머
  - [가이코](https://www.twitch.tv/pymyp2288)
  - [공파리파, 세상을 치유했던 목소리](https://www.twitch.tv/gongparipa)
  - [인류 최후의 로멘티스트 김규식](https://www.youtube.com/channel/UC_eXONnL5HKKTpa6_t4BTOw)
  - [3대 500쳤던 소녀 랑이씌](https://www.twitch.tv/rang0210)
  - [모카형](https://www.twitch.tv/oilj_far)
  - [금사향](https://www.twitch.tv/sah_yang)
  - [애덕](https://www.twitch.tv/aduck09)
  - [이춘향](https://www.twitch.tv/leechunhyang)
