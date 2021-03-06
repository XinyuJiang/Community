package wang.yiwangchunyu.community.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wang.yiwangchunyu.community.utils.threadUtils.MyImageRunnable;
import wang.yiwangchunyu.community.utils.threadUtils.MyImageThread;

/**
 * @author Administrator
 * 检验工具类
 */
public class Utils {

    public static boolean isEmail(String email){

        String reg_email="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(reg_email);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static Bitmap convertStringToIcon(String st)
    {
        // OutputStream out;
        Bitmap bitmap = null;
        try
        {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[0-9]*");

        return pattern.matcher(str).matches();

    }

    public static boolean isAddress(String address){

        String reg_address = "[\\u4e00-\\u9fff0-9a-zA-Z]{1,100}";
        Pattern p = Pattern.compile(reg_address);
        Matcher m = p.matcher(address);
        return m.matches();
    }

    public static boolean isPhoneNumber(String phoneStr) {
        //手机号前三位关系到运营商，需定期更新
        String reg_phone = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\\\d{8}$";
        Pattern p = Pattern.compile(reg_phone);
        Matcher m = p.matcher(phoneStr);
        return m.matches();
    }

    public static boolean isUserName(String username){
        String reg_username = "^[\\u4e00-\\u9fff\\w]{5,16}$";
        Pattern p = Pattern.compile(reg_username);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static boolean isPassWord(String password){
        String reg_password = "[0-9a-zA-Z]{8,16}";
        Pattern p = Pattern.compile(reg_password);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * @return imgStr
     */
    public static String bitmap2String(Bitmap bitmap) {

        if(bitmap==null){

            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        boolean b = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        if(b==false){
            return null;
        }
        byte[] bs = stream.toByteArray();
        String s = Base64.encodeToString(bs, Base64.DEFAULT);

        return s;
    }

    //将网络图片转换成bitmap
    public static void getHttpBitmap(String url, ImageView imageView) {

        MyImageRunnable mir =new MyImageRunnable(url,imageView);
        MyImageThread mit = new MyImageThread();
        mit.start();

    }
}
