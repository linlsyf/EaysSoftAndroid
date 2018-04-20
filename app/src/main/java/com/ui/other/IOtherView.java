package com.ui.other;

import com.core.base.IBaseView;

import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;
import com.ui.other.bean.WeatherMsg;

import java.util.List;
import java.util.Map;

public interface IOtherView extends IBaseView{

	 void updateSection(Section section);

	void updateItem(AddressItemBean imgBean);
	void showNews();

	void showApay();
}
