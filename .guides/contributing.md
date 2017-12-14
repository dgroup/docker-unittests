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
    */
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
         */
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
10. Settings > Editor > File and Code Templates > For current project > Includes > File Header
    - Put the following text
       ```java
        /**
    	 * .
    	 * 
    	 * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
    	 * @version $Id$
    	 * @since   0.1.0
    	 */
       ```
     - Change your name, email and lib version accordingly.
11. Settings > Editor > Copyright > Copyright Profiles > Add new with name `default` > 
     - Copy [license](../LICENSE.txt) 
     - Press `Apply` > `OK`
12. Settings > Editor > Code Style > Java > Code Generation 
     - Select option "Make generated local variables final"
     - Select option "Make generated parameters final"
       