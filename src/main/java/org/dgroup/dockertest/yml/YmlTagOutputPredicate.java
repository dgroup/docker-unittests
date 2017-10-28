package org.dgroup.dockertest.yml;

import java.util.function.Predicate;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class YmlTagOutputPredicate {

    private final String type;
    private final Predicate<String> predicate;

    public YmlTagOutputPredicate(String type, Predicate<String> predicate) {
        this.type = type;
        this.predicate = predicate;
    }

    public String type(){
        return type;
    }

    public boolean test(String actualValue){
        return predicate.test(actualValue);
    }
}
