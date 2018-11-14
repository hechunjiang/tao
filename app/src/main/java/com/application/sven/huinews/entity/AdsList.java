package com.application.sven.huinews.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/4
 * effect:
 */
public class AdsList implements Serializable {

    /**
     * ad_type : baidu
     * picture_model : single
     * img :
     * c_url :
     * width :
     * height :
     * title :
     * desc :
     * report : {"imp":[],"clk":[]}
     * img_arr : []
     * in_position : 1
     * is_redpack : 1
     */

    private String ad_type;
    private String picture_model;
    private String img;
    private String c_url;
    private String width;
    private String height;
    private String title;
    private String desc;
    private ReportBean report;
    private int in_position;
    private int is_redpack;
    private String[] img_arr;


    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }

    public String getPicture_model() {
        return picture_model;
    }

    public void setPicture_model(String picture_model) {
        this.picture_model = picture_model;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getC_url() {
        return c_url;
    }

    public void setC_url(String c_url) {
        this.c_url = c_url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ReportBean getReport() {
        return report;
    }

    public void setReport(ReportBean report) {
        this.report = report;
    }

    public int getIn_position() {
        return in_position;
    }

    public void setIn_position(int in_position) {
        this.in_position = in_position;
    }

    public int getIs_redpack() {
        return is_redpack;
    }

    public void setIs_redpack(int is_redpack) {
        this.is_redpack = is_redpack;
    }

    public String[] getImg_arr() {
        return img_arr;
    }

    public void setImg_arr(String[] img_arr) {
        this.img_arr = img_arr;
    }

    public static class ReportBean {
        private String[] imp;
        private String[] clk;

        public String[] getImp() {
            return imp;
        }

        public void setImp(String[] imp) {
            this.imp = imp;
        }

        public String[] getClk() {
            return clk;
        }

        public void setClk(String[] clk) {
            this.clk = clk;
        }

        @Override
        public String toString() {
            return "ReportBean{" +
                    "imp=" + Arrays.toString(imp) +
                    ", clk=" + Arrays.toString(clk) +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AdsList{" +
                "ad_type='" + ad_type + '\'' +
                ", picture_model='" + picture_model + '\'' +
                ", img='" + img + '\'' +
                ", c_url='" + c_url + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", report=" + report +
                ", in_position=" + in_position +
                ", is_redpack=" + is_redpack +
                ", img_arr=" + Arrays.toString(img_arr) +
                '}';
    }
}
