[![Gitter](https://badges.gitter.im/dgroup/docker-unittests.svg)](https://gitter.im/dgroup/docker-unittests?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![CircleCI](https://circleci.com/gh/dgroup/docker-unittests.svg?style=svg&circle-token=b92ed160ef63a282a5464d370494df411d6d5600)](https://circleci.com/gh/dgroup/docker-unittests)
[![0pdd](http://www.0pdd.com/svg?name=dgroup/docker-unittests)](http://www.0pdd.com/p?name=dgroup/docker-unittests)
[![Dependency Status](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5a26cbce0fb24f3480a39124)

@todo #/DEV how-to guide is required because users have no idea how to test docker images with this tool
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