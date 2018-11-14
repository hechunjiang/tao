package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class MovieMoreListRequest extends BaseRequest {
    private String keywords;
    private int limit;
    private int page;
    private int buId;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getBuId() {
        return buId;
    }

    public void setBuId(int buId) {
        this.buId = buId;
    }
}
