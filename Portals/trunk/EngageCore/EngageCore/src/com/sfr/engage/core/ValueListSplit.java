package com.sfr.engage.core;


import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ValueListSplit implements Serializable {
    @SuppressWarnings("compatibility")    
    private Map<String,String> mapList; 
    private int dividedValue;
    private int loopValue;
    private static final long serialVersionUID = 1L;
    public ValueListSplit() {
        super();
    }
    
    public Map<String,String> callValueList(int size,List<String> passedList) {
        mapList = new HashMap<String,String>();
        dividedValue=size/150;
        loopValue=dividedValue+1;
        int count=150;
        int startIndex=0;
        int maxIndex=150;
          for(int i=0;i<loopValue;i++) {
             String valuesList="";
                for(int j=startIndex;j<maxIndex;j++) { 
                        if(j!=size)
                        {
                        valuesList=valuesList+passedList.get(j).toString().trim()+",";
                        }else {
                             break;
                             }
                        }
                valuesList=valuesList.substring(0, valuesList.length()-1);                            
                String listName="listName"+i;
                mapList.put(listName, valuesList);  
                 if(maxIndex<size)
                {                        
                startIndex=startIndex+count;
                maxIndex=maxIndex+count;
                }
            }        
        
        return mapList;
    }
}
