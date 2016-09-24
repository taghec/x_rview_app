package com.avilagroup.dev.x_rview_app.model;

/**
 * Created by taghec on 20/09/2016.
 */

public interface HumanInterface extends LivingEntity {
    String getFirstname();
    String getLastname();
    void setFirstname(String firstname);
    void setLastname(String lastname);
}
