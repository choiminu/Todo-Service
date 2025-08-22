# Todo-Service
Todo-Service는 로그인한 사용자가 자신의 카테고리 안에서 할 일(Task)을 생성하고 관리할 수 있는 애플리케이션입니다.  
특정 Task는 공유 링크로 외부에 공개할 수 있으며 공유 시 만료일과 권한(`READ`/`EDIT`) 을 지정해 읽기 전용 또는 편집 가능 상태로 노출할 수 있습니다.

<br>

### 사용한 기술
- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- Spring Security
- Mysql

<br>

### 주력으로 사용한 라이브러리
**Spring Data JPA**
> Spring Data JPA는 JPA를 기반으로 한 ORM 기술을 단순화하여 제공하는 라이브러리입니다. 기본적인 CRUD 메서드를 제공하고 테이블을 빠르게 생성 및 설계할 수 있어 제한된 시간 내에 프로젝트를 시작하기에 적합하다고 생각해 해당 라이브러리를 사용하였습니다.

**MapStruct**
> MapStruct는 Java 애플리케이션에서 객체 간 변환 코드를 컴파일 시점에 자동 생성해주는 라이브러리입니다. 여러 팀 프로젝트를 진행하면서 DTO와 엔티티 간 변환 로직이 반복적이고 코드가 지저분해진다는 문제를 자주 경험했습니다. 과거에는 `Mapper` 클래스를 직접 만들어 수동으로 변환 로직을 작성했지만 이번 과제를 계기로 MapStruct를 학습하고 적용하여 단순한 변환 과정을 자동화할 수 있었습니다. 다만 파라미터가 객체로 포장되어 있거나 복잡한 변환 과정이 필요한 경우에는 여전히 수동으로 변환 로직을 작성했지만 단순한 변환에서는 매우 효율적으로 활용할 수 있었습니다.

**QueryDLS**
> QueryDSL은 JPQL을 타입 안전하게 작성할 수 있도록 지원하는 라이브러리 입니다. 이번 과제에서는 사용자가 생성한 할 일(Task)을 여러 조건(카테고리, 상태, 기간 등)으로 필터링해야 하는 문제가 있었고 단순 쿼리 메서드만으로는 복잡한 조건을 처리하기 어려웠기 때문에 QueryDSL을 도입하였습니다.


**Spring Security**
> Spring Security는 스프링 애플리케이션에서 인증과 인가를 담당하는 보안 프레임워크입니다. 이번 과제에서는 직접적인 인증/인가 기능을 구현하지는 않았지만 비밀번호 암호화를 위해 `PasswordEncoder`를 활용하였습니다.


<br>

### 핵심기능

| 메인페이지 | 할일 등록 |
| ---------- | --------- |
| <p align="center"><img width="500" height="800" src="https://github.com/user-attachments/assets/9e7af774-0988-42e4-a47a-80140222dbfd" /></p> | <p align="center"><img width="500" height="800" src="https://github.com/user-attachments/assets/f04efb07-d5fd-4b61-b419-defeb4bfc3eb" /></p> |
| <p align="center"><img width="500" height="800" src="https://github.com/user-attachments/assets/f98f057f-1693-423f-813f-2257c48042d2" /></p> | <p align="center"><img width="500" height="800" src="https://github.com/user-attachments/assets/6334f764-6d3b-42bf-9cc2-fb3fd592b4552" /></p> |
| <p align="center">회원가입</p> | <p align="center">로그인</p> |



