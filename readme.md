[![Gitter](https://badges.gitter.im/dgroup/docker-unittests.svg)](https://gitter.im/dgroup/docker-unittests?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![CircleCI](https://circleci.com/gh/dgroup/docker-unittests.svg?style=svg&circle-token=b92ed160ef63a282a5464d370494df411d6d5600)](https://circleci.com/gh/dgroup/docker-unittests)
[![0pdd](http://www.0pdd.com/svg?name=dgroup/docker-unittests)](http://www.0pdd.com/p?name=dgroup/docker-unittests)
[![Dependency Status](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124)

### Testing of docker images
The main concept is that all tests should use the image as is without any 'internal' go-related features.
We, like users, receive the image and we are going to check what we've got.
1. Define an *.yml file with tests
   ```yml
   # image-tests.yml
   
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
           - startWith: "curl 7."
           - contains:  "Protocols: "
           - contains:  "ldap ldaps pop3"
           - contains:  "Features: "
           - contains:  "AsynchDNS IDN IPv6 Largefile GSS-API"
   ```
2. Run tests for image 
   ```bash
    java -jar docker-unittests.jar -f image-tests.yml -i openjdk:9.0.1-11
   ``` 
   `-f` yml file with tests
   
   `-i` docker image 
   
@todo #/DEV add https://codebeat.co badge as project moved to public state

### Contributing F.A.Q.
1. Use `@todo #/DEV` labels for all todo tasks.
   Its allows 0pdd to create the issues.
2. Run `mvn clean package -Pqulice` before commit. 
   All issues related to your code should be fixed before commit.
3. Each class should have the javadocs with these tags `@author`, `@version`, `@since`.
   For example,
   ```java
   /**
    * Represents application command-line arguments.
    *
    * @author Yurii Dubinka (yurii.dubinka@gmai.com)
    * @version $Id$
    * @since 0.1.0
    **/
   public final class Args {
   ...
   ```
   You can configure such template in your IDE.
   For IntelliJ IDEA:
   - File > Settings > Editor > File and Code Templates > Includes > File Header 
   - Paste text below
     ```
        /**
         * .
         * 
         * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
         * @version $Id$
         * @since   0.1.0
         **/
     ```
   - Specify your name and email
   - Press `Apply`, `OK`.  
4. Settings > Editor > Code Style > Java > JavaDoc
   - Disable `Generate "<p>" on empty lines`
5. Settings > Editor > Code Style > Java >Blank Lines
   - Set `In declarations` equal to 0 in section `Keep Maximum Blank Lines`
6. Settings > Editor > Code Style > Java > Imports
   - Set the following order for `Import Layout` section
     ```bash
     import java.*
     import javax.*
     import all other imports
     import static all other imports
     ```
7. Settings > Editor > Сopyright > Formatting > Java
    - Select `Use custom formatting options`
    - Select `Separator before` with `Length` equal to 1
8. Settings > Editor > File and Code Templates > For current project > Files > Class
    - Re-order text to the following
   	  ```java
         /**
          * MIT License
          *
            ...
          * SOFTWARE.
          */
          #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
          #parse("File Header.java")
          public final class ${NAME} {
          }
       
   	  ```
   	  (empty line at the end is required).
9. Settings > Editor > Code Style > Java > Tabs and Indents
    - Change `Continuation Indent` to 4
    - Press `OK`
                                         