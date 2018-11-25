package com.sixin.police.market.bean.appdetailinfo.appintroduceinfo;

import com.lehand.adapter.entity.MultiItemEntity;
import com.sixin.police.market.bean.BaseBean;

import java.util.List;

/**
 * 应用截图的实体类
 * */
public class PreImageEntity extends AppIntroduceBase {
    //TODO 测试实体类中添加额外内容会不会导致Gson解析失败

    private String title;

    private List<String> imgs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    @Override
    public int getItemType() {
        return MultiTypeConfig.ITEM_TYPE_IMAGES;
    }
}
