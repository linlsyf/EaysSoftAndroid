
package com.business;

import android.content.Context;

import com.business.login.User;
import com.core.base.BaseUiInterface;
import com.core.base.GlobalConstants;
import com.core.utils.system.BroadcastUtils;


/**
 * <br>创建者：ldh
 * <br>修改时间：2015-5-25 下午6:02:11
 * <br>作用：消息广播与通讯协议，常量定义的类
 */
public class BusinessBroadcastUtils {
	
	/**当前为最新版本，不需要更新*/
	public final static String IS_NEW_VERSION			= "is_new_version";
	/**有最新版本，需要更新*/
	public final static String HAS_NEW_VERSION			= "has_new_version";
	public static final String HTTP_ERROR ="http_error" ;
	public static String TYPE_LOCAL_REGISTER="local_register";
	public static String TYPE_LOCAL_REGISTER_SUCCESS_AUTOLOGIN="register_sucess_autologin";
    /**登录id*/
	public static String USER_VALUE_LOGIN_ID;
	public static String USER_VALUE_PWD;
	/**用户id*/
	public static String USER_VALUE_USER_ID;
	public static String STRING_LOGIN_USER_ID="login_user_id";
	public static String STRING_LOGIN_USER_PWD="login_user_pwd";
	public static String STRING_LOGIN_ID="login_login_id";
	public static User loginUser;

	/**
	 * <br>创建者：ldh
	 * <br>时间：2015-6-11 下午5:30:06
	 * <br>注释： 发送广播给UI层
	 * @param context 上下文
	 * @param type 消息类型
	 * @param obj 传输对象
	 */
	public static void sendBroadcast(Context context,String type,Object obj){
		BroadcastUtils.sendBroadCast(context, GlobalConstants.getInstance().getBroadCastReceiverActionName(), 
								BaseUiInterface.MSG_TYPE, BaseUiInterface.MSG_MODE, type, obj);
	}
	/**保存pdf 批注 发送过来的广播*/
	public static String  TYPE_SHOPCAR_LIST="com.ui.car.TabFragment4.list";

	
	//========平板主界面======
	/**homeGroupActivity 点击返回按钮事件*/
	public static String  TYPE_homeGroup_activity_onBackPressed="HomeGroup_activity_onBackPressed";
	/**homeGroupActivity 点击返回按钮事件*/
	public static String  TYPE_System_activity_onBackPressed="System_activity_onBackPressed";
	/**homeGroupActivity   显示会议日历*/
	public static String  TYPE_System_Show_ScheDuleActivity="System_Show_ScheDuleActivity";
	
	//========文档详情界面======
	/**homeGroupActivity 点击返回会议日历事件*/
	public static String  TYPE_System_DetailsPadViewActivity_onBackPressed_Schedule="System_DetailsPadViewActivity_onBackPressed_Schedule";
	/**弹出参加意见框*/
	public static String  TYPE_System_DetailsPadViewActivity_MeetingConfirm="System_DetailsPadViewActivity_MeetingConfirm";
	/**homeGroupActivity 点击返回按钮事件*/
	public static String  TYPE_DetailsPadViewActivity_complete="DetailsPadViewActivity_complete";
	/**主界面退出*/
	public static String  TYPE_HomeActivity_exit="HomeActivity_exit";
	/**公文列表刷新*/
	public static String  TYPE_DataPullToRefreshList_refresh="DetailsPadViewActivity_request_refresh";
	/**导航界面刷新*/
	public static String  TYPE_MenuSubActivity_requset_Item_Num_refresh="MenuSubActivity_requset_Item_Num_refresh";
	/**手机导航刷新*/
	public static String  TYPE_OfficeFragment_requset_Item_Num_refresh="OfficeFragment_requset_Item_Num_refresh";
	
	/**手机导航刷新*/
	public static String  TYPE_HometabFragment_requset_Item_Num_refresh="HometabFragment_requset_Item_Num_refresh";
	
	/**打开通讯录界面*/
	public static String  TYPE_MenuSubActivity_open_TXL="MenuSubActivity_open_TXL";
	
	//========日程管理======
	/**刷新编辑和新加后的个人日程界面*/
	public static String  TYPE_PersonScheduleActivity_refresh="PersonScheduleActivity_refresh";
	/**刷新日程 列表界面*/
	public static String  TYPE_PersonSchduleListFragment_refresh="PersonSchduleListFragment_refresh";
	//========公文运转======
	/**找人代办后刷新*/
	public static String  TYPE_DocTransfer_selectUserAgent_refresh="DocTransfer_selectUserAgent_refresh";
	public static String  TYPE_DailyWork_add_group_sucess="DailyWork_add_group_sucess";
	//========通讯录======
	public static String  TYPE_DailyWorkAdress_deletegroup_sucess_refresh="DailyWorkAdress_deletegroup_sucess_refresh";
	/**关闭群组管理界面*/
	public static String  TYPE_DailyWorkAdress_GroupDetails_finish="DailyWorkAdress_GroupDetails_finish";
	/**关闭所有界面*/
	public static String  TYPE_system_all_Activity_finish="system_all_Activity_finish";
	/**邮件选择界面*/
	public static String  TYPE_MailMainViewActivity_showPadMailContact="MailMainViewActivity_showPadMailContact";
	public static String  TYPE_MailMainViewActivity_resumePadMailContact="_MailMainViewActivity_resumePadMailContact";
	/**后台检测被杀删除缓存文件*/
	public static String  Type_AlwayonServiceDeleteTempFile="AlwayonServiceDeleteTempFile";
	/**完成刷新*/
	public static String  Type_FlowSelectViewController_onresult="FlowSelectViewController_onresult";
	/**流程运转完成刷新*/
	public static String  Type_FlowSelectViewController_sucess="FlowSelectViewController_sucess";
	/**流程运转完成 详情界面关闭并请求刷新*/
	public static String  Type_DetailsPadViewActivity_flowfinish_request_refresh="Type_DetailsPadViewActivity_flowfinish_request_refresh";
	/**完成刷新*/
	public static String  Type_HometabActivity_gridview_item_click="HometabActivity_gridview_item_click";
	/**开始发送显示进度条*/
	public static String  Type_Local_MyHttpUtils_ShowProcess="Type_Local_MyHttpUtils_ShowProcess";
	/**关闭进度条*/
	public static String  Type_Local_MyHttpUtils_Close_Process="Local_MyHttpUtils_Close_Process";
	/**发送服务器返回成功*/
	public static String  Type_Local_MyHttpUtils_Server_Response_Sucess="Local_MyHttpUtils_Server_Response_Sucess";
	/**发送服务器返回失败*/
	public static String  Type_Local_MyHttpUtils_Server_Response_Fail="Local_MyHttpUtils_Server_Response_Fail";
	
	
	/**跳转viewpage 主界面*/
	public static String  Type_Local_HOME_PAGE_CHANGE="Type_Local_HOME_PAGE_CHANGE";


}
