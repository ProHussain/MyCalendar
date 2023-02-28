package com.example.mycalendar.model;

import com.google.gson.annotations.SerializedName;

public class Ads {
    @SerializedName("Fan_inter")
    private String fan_inter;

    @SerializedName("admob_pub")
    private String admob_pub;

    @SerializedName("admob_app_id")
    private String admob_app_id;

    @SerializedName("admob_banner")
    private String admob_banner;

    @SerializedName("admob_inter")
    private String admob_inter;

    @SerializedName("admob_AppOpen")
    private String admob_AppOpen;

    @SerializedName("admob_native")
    private String admob_native;

    @SerializedName("admob_reward")
    private String admob_reward;

    @SerializedName("ONESIGNAL_APP_ID")
    private String ONESIGNAL_APP_ID;

    public String getFan_inter() {
        return fan_inter;
    }

    public String getAdmob_pub() {
        return admob_pub;
    }

    public String getAdmob_app_id() {
        return admob_app_id;
    }

    public String getAdmob_banner() {
        return admob_banner;
    }

    public String getAdmob_inter() {
        return admob_inter;
    }

    public String getAdmob_AppOpen() {
        return admob_AppOpen;
    }

    public String getAdmob_native() {
        return admob_native;
    }

    public String getAdmob_reward() {
        return admob_reward;
    }

    public String getONESIGNAL_APP_ID() {
        return ONESIGNAL_APP_ID;
    }
}
