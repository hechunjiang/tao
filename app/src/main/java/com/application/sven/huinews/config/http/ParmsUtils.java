package com.application.sven.huinews.config.http;

import java.util.TreeMap;

/**
 * auther: sunfuyi
 * data: 2018/5/14
 * effect:
 */
public class ParmsUtils {

    public TreeMap<String, String> params;

    public ParmsUtils getPostBody(String key, String value) {
        if (this.params == null) {
            params = new TreeMap<>();
        }
        try {
            params.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
