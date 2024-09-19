# LMS Playground
## 목적
모든 것을 집어 삼키는 프로젝트
* 서비스 출시 목표가 아님. 재밌어 보이는 기술은 모두 흡수한다.
* 사용할 수 있는 모든 기술을 학습하는 용도로 사용한다.

## 프로젝트 모듈 구조
* common: 인프라에 관련 없는 공용 유틸
* core: 기본 도메인
  * api
  * domain
  * persistence
* core-store: 스토어 도메인
  * api
  * domain
* eureka-server: Eureka 서버
* gateway: 게이트웨이 서버
* infrastructure: 각 모듈에서 사용할 수 있는 인프라 관련 모듈

## 적용 기술, 기능
* 멀티 모듈
  * [x] Entity, Domain 분리
  * [x] API, Domain 분리
* MSA
  * [x] Spring Eureka
  * [x] Spring Cloud Gateway
  * [ ] 보상 트랜잭션
  * [ ] ...
* Framework
  * [x] Spring MVC
  * [ ] Spring Webflux
  * [x] Spring Security
  * [x] Spring Data JPA
  * [ ] QueryDSL
검색
  * [ ] Elasticsearch
  * [ ] 추천 알고리즘
* Redis
  * [x] 분산락
  * [x] Redis Caching
* [ ] Mock 결제 모듈 적용
* [ ] Apache Kafka
* 컨테이너
  * [ ] Docker
  * [ ] k8s
* 모니터링
  * [ ] Prometheus & Grafana
