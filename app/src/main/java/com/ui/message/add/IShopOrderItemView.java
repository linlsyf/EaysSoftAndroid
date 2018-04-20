package com.ui.message.add;

import com.core.base.IBaseView;
import com.easy.recycleview.recycleview.item.AddressItemBean;
import com.easy.recycleview.recycleview.sectionview.Section;


public interface IShopOrderItemView extends IBaseView{

	void showUi(Section nextSection);
	void selectImg();
	void inputItem(AddressItemBean imgBean);

	void updateItem(AddressItemBean imgBean);

	void getImgeDbId(String id);

	void addSucess();
	void addGoodsSucess();
}
