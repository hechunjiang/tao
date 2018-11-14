package com.application.sven.huinews.main.preemption.entity;

import java.io.Serializable;

/**
 * Created by sfy. on 2018/5/9 0009.
 */

public class MovieCount implements Serializable {
    public int count;
    public boolean isSelected;

    public MovieCount(int count, boolean isSelected) {
        this.count = count;
        this.isSelected = isSelected;
    }
}
