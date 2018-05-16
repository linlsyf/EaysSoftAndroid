package com.core;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.core.base.GlobalConstants;
import com.core.db.greenDao.gen.DaoMaster;
import com.core.db.greenDao.gen.DaoSession;
import com.core.imge.ImageLoadUtils;
import com.easy.recycleview.recycleview.RecycleViewManage;
import com.easysoft.utils.lib.DebugUtlis.CrashHandler;
import com.mob.MobSDK;
import com.tencent.bugly.crashreport.CrashReport;
import android.support.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;
/**
 * 
 * @author ldh
 * 全局Application
 *  用于初始化局标识，地址端口
 *  初始化全局变量  尽量放置到GlobalConstants类
 *  
 * 
 */
public class CoreApplication extends Application {
	private static final String TAG = "Tinker.CoreApplication";

	public static CoreApplication instance;
	
	/**公有变量*/
	public static GlobalConstants globalConstants;

	private DaoMaster.DevOpenHelper mHelper;
	private SQLiteDatabase db;
	private DaoMaster mDaoMaster;
	private DaoSession mDaoSession;

	public boolean isDubug=false;
//	private ApplicationLike tinkerApplicationLike;
	@Override
	public void onCreate() {
		super.onCreate();
//		initTinkerPatch();
		instance = this;
		  JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
	         JPushInterface.init(this);     		// 初始化 JPush
	      // 是否为平板
			 initGlobalConstants();
			init();
		RecycleViewManage.getInStance().setIloadImage(ImageLoadUtils.getInStance());

	}
//	/**
//	 * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
//	 */
//	private void initTinkerPatch() {
//		// 我们可以从这里获得Tinker加载过程的信息
//		if (BuildConfig.TINKER_ENABLE) {
//			tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
//			// 初始化TinkerPatch SDK
//			TinkerPatch.init(
//					tinkerApplicationLike
////                new TinkerPatch.Builder(tinkerApplicationLike)
////                    .requestLoader(new OkHttp3Loader())
////                    .build()
//			)
//					.reflectPatchLibrary()
//					.setPatchRollbackOnScreenOff(true)
//					.setPatchRestartOnSrceenOff(true)
//					.setFetchPatchIntervalByHours(3)
//			;
//			// 获取当前的补丁版本
//			Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());
//
//			// fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
//			// 不同的是，会通过handler的方式去轮询
//			TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
//		}
//	}

	/**
	 * 
	 * <br>创建者：ldh
	 * <br>修改时间：2016年1月25日 上午9:09:38 
	 * <br>注释：初始化一些数据  如调试信息  jpush信息
	 */
	private void init() {
//
		 setDatabase();
		MobSDK.init(this,"2433f5d8b5a48","33927e698b643937655aa60604f7686e");
		CrashHandler handler = CrashHandler.getInstance();
		handler.init(getApplicationContext());
		CrashReport.initCrashReport(getApplicationContext(), "5efad75bba", true);
	}




	private void setDatabase() {
		// 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
		// 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
		// 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
		// 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
		mHelper = new DaoMaster.DevOpenHelper(this, "easy-db", null);
		db = mHelper.getWritableDatabase();
		// 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
		mDaoMaster = new DaoMaster(db);
		mDaoSession = mDaoMaster.newSession();
	}

//	@Override
	public void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//you must install multiDex whatever tinker is installed!
		MultiDex.install(base);
	}

	public DaoSession getDaoSession() {
		return mDaoSession;
	}

	public SQLiteDatabase getDb() {
		return db;
	}



	public static CoreApplication getInstance() {
		return instance;
	}
	public static CoreApplication getAppContext() {
		return instance;
	}
	

	/**
	 * 简化Application代码
	 */
	public void initGlobalConstants() {
		//初始化全局变量
		 globalConstants=	GlobalConstants.getInstance();
		globalConstants.setApplicationContext(
				this.getApplicationContext());
		globalConstants.initSystemData();

		globalConstants.setDevDensityVal(
				this.getResources().getDisplayMetrics().density);

		globalConstants.istabandTwoClomnstyle = false;


		
	}
	



	




}
