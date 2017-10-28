package org.dgroup.dockertest.text;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class StringOccurrences {

    private final String base;
    private final String searchStr;

    public boolean nonEqualTo(int occurrences) {
        return !equalTo(occurrences);
    }

    public boolean equalTo(int occurrences){
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){
            lastIndex = base.indexOf(searchStr,lastIndex);
            if(lastIndex != -1){
                count ++;
                lastIndex += searchStr.length();
            }
        }
        return count == occurrences;
    }
}