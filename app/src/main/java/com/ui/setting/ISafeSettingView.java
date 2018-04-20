package com.ui.setting;

import com.core.base.IBaseView;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;


public interface ISafeSettingView extends IBaseView{

	 void initUI(Section section);

	void showUpdate();

	void logOut();
	void updateItem(AddressItemBean imgBean);

	void showNews();
}
