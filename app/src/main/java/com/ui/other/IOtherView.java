package com.ui.other;

import com.core.base.IBaseView;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;
import com.ui.other.bean.WeatherMsg;

import java.util.List;
import java.util.Map;

public interface IOtherView extends IBaseView{

	 void updateSection(Section section);

	void updateItem(AddressItemBean imgBean);
	void showNews();

	void showApay();
}
