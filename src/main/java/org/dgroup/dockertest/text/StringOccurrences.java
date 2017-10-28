package org.dgroup.dockertest.text;


public final class StringOccurrences {

    private final String origin;
    private final String searchStr;

    public StringOccurrences(String origin, String search){
        this.origin = origin;
        this.searchStr = search;
    }

    public boolean nonEqualTo(int occurrences) {
        return !equalTo(occurrences);
    }

    public boolean equalTo(int occurrences){
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){
            lastIndex = origin.indexOf(searchStr,lastIndex);
            if(lastIndex != -1){
                count ++;
                lastIndex += searchStr.length();
            }
        }
        return count == occurrences;
    }
}