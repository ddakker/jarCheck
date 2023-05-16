# java version check
jar 파일 또는 여러개의 jar가 묶여 있는 fat jar 형태도 가능

```
$ java -jar build/libs/jarCheck.jar test/test.jar 1.1 17

[OUTPUT]
...
major: 49 : 1.5 - test.jar:org/codehaus/aspectwerkz/DeploymentModelEnum.class
...
major: 55 : 11.0 - BOOT-INF/lib/infinispan-commons-jdk11-13.0.10.Final.jar:org/infinispan/commons/jdkspecific/ProcessorInfo.class
major: 55 : 11.0 - BOOT-INF/lib/infinispan-commons-jdk11-13.0.10.Final.jar:org/infinispan/commons/jdkspecific/CallerId.class
...
```