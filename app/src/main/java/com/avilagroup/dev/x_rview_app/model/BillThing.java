package com.avilagroup.dev.x_rview_app.model;

/**
 * Created by taghec on 05/10/2016.
 */

public interface BillThing extends Thing {
    String getInvoice();
    long getExpirationDate();
    long getAmountDue();

    void setInvoice(String invoice);
    void setExpirationDate(long exp);
    void setAmountDue(long due);
}
