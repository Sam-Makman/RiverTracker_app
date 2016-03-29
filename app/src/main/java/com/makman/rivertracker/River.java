package com.makman.rivertracker;

import android.os.Parcel;
import android.os.Parcelable;

public class River implements Parcelable {

    /**
     * id : ​3
     * name : E.F. Lewis
     * section : Sunset to Horseshoe
     * difficulty : IV
     * cfs : ​1000
     * details : null
     * state : WA
     * put_in : null
     * take_out : null
     * picture : {"picture":{"url":null}}
     * has_alert : true
     */

    private String id;
    private String name;
    private String section;
    private String difficulty;
    private String cfs;
    private String details;
    private String state;
    private String put_in;
    private String take_out;
    /**
     * picture : {"url":null}
     */

    private PictureBean picture;
    private boolean has_alert;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCfs() {
        return cfs;
    }

    public void setCfs(String cfs) {
        this.cfs = cfs;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPut_in() {
        return put_in;
    }

    public void setPut_in(String put_in) {
        this.put_in = put_in;
    }

    public String getTake_out() {
        return take_out;
    }

    public void setTake_out(String take_out) {
        this.take_out = take_out;
    }

    public PictureBean getPicture() {
        return picture;
    }

    public void setPicture(PictureBean picture) {
        this.picture = picture;
    }

    public boolean isHas_alert() {
        return has_alert;
    }

    public void setHas_alert(boolean has_alert) {
        this.has_alert = has_alert;
    }

    public static class PictureBean implements Parcelable {
        /**
         * url : null
         */

        private RiverPicture picture;

        public RiverPicture getPicture() {
            return picture;
        }

        public void setPicture(RiverPicture picture) {
            this.picture = picture;
        }

        public static class RiverPicture implements Parcelable {
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.url);
            }

            public RiverPicture() {
            }

            protected RiverPicture(Parcel in) {
                this.url = in.readString();
            }

            public static final Creator<RiverPicture> CREATOR = new Creator<RiverPicture>() {
                @Override
                public RiverPicture createFromParcel(Parcel source) {
                    return new RiverPicture(source);
                }

                @Override
                public RiverPicture[] newArray(int size) {
                    return new RiverPicture[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.picture, flags);
        }

        public PictureBean() {
        }

        protected PictureBean(Parcel in) {
            this.picture = in.readParcelable(RiverPicture.class.getClassLoader());
        }

        public static final Creator<PictureBean> CREATOR = new Creator<PictureBean>() {
            @Override
            public PictureBean createFromParcel(Parcel source) {
                return new PictureBean(source);
            }

            @Override
            public PictureBean[] newArray(int size) {
                return new PictureBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.section);
        dest.writeString(this.difficulty);
        dest.writeString(this.cfs);
        dest.writeString(this.details);
        dest.writeString(this.state);
        dest.writeString(this.put_in);
        dest.writeString(this.take_out);
        dest.writeParcelable(this.picture, flags);
        dest.writeByte(has_alert ? (byte) 1 : (byte) 0);
    }

    public River() {
    }

    protected River(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.section = in.readString();
        this.difficulty = in.readString();
        this.cfs = in.readString();
        this.details = in.readString();
        this.state = in.readString();
        this.put_in = in.readString();
        this.take_out = in.readString();
        this.picture = in.readParcelable(PictureBean.class.getClassLoader());
        this.has_alert = in.readByte() != 0;
    }

    public static final Parcelable.Creator<River> CREATOR = new Parcelable.Creator<River>() {
        @Override
        public River createFromParcel(Parcel source) {
            return new River(source);
        }

        @Override
        public River[] newArray(int size) {
            return new River[size];
        }
    };
}
