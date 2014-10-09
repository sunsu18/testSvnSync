package com.sfr.engage.core;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ValueListSplit {
    public static Map<String, String> callValueList(int size, List<String> passedList) {
        int dividedValue;
        int loopValue;
        final int oneFifty = 150;
        Map<String, String> mapList = new HashMap<String, String>();
        dividedValue = size / oneFifty;
        loopValue = dividedValue + 1;
        int count = oneFifty;
        int startIndex = 0;
        int maxIndex = oneFifty;
        for (int i = 0; i < loopValue; i++) {
            String valuesList = "";
            for (int j = startIndex; j < maxIndex; j++) {
                if (j != size) {
                    valuesList = valuesList + passedList.get(j).toString().trim() + ",";
                } else {
                    break;
                }
            }
            valuesList = valuesList.substring(0, valuesList.length() - 1);
            String listName = "listName" + i;
            mapList.put(listName, valuesList);
            if (maxIndex < size) {
                startIndex = startIndex + count;
                maxIndex = maxIndex + count;
            }
        }
        return mapList;
    }
}
