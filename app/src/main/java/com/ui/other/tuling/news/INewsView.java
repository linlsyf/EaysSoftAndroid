package com.ui.other.tuling.news;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.ui.other.tuling.entity.NewsEntity;


public interface INewsView extends IBaseView{

	 void initUI(Section section);
     void showItem(NewsEntity itemBean);

}
