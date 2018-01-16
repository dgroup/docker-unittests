[![Gitter](https://badges.gitter.im/dgroup/docker-unittests.svg)](https://gitter.im/dgroup/docker-unittests?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![CircleCI](https://circleci.com/gh/dgroup/docker-unittests.svg?style=svg&circle-token=b92ed160ef63a282a5464d370494df411d6d5600)](https://circleci.com/gh/dgroup/docker-unittests)
[![0pdd](http://www.0pdd.com/svg?name=dgroup/docker-unittests)](http://www.0pdd.com/p?name=dgroup/docker-unittests)
[![Dependency Status](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124)
[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](./LICENSE.txt)

### Testing of docker images
The main concept is that all tests should use the image as is without any 'internal' 
go-related features.
We, like users, receive the image and we are going to check what we've got.

The project has been started in Java as POC, however, I'm thinking about porting to python which is more suitable lang for the Ansible-oriented stack. 
Kindly ask you to raise the issue in case of any suggestions regarding another ways\languages.

1. Define an [*.yml file](./.guides/image-tests.yml) with tests.
   For now only `UTF-8` is supported, user-specific encoding will be implemented in scope of [#41](https://github.com/dgroup/docker-unittests/issues/41).
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
3. Got results
   ![docker image tests results](./.guides/image-tests-results.png)

### F.A.Q.
 - Supported output predicates are `startsWith`, `endsWith`, `contains`, `equals` and `matches`(regexp statement).
 - [Contributing guide](./.guides/contributing.md)  
 - [Github](./.guides/github.md)
 - [Docker](https://github.com/dgroup/docker-on-windows#docker-faq)                                       
                                    
### TODOs for release 1.0-alpha
 - Make project public once milestone [1.0-alpha](https://github.com/dgroup/docker-unittests/milestone/1) is done. 
 - @todo #6 update section "Testing..." with output details for passed and failed case.
    Also add example of usage from shell script (with bash `set -e` option).  
 - @todo #12 Add cobertura/jacoco coverage badge once project moved to public 
 - @todo #13 Add https://codebeat.co badge once project moved to public
 - @todo #14 Add http://i.jpeek.org badge after release to maven central
 - @todo #15 Add https://sonarcloud.io badge once project moved to public
 - @todo #16 Add https://codecov.io badge once project moved to public
 - @todo #17 Add http://www.javadoc.io badge once project moved to public
