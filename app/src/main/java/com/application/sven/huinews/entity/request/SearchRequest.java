package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/25
 * effect:
 */
public class SearchRequest extends BaseRequest {
    private String keywords;
    private String type; //小视频-video  //电影->film  电视剧->tvplay
    private int limit;
    private int page;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
