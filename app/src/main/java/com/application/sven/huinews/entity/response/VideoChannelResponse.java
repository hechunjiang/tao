package com.application.sven.huinews.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/15
 * effect:
 */
public class VideoChannelResponse extends BaseResponse implements Serializable {

    private ChannelData data;

    public ChannelData getData() {
        return data;
    }

    public void setData(ChannelData data) {
        this.data = data;
    }

    public static class ChannelData implements Serializable {

        private List<VBean> v;
        private List<MBean> m;
        private List<BBean> b;

        public List<VBean> getV() {
            return v;
        }

        public void setV(List<VBean> v) {
            this.v = v;
        }

        public List<MBean> getM() {
            return m;
        }

        public void setM(List<MBean> m) {
            this.m = m;
        }

        public List<BBean> getB() {
            return b;
        }

        public void setB(List<BBean> b) {
            this.b = b;
        }

        public static class VBean implements Serializable {
            /**
             * id : 1
             * name : 精选
             * temp_type : transverse
             * type : 1
             */

            private int id;
            private String name;
            private String temp_type;
            private int type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTemp_type() {
                return temp_type;
            }

            public void setTemp_type(String temp_type) {
                this.temp_type = temp_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class MBean implements Serializable {
            /**
             * id : 8
             * name : 推荐
             * temp_type : transverse
             * type : 2
             */

            private int id;
            private String name;
            private String temp_type;
            private int type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTemp_type() {
                return temp_type;
            }

            public void setTemp_type(String temp_type) {
                this.temp_type = temp_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }


        public static class BBean implements Serializable {
            private int id;
            private String name;
            private String temp_type;
            private int type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTemp_type() {
                return temp_type;
            }

            public void setTemp_type(String temp_type) {
                this.temp_type = temp_type;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }

    @Override
    public String toString() {
        return "VideoChannelResponse{" +
                "data=" + data +
                '}';
    }
}
