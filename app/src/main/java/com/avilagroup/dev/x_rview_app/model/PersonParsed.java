package com.avilagroup.dev.x_rview_app.model;

import android.os.Parcel;
import android.os.Parcelable;

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

public class PersonParsed
        implements HumanInterface,Parcelable {
    private String gender;
    private int age;
    private String firstName;
    private String lastName;

    /**
     * CONSTRUCTORS - both the normal obj init, and Parcelable req'd
     */
    public PersonParsed(String lastN, String firstN){
        new PersonParsed(lastN, firstN, new Random().nextInt(100));
    }
    public PersonParsed(String lastN, String firstN, int age){
        String[] gen = {"female", "male"};
        String gender = gen[new Random().nextInt(gen.length)];
        new PersonParsed(lastN, firstN, age, gender);
    }
    public PersonParsed(String lastN, String firstN, int age, String gender){
        this.firstName = firstN;
        this.lastName = lastN;
        this.age = age;
        this.gender = gender;
    }

    /**
     * PARCELABLE - these are Parcelable spec methods
     *              * PersonParsed(Parcel in)
     *              * Creator<PersonParsed> CREATOR
     *              * describeContents()
     *              * writeToParcel(Parcel dest, int flags)
     * @param in    - parcel to read. it's how it gets sent here
     */
    protected PersonParsed(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.age = in.readInt();
        this.gender = in.readString();
    }

    /**
     * Android creates obj to go from ser<->obj using this, either
     * individual or arrays - as needed
     * auto-gen
     */
    public static final Creator<PersonParsed> CREATOR = new Creator<PersonParsed>() {
        @Override
        public PersonParsed createFromParcel(Parcel in) {
            return new PersonParsed(in);
        }

        @Override
        public PersonParsed[] newArray(int size) {
            return new PersonParsed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Actual serialization - see ref link above. Indicates reading back should
     *              - follow order given here.
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(age);
        dest.writeString(gender);
    }

    /**
     * GET/SETers
     * @return
     */
    @Override
    public String getFirstname() {
        return firstName;
    }
    @Override
    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    @Override
    public String getLastname() {
        return lastName;
    }
    @Override
    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    @Override
    public int getAge() {
        return age;
    }
    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getGender() {
        return gender;
    }
    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }
}
