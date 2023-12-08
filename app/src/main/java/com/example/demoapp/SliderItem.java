package com.example.demoapp;

import java.io.Serializable;

public class SliderItem implements Serializable {

    // Ở đây bạn có thể sử dụng String variable để đến cửa hàng url
    // Nếu bạn muốn load ảnh từ internet
     int resourceId;

    public SliderItem(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
