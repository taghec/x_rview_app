package com.avilagroup.dev.x_rview_app.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import com.avilagroup.dev.x_rview_app.BR;

import java.util.Date;
import java.util.Random;

/**
 * Created by taghec on 05/10/2016.
 */

public class BillParsedObs
        extends BaseObservable
        implements BillThing,Parcelable {
    private int billId;
    private String billName;
    private short isTangible;
    private String invoiceNo;
    private long dateCreated;
    private long dateExpires;
    private long amtDue;
    private final static long ONEDAY_MIL = 24*60*60*1000;

    /**
     * CONSTRUCTORS
     */
    public BillParsedObs(String billName, long billAmt){
        this(billName, billAmt, "ABC" + (new Random().nextInt(1000)));
    }
    public BillParsedObs(String billName, long billAmt, String invNo) {
        this(billName, billAmt, invNo, new Random().nextInt(500)+200);
    }
    public BillParsedObs(String billName, long billAmt, String invNo, int billId){
        this.billId = billId;
        this.billName = billName;
        this.amtDue = billAmt;
        this.invoiceNo = invNo;

        this.isTangible = 1;
        this.dateCreated = new Date().getTime();
        this.dateExpires = dateCreated + (new Random().nextInt(30))*ONEDAY_MIL;
    }

    /**
     * Parcelable req's
     * @param in
     *
     * -----------------------------------
     */
    protected BillParsedObs(Parcel in) {
        this.billId = in.readInt();
        this.billName = in.readString();
        this.amtDue = in.readLong();
        this.invoiceNo = in.readString();

        this.isTangible = (short)in.readInt();
        this.dateCreated = in.readLong();
        this.dateExpires = in.readLong();
    }
    public static final Creator<BillParsedObs> CREATOR = new Creator<BillParsedObs>() {
        @Override
        public BillParsedObs createFromParcel(Parcel in) {
            return new BillParsedObs(in);
        }

        @Override
        public BillParsedObs[] newArray(int size) {
            return new BillParsedObs[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(billId);
        dest.writeString(billName);
        dest.writeLong(amtDue);
        dest.writeString(invoiceNo);

        dest.writeInt(isTangible);
        dest.writeLong(dateCreated);
        dest.writeLong(dateExpires);
    }


    /**
     * GETTERS/SETTERS
     * ---------------------------------------
     */
    @Override
    public int getId() {
        return this.billId;
    }

    @Override
    @Bindable
    public String getName() {
        return this.billName;
    }

    @Override
    public short getTangible() {
        return this.isTangible;
    }

    @Override
    @Bindable
    public long getCreatedDate() {
        return this.dateCreated;
    }

    @Override
    @Bindable
    public String getInvoice() {
        return this.invoiceNo;
    }

    @Override
    @Bindable
    public long getExpirationDate() {
        return this.dateExpires;
    }

    @Override
    @Bindable
    public long getAmountDue() {
        return this.amtDue;
    }

    @Override
    public void setId(int id) {
        // not an observ - should not change once set
        this.billId = id;
    }

    @Override
    public void setName(String billName) {
        this.billName = billName;
        notifyPropertyChanged(BR.name);
    }

    @Override
    public void setTangible(short tangible) {
        // will not change once set
        this.isTangible = tangible;
    }

    @Override
    public void setCreatedDate(long date) {
        this.dateCreated = date;
        notifyPropertyChanged(BR.createdDate);
    }

    @Override
    public void setInvoice(String invoice) {
        this.invoiceNo = invoice;
        notifyPropertyChanged(BR.invoice);
    }

    @Override
    public void setExpirationDate(long exp) {
        this.dateExpires = exp;
        notifyPropertyChanged(BR.expirationDate);
    }

    @Override
    public void setAmountDue(long due) {
        this.amtDue = due;
        notifyPropertyChanged(BR.amountDue);
    }

}
