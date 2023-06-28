# yalooStore-batch

# 배치와 스케줄러

# 구현의 흐름

# Batch server

# API server
- 휴먼 회원찾기와 관련된 API 제공
  - 회원 인증을 통한 로그인 시 해당 회원의 로그인 이력 추가(auth server 내에서 해당 API를 호출해서 해당 작업 진행)
    - [post] /api/service/members/loginHistory 
  - 오늘을 기준으로 지난 1년간 로그인 한 이력이 없는 회원 리스트를 돌려주는 API 제공(http method - get)
  - 휴먼 회원화를 진행한 경우 다시 해당 회원의 데이터 이력을 업데이트 하기 위한 API 제공(http method - post)
    - POST를 사용해서 해당 작업을 수행하는 이유? put의 경우엔 모든 리소스를 변경할 때 사용하고 우리는 휴먼회원으로 만드는 컬럼 하나만 변경해주면 되기 때문이다.
