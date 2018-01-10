### Contributing F.A.Q.
1. Use `@todo #/DEV` labels for all todo tasks.
   Its allows 0pdd to create the issues.
2. Run `mvn clean package -Pqulice` before commit. 
   All issues related to your code should be fixed before commit.
3. Each class should have the javadocs with these tags `@author`, `@version`, `@since`.
   For example,
   ```java
     /**
      * .
      *
      * @author Yurii Dubinka (yurii.dubinka@gmail.com)
      * @version $Id$
      * @since 1.0.0
      */
     public final class Args {   
     ...
   ```
   You can configure such template in your IDE.
   For IntelliJ IDEA:
   - File > Settings > Editor > File and Code Templates > Includes > File Header 
   - Put the following text (change name, email and version accordingly)
     ```java
      /**
       *
       * .
       *
       * @author Yurii Dubinka (yurii.dubinka@gmail.com)
       * @version $Id$
       * @since 1.0.0
       */
     ```
   - Specify your name and email
   - Press `Apply`, `OK`.  
4. Settings > Editor > Code Style 
    - Change `Hard wrap at` to `80`
    - Press `Apply` > `OK`
5. Settings > Editor > Code Style > Java > Wrapping and Braces
    - Enable "Ensure right margin is not exceeded" tick
    - Press `Apply` > `OK`
6. Settings > Editor > Code Style > Java > JavaDoc
   - Disable `Generate "<p>" on empty lines`
7. Settings > Editor > Code Style > Java > Blank Lines
   - Set `In declarations` equal to 0 in section `Keep Maximum Blank Lines`
8. Settings > Editor > Code Style > Java > Imports
   - Set the following order for `Import Layout` section
     ```bash
     import java.*
     import javax.*
     import all other imports
     import static all other imports
     ```
   - Change `Class count to use import with '*'` to 20
   - Change `Names count to use static import with '*'` to 20
9. Settings > Editor > Сopyright > Formatting > Java
    - Select `Use custom formatting options`
    - Select `Separator before` with `Length` equal to 1
10. Settings > Editor > File and Code Templates > For current project > Files > Class
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
11. Settings > Editor > Code Style > Java > Tabs and Indents
    - Change `Continuation Indent` to 4
    - Press `OK`
12. Settings > Editor > Copyright > Copyright Profiles > Add new with name `default` > 
     - Copy [license](../LICENSE.txt) 
     - Press `Apply` > `OK`
14. Settings > Editor > Code Style > Java > Code Generation 
     - Select option "Make generated local variables final"
     - Select option "Make generated parameters final"
15. Settings > Editor > Code Style > Java > Spaces
     - Select `Array initializer braces`
     - Press `Apply` > `OK`
16. Settings > File and Code Templates > Includes
     - Press `+`, name = `Unit-test File Header`, extension = `java`
     - Add text like below (change name, email and version accordingly)
        ```java
         /**
          * Unit tests for class {@link }.
          * 
          * @author  Yurii Dubinka (yurii.dubinka@gmail.com)
          * @version $Id$
          * @since   1.0
          * @checkstyle JavadocMethodCheck (500 lines)
          */
    	  @SuppressWarnings("PMD.AvoidDuplicateLiterals")
        ```
     - Press `Apply`
	 - Settings > File and Code Templates > Includes > Code
	 - Change `JUnit4 Test Class` to
	    ```java
		    ...
		    #parse("Unit-test File Header.java")
		    ...
  		```
     - Change `JUnit5 Test Class` to
	    ```java
		    ...
		    #parse("Unit-test File Header.java")
		    ...
     - Press `OK`