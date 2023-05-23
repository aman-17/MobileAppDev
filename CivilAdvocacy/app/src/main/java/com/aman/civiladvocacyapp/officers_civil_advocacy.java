package com.aman.civiladvocacyapp;

import android.os.Parcel;
import android.os.Parcelable;

public class officers_civil_advocacy implements Parcelable {
    String designation, organizingParty,name,Address,emailaddress;
    String phoneno,Websitelink,image,UserAddress,pictureurl,websitename;
    String facebooklink,youtubelink,twitterlink;



    public String getOrganizingParty() {
        return organizingParty;
    }

    public void setOrganizingParty(String organizingParty) {
        this.organizingParty = organizingParty;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }



    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getWebsitelink() {
        return Websitelink;
    }

    public void setWebsitelink(String websitelink) {
        this.Websitelink = websitelink;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getTwitterlink() {
        return twitterlink;
    }

    public void setTwitterlink(String twitterlink) {
        this.twitterlink = twitterlink;
    }

    public String getFacebooklink() {
        return facebooklink;
    }

    public void setFacebooklink(String facebooklink) {
        this.facebooklink = facebooklink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }


    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }
    public void setUrl(String urls) {
        this.websitename = urls;
    }

    public String getPictureurl() {
        return pictureurl;
    }
    public String getUrl() {
        return websitename;
    }
    public officers_civil_advocacy() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.name);
        dest.writeString(this.Address);
        dest.writeString(this.designation);
        dest.writeString(this.organizingParty);

        dest.writeString(this.Websitelink);
        dest.writeString(this.image);
        dest.writeString(this.emailaddress);
        dest.writeString(this.phoneno);

        dest.writeString(this.twitterlink);
        dest.writeString(this.youtubelink);
        dest.writeString(this.UserAddress);
        dest.writeString(this.facebooklink);
    }

    protected officers_civil_advocacy(Parcel in) {

        this.name = in.readString();
        this.Address = in.readString();
        this.designation = in.readString();
        this.organizingParty = in.readString();

        this.Websitelink = in.readString();
        this.image = in.readString();
        this.emailaddress = in.readString();
        this.phoneno = in.readString();

        this.twitterlink = in.readString();
        this.youtubelink = in.readString();
        this.UserAddress = in.readString();
        this.facebooklink = in.readString();
    }

    public static final Creator<officers_civil_advocacy> CREATOR = new Creator<officers_civil_advocacy>() {
        @Override
        public officers_civil_advocacy createFromParcel(Parcel source) {
            return new officers_civil_advocacy(source);
        }

        @Override
        public officers_civil_advocacy[] newArray(int size) {
            return new officers_civil_advocacy[size];
        }
    };
    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        this.UserAddress = userAddress;
    }
}
