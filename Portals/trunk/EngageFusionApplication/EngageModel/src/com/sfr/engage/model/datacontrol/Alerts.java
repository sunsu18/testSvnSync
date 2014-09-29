package com.sfr.engage.model.datacontrol;

import com.sfr.engage.core.AlertsSubscribeRequest;
import com.sfr.engage.core.AlertsSubscribeResponse;
import com.sfr.engage.core.AlertsUnsubscribeRequest;
import com.sfr.engage.core.AlertsUnsubscribeResponse;
import com.sfr.engage.services.core.dao.AlertsDao;

public class Alerts {
    public Alerts() {
        super();
    }

    public AlertsSubscribeResponse subscribeAlerts(AlertsSubscribeRequest subscribeRequest) {
        AlertsDao alertDao = new AlertsDao();
        return alertDao.subscribeAlerts(subscribeRequest);
    }

    public AlertsUnsubscribeResponse unsubscribeAlerts(AlertsUnsubscribeRequest unsubscribeRequest) {
        AlertsDao alertDao = new AlertsDao();
        return alertDao.unsubscribeAlerts(unsubscribeRequest);
    }
}
