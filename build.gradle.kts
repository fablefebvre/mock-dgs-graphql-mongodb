plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.netflix.dgs.codegen") version "7.0.3"
	id("io.freefair.lombok") version "8.13.1"
}

group = "com.fabrewer.mock"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["netflixDgsVersion"] = "10.0.4"

dependencies {
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-undertow")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	modules {
		module("org.springframework.boot:spring-boot-starter-reactor-netty") {
			replacedBy("org.springframework.boot:spring-boot-starter-undertow", "Use Undertow instead of Reactor Netty")
		}
	}
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:${property("netflixDgsVersion")}")
	}
}

tasks.generateJava {
	schemaPaths.add("${projectDir}/src/main/resources/schema")
	packageName = "com.fabrewer.mock.graphql.codegen"
	generateClient = true
}

tasks.withType<Test> {
	useJUnitPlatform()
}
