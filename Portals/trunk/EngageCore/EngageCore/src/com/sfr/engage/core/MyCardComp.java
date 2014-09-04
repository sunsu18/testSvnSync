package com.sfr.engage.core;

import java.util.Comparator;

public class MyCardComp implements Comparator<CardInfo>{
    public MyCardComp() {
        super();
    }

    public int compare(CardInfo o1, CardInfo o2) {
        int c1 = Integer.parseInt(o1.getBlockAction());
                int c2 = Integer.parseInt(o2.getBlockAction());
                long embos1 = Long.parseLong(o1.getExternalCardID());
                long embos2 = Long.parseLong(o2.getExternalCardID());
                if(c1 > c2) {
                    return 1;
                }
                else if (c2 == c1)
                {
                    return o1.getExternalCardID().compareTo(o2.getExternalCardID());
                }
                else {
                    
//                    if(embos1 < embos2) {
                        return -1;
//                    }
//                    else {
//                        return 1;
//                    }
                }
       
    }
}
