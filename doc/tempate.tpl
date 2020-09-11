

// apply plugin: 'java'
// apply plugin: 'idea'
// apply plugin: 'maven-publish'
// apply plugin: 'org.springframework.boot'
// apply plugin: "io.spring.dependency-management"
// apply plugin: "io.freefair.lombok"


// java编译的时候缺省状态下会因为中文字符而失败
// tasks.withType(JavaCompile) {
//     options.encoding = "UTF-8"
// }

// bootJar {
//     // 默认不需要打可执行jar包
//     enabled = false
// }

// configurations {
//     compileOnly {
//         extendsFrom annotationProcessor
//     }
// }


// buildscript {
//     ext {
//         springBootVersion = '2.3.3.RELEASE'
//         springDMVersion = '1.0.10.RELEASE'
//         lombokVersion = '5.2.1'
//     }
//     repositories {
//         // 这个public是central仓和jcenter仓的聚合仓
//         maven { url 'https://maven.aliyun.com/repository/public' }
//         maven { url 'https://maven.aliyun.com/repository/spring' }
//         maven { url 'https://maven.aliyun.com/repository/spring-plugin' }
//         maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
//         maven { url 'https://maven.aliyun.com/repository/google' }
//     }
//     dependencies {
//         classpath("io.spring.gradle:dependency-management-plugin:${springDMVersion}")
//         classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
//         classpath("io.freefair.gradle:lombok-plugin:${lombokVersion}")
//     }
// }


// configure(allprojects.findAll { it.name.startsWith('sub') }) {
//     subTask << {
//         println 'this is a sub project'
//     }
// }

// ext.subProjects = allprojects.findAll { it.name.startsWith('sub') }
// configure(subProjects) {
//     apply plugin: 'war'
// }

// project(':sub-project1') {
//     task forProject1 << {
//         println 'for project 1'
//     }
// }