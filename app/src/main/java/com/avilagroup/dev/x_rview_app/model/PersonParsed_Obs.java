package com.avilagroup.dev.x_rview_app.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.avilagroup.dev.x_rview_app.BR;

import java.util.Random;

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

public class PersonParsed_Obs extends BaseObservable
        implements Parcelable {
    private String gender = "female";
    private int age = 0;
    private String firstName = "firstName";
    private String lastName = "lastName";
    private static final short LOADING_SHORT = 1000;
    private static final String[] GEN_LIST = {"female", "male"};

    /**
     * CONSTRUCTORS - both the normal obj init, and Parcelable req'd
     */
    public PersonParsed_Obs(String lastN, String firstN){
        this(lastN, firstN, new Random().nextInt(100));
    }
    public PersonParsed_Obs(String lastN, String firstN, int age){
//        String[] gen = {"female", "male"};
//        String gender = gen[new Random().nextInt(gen.length)];
        this(lastN, firstN, age, GEN_LIST[new Random().nextInt(GEN_LIST.length)]);
    }
    public PersonParsed_Obs(String lastN, String firstN, int age, String gender){
        this.firstName = firstN;
        this.lastName = lastN;
        this.age = age;
        this.gender = gender;
    }

    /**
     * PARCELABLE - these are Parcelable spec methods
     *              * PersonParsed_Obs(Parcel in)
     *              * Creator<PersonParsed_Obs> CREATOR
     *              * describeContents()
     *              * writeToParcel(Parcel dest, int flags)
     * @param in    - parcel to read. it's how it gets sent here
     */
    protected PersonParsed_Obs(Parcel in) {
        gender = in.readString();
        age = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
    }

    /**
     * Android creates obj to go from ser<->obj using this, either
     * individual or arrays - as needed
     * auto-gen
     */
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

    /**
     * Actual serialization - see ref link above. Indicates reading back should
     *              - follow order given here.
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gender);
        dest.writeInt(age);
        dest.writeString(firstName);
        dest.writeString(lastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * GET/SETers
     * @return
     */
    @Bindable
    public String getFirstname() {
        return this.firstName;
    }
    public void setFirstname(String firstname) {
        this.firstName = firstname;
        notifyPropertyChanged(BR.firstname);
    }
    @Bindable
    public String getLastname() {
        return this.lastName;
    }
    public void setLastname(String lastname) {
        this.lastName = lastname;
        notifyPropertyChanged(BR.lastname);
    }
    @Bindable
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }
    @Bindable
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

}
