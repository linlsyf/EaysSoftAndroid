package com.ui.message;

import com.core.recycleview.item.AddressItemBean;

/**
 * Created by ldh on 2017/5/11.
 * 个人信息卡片
 */

public class GoodsInfoBean extends AddressItemBean {
    private String id;
    private String name;
    private String imageId;
    private String imagUrl;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImagUrl() {
        return imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }


}
