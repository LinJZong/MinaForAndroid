package com.android.mina.minaserver;
/**
 * 状态码类，返回信息以下包含状态码
 * @author linj
 *
 */
public class StatusCode {
   public static final String STATUS_SUCCESS="000";//本次请求传输成功
   /*登录接口返回码*/
   public static final String LOGIN_USERNAME_NULL="001";//用户名为空
   public static final String LOGIN_PASSWORD_NULL="002";//密码为空
   public static final String LOGIN_DEVICEID_NULL="003";//设备号为空
   public static final String LOGIN_USERINFO_ERROR="004";//用户名或密码错误
   public static final String LOGIN_UNBIND="005";//本次请求传输成功
   public static final String LOGIN_DEVIEDID_ERROR="006";//本次请求传输成功
   public static final String LOGIN_ORGAN_ERROR="007";//查询用户机构出错
   public static final String LOGIN_ORGAN_NULL="008";//该用户还未分配机构，请联系管理员
   public static final String LOGIN_DEVICE_LOCKED="009";//设备已被锁定
   public static final String LOGIN_USER_FORBID="010";//用户已被禁用
   
   /*绑定接口返回码*/
   public static final String Bind_USERNAME_NULL="011";//用户名为空
   public static final String Bind_PASSWORD_NULL="012";//密码为空
   public static final String Bind_DEVICEID_NULL="013";//设备号为空
   public static final String Bind_USERINFO_ERROR="014";//用户名或密码错误
   public static final String Bind_BINDCODE_ERROR="015";//绑定码错误
   public static final String Bind_BIND_ERROR="016";//设置设备号出错
   
   /*获取元数据接口返回码*/
   public static final String META_USERID_NULL="021";//用户名不能为空
   public static final String META_METALIST_NULL="022";//参数不对或者无法连接到服务器,获取的元数据数组为Null
   public static final String META_FILELIST_NULL="023";//没有查询到相关文件类型属性,请查看UST文件类型配置是否有手机扫描件
   
   /*高级查询接口返回码*/
   public static final String QUERY_USERID_NULL="031";//用户名不能为空
   public static final String QUERY_MODEL_NULL="032";//模型号为空
   public static final String QUERY_APP_NULL="033";//应用号为空
   public static final String QUERY_ORGAN_NULL="034";//机构号为空
   public static final String QUERY_META_NULL="035";//作为查询条件的元数据为空
   public static final String QUERY_RESULT_NULL="036";//查询结果为Null,查询出错
   public static final String QUERY_RESULT_ERROR="037";//查询结果解析出错，没有查询到相关数据
   public static final String QUERY_RESULT_ERROR2="038";//查询结果非该用户上传
   public static final String QUERY_RESULT_ERROR3="039";//ECM返回出错
   
   /*文件查询接口返回码*/
   public static final String BATCH_BATCH_NULL="041";//批次号为空
   public static final String BATCH_MODEL_NULL="042";//模型号为空
   public static final String BATCH_APP_NULL="043";//应用号为空
   public static final String BATCH_ORGAN_NULL="044";//机构号为空
   public static final String BATCH_SERVICE_ERROR="045";//请求ecmservice出错
   public static final String BATCH_RESULT_ERROR="046";//ecm返回结果出错
   public static final String BATCH_RESULT_ERROR2="047";//ecm返回结果出错2
   
   /*任务相关接口返回码*/
   public static final String TASK_CLAIM_FAIL="051";//任务认领失败
   public static final String TASK_LIST_FAIL="052";//已认领的任务列表获取失败
   public static final String TASK_SIGNIN_FAIL="053";//任务签到失败
   public static final String TASK_APPLY_FAIL="054";//任务认领失败
   public static final String TASK_DISTRILIST_FAIL="055";//获取未分配任务列表失败
   public static final String TASK_QUIT_FAIL="056";//任务放弃失败
   
   /*推送相关接口返回码*/
   public static final String PUSH_MESSAGE_STATUS_FAIL="061";//消息状态修改失败
   public static final String PUSH_UNREAD_MESSAGE_FAIL="062";//获取未读消息列表失败
   public static final String PUSH_READED_MESSAGE_FAIL="063";//获取已读消息列表失败
   public static final String PUSH_INIT_FAIL="064";//初始化推送通知失败
   public static final String PUSH_UNCLAIM_TASK_FAIL="065";//获取未分配任务列表失败
   public static final String PUSH_CLAIMED_TASK_FAIL="066";//获取已分配任务列表失败
   
   /*任务上传接口返回码*/
   public static final String UPLOAD_PARAMETER_ERROR="071";//传入参数错误
   public static final String UPLOAD_DISK_ERROR="072";//需要创建的文件夹盘符不存在
   public static final String UPLOAD_PERMISSION_ERROR="073";//没有获取到用户权限
   public static final String UPLOAD_SERVICE_ERROR="074";//ECM返回出错
   public static final String UPLOAD_OTHER_ERROR="075";//其他错误
   public static final String UPLOAD_DELETE_ERROR="076";//开始上传时，删除文件出错
   public static final String UPLOAD_GET_EXTRA_ERROR="077";//获取附加信息出错
   public static final String UPLOAD_WRITE_ERROR="078";//写入文件出错
   
   public static final String STATUS_ERROR="100";//服务端未知错误
}
