package com.application.sven.huinews.entity.request;

/**
 * auther: sunfuyi
 * data: 2018/5/24
 * effect:
 */
public class VideoCollectionCancelRequest extends BaseRequest {
    private int type;
    private String rId;  //收藏id 删除多个使用英文“,”分割，如：1,2
    private String delType; //删除类型 all->全部 不传或者其他为选择删除

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getDelType() {
        return delType;
    }

    public void setDelType(String delType) {
        this.delType = delType;
    }

    @Override
    public String toString() {
        return "VideoCollectionCancelRequest{" +
                "type=" + type +
                ", rId='" + rId + '\'' +
                ", delType='" + delType + '\'' +
                '}';
    }
}
