package com.sfr.engage.util;

import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

public class MyPageListener implements PagePhaseListener {
    public MyPageListener() {
        super();
    }

    public void afterPhase(PagePhaseEvent pagePhaseEvent) {
    }

    public void beforePhase(PagePhaseEvent pagePhaseEvent) {
        System.out.println("Before phase");
    }
}
