package com.avilagroup.dev.x_rview_app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by taghec on 29/09/2016.
 *
 * This model will mimic the Person class, w two exceptions:
 *      - Start using BaseObservable: this is for android's Data Binding features.
 *      - Make this implement Parcelable: customized 'serializable' implementation
 *          for use in android apps. The purpose is to be able to move data between
 *          Act's using .getExtras, .putExtras of the Intent method.
 *
 *      - Ideas came from various sources, but good ex:
 *          * https://stfalcon.com/en/blog/post/faster-android-apps-with-databinding
 *          * https://halfthought.wordpress.com/2016/03/23/2-way-data-binding-on-android
 *          * https://coderwall.com/p/vfbing/passing-objects-between-activities-in-android
 */

public class PersonParsed_Obs
        implements HumanInterface,Parcelable {
    protected PersonParsed_Obs(Parcel in) {
    }

    public static final Creator<PersonParsed_Obs> CREATOR = new Creator<PersonParsed_Obs>() {
        @Override
        public PersonParsed_Obs createFromParcel(Parcel in) {
            return new PersonParsed_Obs(in);
        }

        @Override
        public PersonParsed_Obs[] newArray(int size) {
            return new PersonParsed_Obs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public String getFirstname() {
        return null;
    }

    @Override
    public String getLastname() {
        return null;
    }

    @Override
    public void setFirstname(String firstname) {

    }

    @Override
    public void setLastname(String lastname) {

    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public void setAge(int age) {

    }

    @Override
    public void setGender(String gender) {

    }
}
