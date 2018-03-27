package wang.yiwangchunyu.community;

import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.webService.RequestApiData;

/**
 * @author  ItLanBaoApplication主要作用是处理一些app全局变量，
 */
public class ItLanBaoApplication extends ItLanbaoLibApplication {

	private static UserBaseInfo baseUser;//用户基本信息

	private RequestApiData requestApi;
	private static ItLanBaoApplication instance;

	// 渠道号
	private String fid = "";
	// 版本号
	private String version = "";

	@Override
	public void onCreate() {
		super.onCreate();
		setInstance(this);
		requestApi = RequestApiData.getInstance();
	}

	public static void setInstance(ItLanBaoApplication instance) {
		ItLanBaoApplication.instance = instance;
	}


	/**
	 * 设置用户基本信息
	 * @param baseUser
	 */
	public void setBaseUser(UserBaseInfo baseUser) {
		this.baseUser = baseUser;
	}

	public UserBaseInfo getBaseUser() {
		return baseUser;
	}

	/**
	 * 获取ItLanBaoApplication实例
	 *
	 * @return
	 */
	public static ItLanBaoApplication getInstance() {
		return instance;
	}

}
