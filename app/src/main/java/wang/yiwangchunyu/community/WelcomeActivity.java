package wang.yiwangchunyu.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import wang.yiwangchunyu.community.constant.Constant;
import wang.yiwangchunyu.community.constant.KeyConstance;
import wang.yiwangchunyu.community.constant.UrlConstance;
import wang.yiwangchunyu.community.users.UserBaseInfo;
import wang.yiwangchunyu.community.users.UserPreference;
import wang.yiwangchunyu.community.webService.HttpResponeCallBack;
import wang.yiwangchunyu.community.webService.RequestApiData;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends Activity implements HttpResponeCallBack {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        iv = (ImageView) this.findViewById(R.id.logo);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(2000);
        iv.startAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * 动画结束时判断是否保存有登录的信息
             * @param animation
             */
            @Override
            public void onAnimationEnd(Animation animation) {
                //暂时用用户名密码登录
                //String userAccount = UserPreference.read(KeyConstance.IS_USER_ACCOUNT, null);//软件还没有保持账号
                String userPassword = UserPreference.read(KeyConstance.IS_USER_PASSWORD, null);
                String userid = UserPreference.read(KeyConstance.IS_USER_ID, null);

                if (TextUtils.isEmpty(userid)) {//没有保存的登录信息跳转到登录界面
                    //空的，表示没有注册，或者清除数据
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    finish();
                } else {
                    //用保存的信息直接登录
                    RequestApiData.getInstance().getLoginData(userid, userPassword,
                            UserBaseInfo.class, WelcomeActivity.this);

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onResponeStart(String apiName) {

    }

    @Override
    public void onLoading(String apiName, long count, long current) {
        Toast.makeText(WelcomeActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(String apiName, Object object) {
        //当前接口是否是获取用户的基本信息的接口
        if (UrlConstance.KEY_USER_BASE_INFO.equals(apiName)) {
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                ItLanBaoApplication.getInstance().setBaseUser(info);//把数据放入到Application里面，全局
                UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUserid()));

                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right);
                finish();

            } else {
                Toast.makeText(WelcomeActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        } else if (UrlConstance.KEY_LOGIN_INFO.equals(apiName)) {//当前接口是登录的接口
            //登录返回数据
            if (object != null && object instanceof UserBaseInfo) {
                UserBaseInfo info = (UserBaseInfo) object;
                if (Constant.KEY_SUCCESS.equals(info.getRet())) {

                    ItLanBaoApplication.getInstance().setBaseUser(info);//将用户信息保存在Application中
                    UserPreference.save(KeyConstance.IS_USER_ID, String.valueOf(info.getUserid()));

                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                    finish();

                } else {
                    Toast.makeText(WelcomeActivity.this, info.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFailure(String apiName, Throwable t, int errorNo, String strMsg) {
        Toast.makeText(WelcomeActivity.this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
