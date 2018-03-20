package com.ui.message;

import com.business.bean.Goods;
import com.core.base.IBaseView;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;

public interface IGoodsView extends IBaseView{

	void showUi(Section nextSection);
	void showItem(AddressItemBean imgBean);
	void toOrder(Goods goods);




}
