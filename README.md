# yalooStore-batch
대량으로 처리되어야 하는 데이터를 처리하기 위한 배치 서버입니다.</br>
해당 배치 서버는 분산 서버(배치 서버가 여러개가 아님)가 아닌 단일 서버로 구성되어 있습니다.

# 구현의 흐름
![image](https://github.com/yalooStore/yalooStore-batch/assets/81970382/dd461800-6917-4879-8f18-f20c2af5a2df)

# Batch server
- Spring이 제공하는 spring-batch 프레임워크를 사용해 배치 서버를 구현하였습니다.
- 배치 서버를 분산 서버로 두고 진행하고 있지는 않지만 규모가 커질 때를 고려한 배치 서버의 분산과 Redis 도입을 생각하고 있습니다.

# API server
- 휴먼 회원찾기와 관련된 API 제공
  - 회원 인증을 통한 로그인 시 해당 회원의 로그인 이력 추가(auth server 내에서 해당 API를 호출해서 해당 작업 진행)
    - [POST] /api/service/members/add/loginHistory/{loginId}
  - 오늘을 기준으로 지난 1년간 로그인 한 이력이 없는 회원 리스트를 돌려주는 API 제공
    - [GET] /api/service/members/loginHistory?today=yyyy-MM-dd   
  - 휴먼 회원화를 행한 경우 다시 해당 회원의 데이터 이력을 업데이트 하기 위한 API 제공
    - [PUT] /api/service/members/modify/inactive
