package com.ui.video;

import com.business.bean.VideoBussinessItem;
import com.core.base.IBaseView;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;


public interface IVideoHomeView extends IBaseView{

	 void initUI(Section section);
     void showItem(VideoBussinessItem itemBean);

}
