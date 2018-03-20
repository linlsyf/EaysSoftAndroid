package com.example.choose;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;

import com.easysoft.costumes.R;
import com.example.choose.utils.ImageUtils;

/**
 * 装入UpLoadFragment的Activity<br>
 * 这里写死一个Fragment实际场景有多个Frament,区别大同小异
 */
public class ChooseFragmentActivity extends BaseActivity {
	
	public static String key_path="path";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_fragment);
		showPictureDailog();
	}
	@Override
	public void onBackPressed() {
		finish();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String imagePath = "";
		if(requestCode == SELECT_IMAGE_RESULT_CODE && resultCode== RESULT_OK){
			if(data != null && data.getData() != null){//有数据返回直接使用返回的图片地址
				imagePath = ImageUtils.getFilePathByFileUri(this, data.getData());
			}else{//无数据使用指定的图片路径
				imagePath = mImagePath;
			}
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(key_path, imagePath);
			setResult(RESULT_OK,intent);//设置返回数据
			finish();
		}
	}
}
