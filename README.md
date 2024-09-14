# LMS SpringBoot
## 목적
도메인, 엔티티가 격리된 환경에서의 개발

## 설명
* Entity는 데이터베이스 관련된 작업만 한다.
* Domain은 비지니스 관련된 작업만 한다.
* Repository는 엔티티를 반환한다.
* Application Layer는 Repository가 아닌 Reader, Processor를 의존한다.
  * Reader: Repository에서 읽어온 데이터를 Domain으로 변환한 후 반환한다.
  * Processor: Application Layer에서 받아온 Domain을 Entity로 변환한 후 저장한다. 

## TODO
  * [ ] 기본적인 API 기능 완성
    * 강의, 수강, 교사, 학생
  * [ ] 오프라인 강의 수강 기능에 분산락 적용
  * [ ] 각 애그리거트를 모듈로 분리
  * [ ] MSA 적용