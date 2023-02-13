
## Introduction
[Gralde](https://docs.gradle.org/current/userguide/userguide.html) is developed at 2007. Gradle build scripts are written using Groovy or Kotlin.


## Why to use?
- Build
- Dependency management
- Faster performance and simpler syntax than [Maven](https://maven.apache.org/guides/) \
Gradle support caching and skipping the tasks when change doesn't occurs.
- 

## How to operate?

1. Initialization
2. Configuration \
Construct Directed Acyclic Graph(DAG) internally.
3. Execution



## Prerequisite
### Install Java JDK
```bash
$ java --version
```
### Install gradle manually
#### Windows 
Install from [this link](https://gradle.org/releases/)
#### Unix
```bash
$ sdk install gradle 7.6
```

#### Mac 
```bash
$ brew install gradle
```
#### Check version
```bash
$ gradle --version
```

### Shortuct
```bash
$ gradle build
$ gradle tasks
$ gradle properties
# Deletes the build folder and lets you start fresh
$ gradle clean build
```


# Configuration build.gradle

### gradle wrapper
This allows us to use the same gradle version anywhere we build.

#### gradlew(.bat)
Just contain script that runs on different OS

#### settings.gradle
Support multiple project (multi module)

### Repositories
```gradle
repositories {
    mavenCentral() // find here at first
    google() // find here at second
}
```
### Transitive Dependency
```bash
# Show dependency quietly
$ gradle -q dependencies
```

```text
org.junit.jupiter:junit-jupiter-engine:5.9.0 has transitive dependencies as shown below
\--- org.junit.jupiter:junit-jupiter-engine:5.9.0
     +--- org.junit:junit-bom:5.9.0
     |    +--- org.junit.jupiter:junit-jupiter-api:5.9.0 (c)
     |    +--- org.junit.jupiter:junit-jupiter-engine:5.9.0 (c)
     |    +--- org.junit.platform:junit-platform-engine:1.9.0 (c)
     |    \--- org.junit.platform:junit-platform-commons:1.9.0 (c)
     +--- org.junit.platform:junit-platform-engine:1.9.0
     |    +--- org.junit:junit-bom:5.9.0 (*)
     |    +--- org.opentest4j:opentest4j:1.2.0
     |    \--- org.junit.platform:junit-platform-commons:1.9.0
     |         \--- org.junit:junit-bom:5.9.0 (*)
     \--- org.junit.jupiter:junit-jupiter-api:5.9.0
          +--- org.junit:junit-bom:5.9.0 (*)
          +--- org.opentest4j:opentest4j:1.2.0
          \--- org.junit.platform:junit-platform-commons:1.9.0 (*)
```

### Parameterization
```build.gradle
// This must be placed before `plugin`
buildscript {
    ext {
        jsoupVersion = '1.15.3'
    }
}

// ..
dependencies {
    // group-id:artifact-id:version
    implementation "org.jsoup:jsoup:${jsoupVersion}"
}
```

### Cache
Gradle stores the cache files at C:\Users\{USER_NAME}\.gradle\caches\{GRADLE_VERSION}.
1. If build, gradle stores the cache files
2. when build, Check  dependency checking in ~/.gradle/caches if already exsists
3. If exists, not install it \
Not exists, install and refresh the cache f

### SourceSets
Notify where the source files to gradle
```build.gradle
sourceSets {
    // Contains production source code of the project,
    // Which is compiled and assembled into a JAR

    // `main` is the name of source set, This is id (plugins: id 'java')
    main {
        java { // configures the java source this source set
            srcDir 'src'
        }
    }
}


```


### What's the Implement, compileOnly, testImplement? 
Faster compilation time due to fewer dependency path
```build.gradle
dependencies {
    implementation "org.jsoup:jsoup:${jsoupVersion}"
    // junit is only needed to test -> compile or runtime
    testImplementation 'org.junit.jupiter:junit-jupiter-engine:5.9.0'
}
```

If I set jsoup as `compileOnly`, main can't run it since it's just used to compiled not on runtime

```build.gradle
dependencies {
    compileOnly "org.jsoup:jsoup:${jsoupVersion}"
}
```

```java
public class Practice {
    public static void main(String[] args) {
        // ⚠️ Can't execute this
        // Caused by: java.lang.ClassNotFoundException: org.jsoup.Jsoup
        Jsoup.connect("http://www.infomoney.com.br/mercados/bitcoin").ignoreContentType(true).execute().body();
    }
}
```


### What's the sequence of tasks? How to customize it? (ex. annotation)
