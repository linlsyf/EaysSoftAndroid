package com.ui.car;

import android.content.Context;

import com.business.bean.Goods;
import com.business.bean.ShopOrder;
import com.core.base.IBaseView;
import com.core.recycleview.sectionview.Section;

public interface IShopOrderListView  extends IBaseView{

	 void list(Section nextSection);
	 void showOrder(ShopOrder order,Goods goods);
	 
	 Context getContext();
	 
}
