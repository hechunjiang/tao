package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/6/4
 * effect:
 */
public class AdsRequest extends BaseRequest {
    private String user_agent; // 浏览器UA
    private String width;//设备宽
    private String height;//设备高
    private String mobile_brand;//设备品牌
    private String mobile_model;//设备型号
    private String mobile_version;//设备操作系统版本号
    private String ip;//客户端IP
    private int page;// 分页参数  页码
    private int network_type;// 网络类型
    private String position;// 广告位置

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
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

    public String getMobile_brand() {
        return mobile_brand;
    }

    public void setMobile_brand(String mobile_brand) {
        this.mobile_brand = mobile_brand;
    }

    public String getMobile_model() {
        return mobile_model;
    }

    public void setMobile_model(String mobile_model) {
        this.mobile_model = mobile_model;
    }

    public String getMobile_version() {
        return mobile_version;
    }

    public void setMobile_version(String mobile_version) {
        this.mobile_version = mobile_version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNetwork_type() {
        return network_type;
    }

    public void setNetwork_type(int network_type) {
        this.network_type = network_type;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "AdsRequest{" +
                "user_agent='" + user_agent + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", mobile_brand='" + mobile_brand + '\'' +
                ", mobile_model='" + mobile_model + '\'' +
                ", mobile_version='" + mobile_version + '\'' +
                ", ip='" + ip + '\'' +
                ", page=" + page +
                ", network_type=" + network_type +
                ", position='" + position + '\'' +
                '}';
    }
}
