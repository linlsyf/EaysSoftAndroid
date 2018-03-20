package com.core;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.core.DebugUtlis.CrashHandler;
import com.core.base.GlobalConstants;
import com.core.db.greenDao.gen.DaoMaster;
import com.core.db.greenDao.gen.DaoSession;
import com.mob.MobSDK;

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


	public static CoreApplication instance;
	
	/**公有变量*/
	public static GlobalConstants globalConstants;

	private DaoMaster.DevOpenHelper mHelper;
	private SQLiteDatabase db;
	private DaoMaster mDaoMaster;
	private DaoSession mDaoSession;

	public boolean isDubug=false;
	@Override
	public void onCreate() {

		super.onCreate();

		instance = this;
		
		  JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
	         JPushInterface.init(this);     		// 初始化 JPush
	      // 是否为平板
			 initGlobalConstants();
			init();
	}




	/**
	 * 
	 * <br>创建者：ldh
	 * <br>修改时间：2016年1月25日 上午9:09:38 
	 * <br>注释：初始化一些数据  如调试信息  jpush信息
	 */
	private void init() {

		// 初始化log检测
		String appId = "900010130"; // 上Bugly(bugly.qq.com)注册产品获取的AppId

		boolean isDebug = false; // true代表App处于调试阶段，false代表App发布阶段
		// // 是否开启调试模式，调试模式下会输出'CrashReport'tag的logcat日志

		// 初始化jpush 用于接收推送以及杀死后会出现执行onCreate 的删除方法
		// JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		// JPushInterface.init(this); // 初始化 JPush

		// initGlobalConstants 方法以及设置好临时文件存放位置
		String TempAttachSaveFullPath = GlobalConstants.getInstance().getAppDocumentHomePath() + GlobalConstants.temp;
//		fileIOManager.DeleteDirectory(TempAttachSaveFullPath);


//		DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//		Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//		daoSession = new DaoMaster(db).newSession();

		 setDatabase();
		MobSDK.init(this,"2433f5d8b5a48","33927e698b643937655aa60604f7686e");

//		MobAPI.initSDK(this, "2433f5d8b5a48");
//		SMSSDK.initSDK(this, "11b137e3a5e00", "632f0c9cf1cd683806146758ef8784e3");
		CrashHandler handler = CrashHandler.getInstance();
		handler.init(getApplicationContext());

//		SpeechUtility.createUtility(getApplicationContext(), "appid="+getString(R.string.app_id));
		initNews();
	
	}

	private void initNews() {

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
