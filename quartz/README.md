Kotlin and Springboot Quartz  
================

[//]: # (## reference site)

[//]: # (| site     | url                                             |)

[//]: # (|----------|-------------------------------------------------|)

[//]: # (| official | https://www.quartz-scheduler.org                |)

[//]: # (| bealdung | https://www.baeldung.com/spring-quartz-schedule |)

## 구성
- Quartz 저장소 타입 : jdbc
- JDBC : H2

## Reference
### Official 
https://www.quartz-scheduler.org               

### bealdung 
https://www.baeldung.com/spring-quartz-schedule

#### Spring.IO
https://docs.spring.io/spring-boot/docs/2.0.0.M3/reference/html/boot-features-quartz.html

#### job 과 scheduler 분리 시도
https://homoefficio.github.io/2019/09/28/Quartz-스케줄러-적용-아키텍처-개선-1/

## 메모
#### `QuartzJobBean.class`는 더 이상 사용하지 않아도 무관
- SpringBeanJobFactory 에서 동일한 작업을 해주므로, ```Job.class``` 사용하면 됨
#### Job 과 Trigger 각각 리스너 적용 가능, 전처리, 필터링 등 여러가지 활용
#### Job 실패에 대한 사항은 Trigger의 MisfireInstruction 속성으로 적절한 처리 가능
- MISFIRE_INSTRUCTION_ 로 시작하는 상수값으로 타입들 구분가능


[//]: # (Building the project)

[//]: # (====================)

[//]: # ()
[//]: # (Though it should not be needed often to build the entire repository at once because we are usually concerned with a specific module.)

[//]: # ()
[//]: # (But if we want to, we can invoke the below command from the root of the repository if we want to build the entire repository with only Unit Tests enabled:)

[//]: # ()
[//]: # (`mvn clean install -Pdefault-first,default-second,default-heavy`)

[//]: # ()
[//]: # (or if we want to build the entire repository with Integration Tests enabled, we can do:)

[//]: # ()
[//]: # (`mvn clean install -Pintegration-lite-first,integration-lite-second,integration-heavy`)

[//]: # ()
[//]: # (Analogously, for the JDK9 and above projects the commands are:)

[//]: # ()
[//]: # (`mvn clean install -Pdefault-jdk9-and-above`)

[//]: # ()
[//]: # (and)

[//]: # ()
[//]: # (`mvn clean install -Pintegration-jdk9-and-above`)

[//]: # ()
[//]: # (Building a single module)

[//]: # (====================)

[//]: # (To build a specific module, run the command: `mvn clean install` in the module directory.)

[//]: # ()
[//]: # ()
[//]: # (Running a Spring Boot module)

[//]: # (====================)

[//]: # (To run a Spring Boot module, run the command: `mvn spring-boot:run` in the module directory.)

[//]: # ()
[//]: # ()
[//]: # (Working with the IDE)

[//]: # (====================)

[//]: # (This repo contains a large number of modules. )

[//]: # (When you're working with an individual module, there's no need to import all of them &#40;or build all of them&#41; - you can simply import that particular module in either Eclipse or IntelliJ. )

[//]: # ()
[//]: # ()
[//]: # (Running Tests)

[//]: # (=============)

[//]: # (The command `mvn clean install` from within a module will run the unit tests in that module.)

[//]: # (For Spring modules this will also run the `SpringContextTest` if present.)

[//]: # ()
[//]: # (To run the integration tests, use the command:)

[//]: # ()
[//]: # (`mvn clean install -Pintegration-lite-first` or )

[//]: # ()
[//]: # (`mvn clean install -Pintegration-lite-second` or )

[//]: # ()
[//]: # (`mvn clean install -Pintegration-heavy` or)

[//]: # ()
[//]: # (`mvn clean install -Pintegration-jdk9-and-above`)

[//]: # ()
[//]: # (depending on the list where our module exists)
