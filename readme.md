## Docker-unittests

[![Build Status](https://travis-ci.org/dgroup/docker-unittests.svg?branch=master)](https://travis-ci.org/dgroup/docker-unittests)
[![DevOps By Rultor.com](http://www.rultor.com/b/dgroup/docker-unittests)](http://www.rultor.com/p/dgroup/docker-unittests)
[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](./license.txt)

[![0pdd](http://www.0pdd.com/svg?name=dgroup/docker-unittests)](http://www.0pdd.com/p?name=dgroup/docker-unittests)
[![Open issues](https://milestone.sloppy.zone/github/dgroup/docker-unittests/milestone/2)](https://github.com/dgroup/docker-unittests/milestone/2)
[![Dependency Status](https://requires.io/github/dgroup/docker-unittests/requirements.svg?branch=master)](https://requires.io/github/dgroup/docker-unittests/requirements/?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/dgroup/docker-unittests/badge.svg)](https://snyk.io/test/github/dgroup/docker-unittests)
[![Dependency Status](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124)

[![Qulice](https://img.shields.io/badge/qulice-passed-blue.svg)](http://www.qulice.com/)
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=org.dgroup.dockertest:docker-unittests)](https://sonarcloud.io/dashboard/index/org.dgroup.dockertest:docker-unittests)
[![Codecov](https://codecov.io/gh/dgroup/docker-unittests/branch/master/graph/badge.svg?token=Pqdeao3teI)](https://codecov.io/gh/dgroup/docker-unittests)
[![Codebeat](https://codebeat.co/badges/f61cb4a4-660f-4149-bbc6-8b66fec90941)](https://codebeat.co/projects/github-com-dgroup-docker-unittests-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a44d11a620da4ff0a6ff294ff9045aa3)](https://www.codacy.com/app/dgroup/docker-unittests?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dgroup/docker-unittests&amp;utm_campaign=Badge_Grade)

The main concept is that all tests should use the image as is without any 'internal' go-related features.
We, like users, receive the image and we are going to check what we've got.

The project has been started in Java as POC, however, I'm thinking about porting to python which is more suitable lang for the Ansible-oriented stack. 
Kindly ask you to raise the issue in case of any suggestions regarding another ways\languages.

1. Define an [*.yml file](./.guides/image-tests.yml) with tests.
   ```yml
   version: 1
   
   tests:
     - test:
         assume: "java version is 1.9, Debian build"
         cmd:    "java -version"
         output:
           - contains: "openjdk version \"9.0.1\""
           - contains: "build 9.0.1+11-Debian-1"
   
     - test:
         assume: "curl version is 7.xxx"
         cmd:    "curl --version"
         output:
           - startsWith: "curl 7."
           - matches:    "^curl\\s7.*\\n.*\\nProtocols.+ftps.+https.+telnet.*\\n.*\\n$"
           - contains:   "AsynchDNS IDN IPv6 Largefile GSS-API"
   ```
2. Run tests for image 
   ```bash
    java -jar docker-unittests.jar -f image-tests.yml -i openjdk:9.0.1-11
   ``` 
   ![docker image tests results](./.guides/image-tests-results.png)
3. Run tests from *.sh
   ```yaml
   version: 1
    
    tests:
    
      - test:
          assume: "java version is 1.9, Debian build"
          cmd:    "java -version"
          output:
            - contains: openjdk version "9.0.1"
            - contains: build 9.0.1+11-Debian
    
      - test:
          assume: "curl version is 8000"
          cmd:    "curl --version"
          output:
            - startsWith: "curl 8000"
            - matches:    "^curl\\s7.*\\n.*\\nProtocols.+ftps.+https.+telnet.*\\n.*\\n$"
            - contains:   "AsynchDNS IDN IPv6 Largefile GSS-API"

   ``` 
   ./test.sh
   ```bash
    #!/usr/bin/env bash
    set -e
    echo Testing has been started
    java -jar docker-unittests.jar -f test.yml -i docker/whalesay
    echo This line will not be executed.
    ```
    ![docker image tests results](./.guides/image-tests-results-failure.png)

#### F.A.Q.
 - Supported output predicates are `startsWith`, `endsWith`, `contains`, `equals` and `matches`(regexp statement).
 - [Contributing guide](./.guides/contributing.md)  
 - [Github](./.guides/github.md)
 - [Docker](https://github.com/dgroup/docker-on-windows#docker-faq)                                       
                                    
#### TODOs for release 1.0
 - @todo #14 Add http://i.jpeek.org badge after release to maven central
 - @todo #17 Add http://www.javadoc.io badge after release to maven central
 - @todo #80 Check rultor as POC for deployment process to mvn central
 - @todo #81 Publish automatically binary artifacts to github releases    
