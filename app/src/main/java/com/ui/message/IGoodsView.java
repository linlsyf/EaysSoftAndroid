package com.ui.message;

import com.business.bean.Goods;
import com.core.base.IBaseView;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;

public interface IGoodsView extends IBaseView{

	void showUi(Section nextSection);
	void showItem(AddressItemBean imgBean);
	void toOrder(Goods goods);

	void resetToolBar(boolean isAdmin);
}
