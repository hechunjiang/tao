package com.application.sven.huinews.utils.umeng.push;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class PushEntry implements Serializable {
    private String actionMethodParam;
    private String actionName;
    private String actionUrl;
    private String actionMethod;

    public String getActionMethodParam() {
        return actionMethodParam;
    }

    public void setActionMethodParam(String actionMethodParam) {
        this.actionMethodParam = actionMethodParam;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(String actionMethod) {
        this.actionMethod = actionMethod;
    }


    public static class PushMethodParm implements Serializable{

        /**
         * data : {"url":"http://www.baidu.com"}
         */

        private DataBean data;


        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * url : http://www.baidu.com
             */

            private Object o;


            public Object get0() {
                return o;
            }

            public void setO(Object o) {
                this.o = o;
            }
        }


        @Override
        public String toString() {
            return "PushMethodParm{" +
                    "data=" + data +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PushEntry{" +
                "actionMethodParam=" + actionMethodParam +
                ", actionName='" + actionName + '\'' +
                ", actionUrl='" + actionUrl + '\'' +
                ", actionMethod='" + actionMethod + '\'' +
                '}';
    }
}
