package com.ui.message.add;

import android.content.Context;

import com.business.bean.ShopOrder;
import com.core.base.IBaseView;
import com.core.recycleview.item.AddressItemBean;
import com.core.recycleview.sectionview.Section;

public interface IShopOrderItemView extends IBaseView{

	void showUi(Section nextSection);
	void selectImg();
	void inputItem(AddressItemBean imgBean);
	void updateItem(AddressItemBean imgBean);

	void getImgeDbId(String id);


	void addSucess();
	void addGoodsSucess();
}
