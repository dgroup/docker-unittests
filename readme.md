
  
[![Maven](https://img.shields.io/maven-central/v/com.github.dgroup/docker-unittests.svg)](https://mvnrepository.com/artifact/com.github.dgroup/docker-unittests)
[![Javadocs](http://www.javadoc.io/badge/com.github.dgroup/docker-unittests.svg)](http://www.javadoc.io/doc/com.github.dgroup/docker-unittests)
[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](./license.txt) 
[![Commit activity](https://img.shields.io/github/commit-activity/y/dgroup/docker-unittests.svg?style=flat-square)](https://github.com/dgroup/docker-unittests/graphs/commit-activity)

[![Build Status](https://travis-ci.org/dgroup/docker-unittests.svg?branch=master&style=for-the-badge)](https://travis-ci.org/dgroup/docker-unittests)
[![0pdd](http://www.0pdd.com/svg?name=dgroup/docker-unittests)](http://www.0pdd.com/p?name=dgroup/docker-unittests)
[![Dependency Status](https://requires.io/github/dgroup/docker-unittests/requirements.svg?branch=master)](https://requires.io/github/dgroup/docker-unittests/requirements/?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/dgroup/docker-unittests/badge.svg)](https://snyk.io/org/dgroup/project/58b731a9-6b07-4ccf-9044-ad305ad243e6/?tab=dependencies&vulns=vulnerable)

<!--- [![Open issues](https://milestone.sloppy.zone/github/dgroup/docker-unittests/milestone/2)](https://github.com/dgroup/docker-unittests/milestone/2) -->
<a href="https://www.yegor256.com/2017/10/24/award-2018.html">
  <img src="docs/winner-dgroup.png" height=45px alt='Winner Badge'/>
</a>

[![DevOps By Rultor.com](http://www.rultor.com/b/dgroup/docker-unittests)](http://www.rultor.com/p/dgroup/docker-unittests)
[![EO badge](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org/#principles)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

<!--- [![jpeek report](http://i.jpeek.org/com.github.dgroup/docker-unittests/badge.svg)](http://i.jpeek.org/com.github.dgroup/docker-unittests) -->
[![Qulice](https://img.shields.io/badge/qulice-passed-blue.svg)](http://www.qulice.com/)
[![SQ passed](https://sonarcloud.io/api/project_badges/measure?project=com.github.dgroup.dockertest%3Adocker-unittests&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.github.dgroup.dockertest:docker-unittests)
[![SQ bugs](https://sonarcloud.io/api/project_badges/measure?project=com.github.dgroup.dockertest%3Adocker-unittests&metric=bugs)](https://sonarcloud.io/dashboard/index/com.github.dgroup.dockertest:docker-unittests)
[![SQ tech debt](https://sonarcloud.io/api/project_badges/measure?project=com.github.dgroup.dockertest%3Adocker-unittests&metric=sqale_index)](https://sonarcloud.io/dashboard/index/com.github.dgroup.dockertest:docker-unittests)
[![Codebeat](https://codebeat.co/badges/f61cb4a4-660f-4149-bbc6-8b66fec90941)](https://codebeat.co/projects/github-com-dgroup-docker-unittests-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a44d11a620da4ff0a6ff294ff9045aa3)](https://www.codacy.com/app/dgroup/docker-unittests?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dgroup/docker-unittests&amp;utm_campaign=Badge_Grade)

[![SQ coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.dgroup.dockertest%3Adocker-unittests&metric=coverage)](https://sonarcloud.io/dashboard/index/com.github.dgroup.dockertest:docker-unittests)
[![Codecov](https://codecov.io/gh/dgroup/docker-unittests/branch/master/graph/badge.svg?token=Pqdeao3teI)](https://codecov.io/gh/dgroup/docker-unittests)

The main concept is that all tests should use the image as is without any 'internal' go-related features.
We, like users, receive the image and we are going to check what we've got.

The project has been started in Java as POC, however, I'm thinking about porting to python which is more suitable lang for the Ansible-oriented stack. 
Kindly ask you to raise the issue in case of any suggestions regarding another ways\languages.

#### General image test
1. Define an [*.yml file](./docs/image-tests.yml) with tests.
   ```yml

    version: 1.1

    tests:

      - assume: "java version is 1.9, Debian build"
        cmd:    "java -version"
        output:
          contains:
            - openjdk version "9.0.1"
            - build 9.0.1+11-Debian

      - assume: "curl version is 7.xxx"
        cmd:    "curl --version"
        output:
          startsWith: "curl 7."
          matches:
           - "^curl\\s7.*\\n.*\\nProtocols.+ftps.+https.+telnet.*\\n.*\\n$"
          contains:
           - "AsynchDNS IDN IPv6 Largefile GSS-API"
    ```
2. Run tests for image 
   ```bash
    java -jar docker-unittests.jar -f image-tests.yml -i openjdk:9.0.1-11
   ``` 
   ![docker image tests results](./docs/image-tests-results.png)
#### Test image by shell script
1. Define the `test.yml` with tests.
   ```yaml
    version: 1.1
    
    tests:
    
      -  assume: "java version is 1.9, Debian build"
         cmd:    "java -version"
         output:
            contains:
             - openjdk version "9.0.1"
             - build 9.0.1+11-Debian
    
      # The test below will fail due to wrong version of curl.
      -  assume: "curl version is 8000"
         cmd:    "curl --version"
         output:
            startsWith: "curl 8000"
            matches:
              - "^curl\\s7.*\\n.*\\nProtocols.+ftps.+https.+telnet.*\\n.*\\n$"
            contains:
              - "AsynchDNS IDN IPv6 Largefile GSS-API"
   ``` 
2. Define an `test.sh` with testing command
   ```bash
    #!/usr/bin/env bash
    set -e
    echo Testing has been started
    java -jar docker-unittests.jar -f test.yml -i openjdk:9.0.1-11
    echo This line will not be executed as testing will fail
    ```
3. Run the `test.sh`
    ![docker image tests results](./docs/image-tests-results-failure.png)

#### Output matching predicates

| Predicate   | Multiple | YML tag format                                   |
|-------------|:--------:|--------------------------------------------------|
| startsWith  | No       | `startsWith: "curl 8000"`                        |
| endsWith    | No       | `endsWith: "VM (build 25.181-b13, mixed mode)"`  |
| equals      | No       | `equals: "curl 7.54.0"`                          |
| contains    | Yes      | `contains: ["7.54", "LibreSSL", "pop3 pop3s"]`   |
| matches     | Yes      | `matches: ["^curl\\s7.*\\n.*\\nProtocols.+ftps.+https.+.*\\n$"]` |
#### F.A.Q.
 - [Contributing guide](./docs/contributing.md)
 - [Github](./docs/github.md)
 - [Docker](https://github.com/dgroup/docker-on-windows#docker-faq)                                       
