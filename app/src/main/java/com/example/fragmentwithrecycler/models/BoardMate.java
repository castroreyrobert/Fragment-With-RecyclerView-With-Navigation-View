package com.example.fragmentwithrecycler.models;


import com.example.fragmentwithrecycler.R;

public class BoardMate {
    public static final String TAG = "Boardmate";


    private long mId;
    private String mName;
    private String mAddress;
    private String mNumber;
    private double mPayable;
    private String mStatus;

  //  public enum Status{FULLYPAID, INITIALLYPAID, UNPAID}

    public BoardMate(){}

    public BoardMate(long id, String name, String address, String number, double payable, String status){
        this.mId = id;
        this.mName = name;
        this.mAddress = address;
        this.mNumber = number;
        this.mPayable = payable;
        this.mStatus = status;
    }

    public String getmAddress() {
        return mAddress;
    }

    public long getmId() {
        return mId;
    }

    public String getmName() {
        return mName;
    }

    public String getmNumber() {
        return mNumber;
    }

    public double getmPayable() {
        return mPayable;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public void setmPayable(double mPayable) {
        this.mPayable = mPayable;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String toString(){
        return "ID: " + mId + " Name: " + mName + " Address: " + mAddress + " Number: " + mNumber
                + " Payable: " + mPayable + " Status: " + mStatus;
    }

   /* public int getAssociatedDrawable(){
        return categoryToDrawable(mStatus);
    }
    public static int categoryToDrawable(Status boardmateStatus){
        switch (boardmateStatus){
            case FULLYPAID:
                return R.drawable.personal;
            case INITIALLYPAID:
                return R.drawable.technical;
            case UNPAID:
                return R.drawable.home;
        }
        return R.drawable.android;
    }*/
}
