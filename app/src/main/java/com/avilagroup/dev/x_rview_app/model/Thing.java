package com.avilagroup.dev.x_rview_app.model;

/**
 * Created by taghec on 05/10/2016.
 */

public interface Thing {
    int getId();
    String getName();
    short getTangible();
    long getCreatedDate();

    void setId(int id);
    void setName(String name);
    void setTangible(short tangible);
    void setCreatedDate(long date);
}
