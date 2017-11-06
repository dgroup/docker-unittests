package org.dgroup.dockertest.yml;

import org.dgroup.dockertest.text.PlainFormattedText;

/**
 * .
 *
 * @author Yurii Dubinka (yurii.dubinka@gmail.com)
 * @since 0.1.0
 **/
public final class IllegalYmlFormatException extends RuntimeException {

    public IllegalYmlFormatException(PlainFormattedText msg){
        this(msg.toString());
    }

    public IllegalYmlFormatException(String msg) {
        super(msg);
    }
}
