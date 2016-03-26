package com.makman.rivertracker;

/**
 * Created by sam on 3/21/16.
 */
public class River {

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

    public static class PictureBean {
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

        public static class RiverPicture {
            private String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
