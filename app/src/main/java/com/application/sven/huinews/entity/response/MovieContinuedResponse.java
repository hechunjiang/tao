package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/5/31
 * effect:
 */
public class MovieContinuedResponse extends BaseResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data implements Serializable {

        /**
         * id : 59
         * m_name : 美国队长3
         * m_img : http://www.nr6h.cn/uploads/20180529/f5ed4fbd3ae04bd947f76034e340d315.png
         * play_id : 421
         * play_time : 100
         */

        private int id;
        private String m_name;
        private String m_img;
        private int play_id;
        private int play_time;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getM_name() {
            return m_name;
        }

        public void setM_name(String m_name) {
            this.m_name = m_name;
        }

        public String getM_img() {
            return m_img;
        }

        public void setM_img(String m_img) {
            this.m_img = m_img;
        }

        public int getPlay_id() {
            return play_id;
        }

        public void setPlay_id(int play_id) {
            this.play_id = play_id;
        }

        public int getPlay_time() {
            return play_time;
        }

        public void setPlay_time(int play_time) {
            this.play_time = play_time;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", m_name='" + m_name + '\'' +
                    ", m_img='" + m_img + '\'' +
                    ", play_id=" + play_id +
                    ", play_time=" + play_time +
                    '}';
        }
    }
}
