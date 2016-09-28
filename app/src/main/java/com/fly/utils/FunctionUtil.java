package com.fly.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @描述： 一些通用的函数
 * @author： lengguoxing
 * @创建时间： @2015-07-21
 */
public class FunctionUtil {
	private static long lastClickTime = 0;
	public static String gc;
	public static String orderNumber;
	public static int youhui;



	/**
	 * 开始用的这种，后来就不限制，调用checkPhone方法。 判断手机格式是否正确，在注册和修改手机号码的时候用到
	 *
	 * @param mobiles
	 * @return true:正确的手机号码
	 */

	public static boolean isMobileNO(String mobiles) {
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Pattern p = Pattern.compile("^(1(([123456789][0-9])|(4[57])))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static void buyNow(Context context) {
		try {
			Intent intent = new Intent("com.ubox.launcher");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			/*PackageManager packageManager = context.getPackageManager();
			Intent intent = packageManager.getLaunchIntentForPackage("com.ubox.launcher");
			if(intent != null) {
				context.startActivity(intent);
			}else{
				ToastUtil.showToast(context, "暂未安装，不支持");
			}*/
		}catch (Exception e) {
			ToastUtil.showToast(context, "暂未安装，不支持");
		}

		/*Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);*/
	}

	/**
	 * 校验手机号码
	 *
	 * @param context
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(Context context, String phone) {
		if (null == phone || "".equals(phone) || "".equals(phone.trim())) {
			ToastUtil.showToast(context, "请输入手机号码");
			return false;
		} else if (phone.length() != 11 || !phone.startsWith("1")) {
			ToastUtil.showToast(context, "手机号码格式不对");
			return false;
		}

		return true;
	}

	/**
	 * 是否包含非法字符 注册的时候判断用户名是否包含特殊字符
	 *
	 * @return true ：包含特殊字符，fasle就是不包含
	 */
	public static boolean containInvalidChars(String str) {
		if (str == null || "".equals(str))
			return false;
		String SPECIAL_STR = "#~!@%^&*();'\"?><[]{}\\|,:/=+—“”‘.`$；，。！@#￥%……&*（）——+？";

		for (int i = 0; i < str.length(); i++) {
			if (SPECIAL_STR.indexOf(str.charAt(i)) != -1) {
				return true;
			}
		}
		return false;
	}




	/**
	 * 检测是否有emoji表情
	 *
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是Emoji
	 *
	 * @param codePoint 比较的单个字符
	 * @return
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
				(codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
				((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
				&& (codePoint <= 0x10FFFF));
	}

	/**
	 * 判断是否全是数字
	 * @param str
	 * @return true :全是数字
	 */
	public static boolean isNumer(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}

		return true;
	}

	/**
	 * 判断是否全是英文
	 * @param str
	 * @return true :全是英文
	 */
	public static boolean isLetter(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断密码是否符合规范
	 * @param str
	 */
	public static boolean isPasswordSpecification(String str) {
		Pattern pattern = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)(?!((\\s.*)|(.*\\s)|(.*\\s.*))$).{6,20}$");
		return pattern.matcher(str).matches();
	}
	/**
	 * 判断用户名是否符合规范
	 * @param str
	 */
	public static boolean isUserNameSpecification(String str) {
		Pattern pattern = Pattern.compile("^[a-zA-Z_\u4e00-\u9fa5][a-zA-Z_0-9\u4e00-\u9fa5]{3,19}$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断email格式是否正确
	 *
	 * @param email
	 * @return
	 */

	public static boolean isEmail(String email) {
		/*final String str_email = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

		Pattern p = Pattern.compile(str_email);
		Matcher m = p.matcher(email);
		return m.matches();*/
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+([\\_|\\-|\\.]?[a-zA-Z0-9])*\\@[a-zA-Z0-9]+([\\_|\\-|\\.]?[a-zA-Z0-9])*\\.[a-zA-Z]{2,3}$");
		return pattern.matcher(email).matches();
	}

	/**
	 * 校验身份证
	 * @return true代表校验通过
	 */
	public static boolean identity(String str){
		Pattern pattern = Pattern.compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
		return pattern.matcher(str).matches();
	}

	//使整个页面滚动到最顶部
	public static void scorllTotop(View view){
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}


	/**
	 * 根据原图和边长绘制圆形图片
	 * @param source
	 * @return
	 */
	public static Bitmap createCircleImage(Bitmap source) {
		int min = source.getWidth();
		if(source.getHeight() < min){
			min = source.getHeight();
		}

		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(target);//产生一个同样大小的画布

		canvas.drawCircle(min / 2, min / 2, min / 2, paint);//首先绘制圆形

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//使用SRC_IN

		canvas.drawBitmap(source, 0, 0, paint);//绘制图片
		return target;
	}


	/**隐藏键盘 **/
	public static void setKeyboardGone(Context context, EditText editText){
		InputMethodManager imm =(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}




	/**
	 * 防止用户连续点击
	 *
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		if (lastClickTime == 0) {// 刚启动第一次运行
			lastClickTime = System.currentTimeMillis();
			return false;
		}

		// 以前在地方运行过，现在在另外一个Activity运行
		long time = System.currentTimeMillis();
		long timeDistanse = time - lastClickTime;
		if (0 < timeDistanse && timeDistanse < 800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}


	// 判断字符串是否为空,不为空返回true
	public static Boolean strNotNull(String str) {
		if (str == null || "null".equals(str) || "".equals(str.trim())) {
			return false;
		}
		return true;
	}

	// 判断list是否为空,不为空返回true
	public static Boolean listNotNull(List<Object> list) {
		if (list == null || list.size() == 0) {
			return false;
		}

		return true;
	}

	// 判断list是否为空,不为空返回true
	public static Boolean listStringNotNull(List<String> list) {
		if (list == null || list.size() == 0) {
			return false;
		}

		return true;
	}

	// 判断array是否为空,不为空返回true
	public static Boolean arrayNotNull(Object[] array) {
		if (array == null || array.length == 0) {
			return false;
		}
		return true;
	}

	// 判断map是否为空,不为空返回true
	public static Boolean mapNotNull(Map map) {
		if (map == null || map.size() == 0) {
			return false;
		}

		return true;
	}

	// 根据总共的数据条数，和每页的条数，获取页数
	public static int getTotalPage(int totalSize, int pageSize) {
		if(pageSize == 0){
			return 1;
		}

		if(totalSize % pageSize == 0){
			return totalSize/pageSize;
		}else{
			return totalSize/pageSize + 1;
		}
	}


	/**
	 * 判断字符串是否包含数字
	 * @return true 包含
	 */
	public static boolean hasDigit(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches())
			flag = true;
		return flag;
	}

	/**
	 * 判断字符串是否纯中文或者纯英文
	 * @return true 包含
	 */
	public static boolean isContainChineseOrEnglish(String str) {
		Pattern p= Pattern.compile("^[A-z]+$|^[\\u4E00-\\u9FA5]+$");
		Matcher m=p.matcher(str);
		if(m.find())
		{
			return true;
		}
		return false;
	}



	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *  @param strDate
	 *  @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	//将日期进行加减运算
	public static Date changeData(Date date, int change){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.add(Calendar.DATE, change);
		date=cld.getTime();

		try{
			date = formatter.parse(formatter.format(date));
		}catch (Exception e){
			e.printStackTrace();
		}

		return date;
	}


	public static void makeImageViewProgress(ImageView imageView){
		Object ob = imageView.getBackground();
		AnimationDrawable anim = (AnimationDrawable) ob;
		anim.stop();
		anim.start();
	}





	// 判断一个字符数组中是否有相等的2个字符串，只要有一对相等就返回true，否则返回false
	public static Boolean arrHasEqaulsNumber(String[] data) {
		for (int j = 0; j < data.length; j++) {// 遍历我们分割好的数组
			for (int j2 = 0; j2 < data.length; j2++) {// 遍历我们分割好的数组
				// FunctionUtil.sysMessage("j= " + j + "  j2= " + j2 + "比较 " +
				// data[j] + " 和   " + data[j2] + " 是否相等 " +
				// (data[j].equals(data[j2])));
				if (j != j2 && data[j].equals(data[j2])) {
					return true;
				}
			}
		}

		return false;
	}

	//比较2个时间的间隙。即时间1减掉时间2,得到的是一个秒数
	public static long calculatorInterval(String time1, String time2){
		if(!FunctionUtil.strNotNull(time1) || !FunctionUtil.strNotNull(time2)){
			return 0;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(time1);
			Date dt2 = df.parse(time2);
			long distanceTime = dt1.getTime() - dt2.getTime();//得到的是毫秒数

			if(distanceTime >= 1000){
				return distanceTime/1000;
			}else{
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽度和高度，单位为px
	 * @param context
	 * @return
	 */
	public static Point getScreenMetrics(Context context){
		DisplayMetrics dm =context.getResources().getDisplayMetrics();
		int w_screen = dm.widthPixels;
		int h_screen = dm.heightPixels;
		return new Point(w_screen, h_screen);

	}

	/**
	 * 获取屏幕长宽比
	 * @param context
	 * @return
	 */
	public static float getScreenRate(Context context){
		Point P = getScreenMetrics(context);
		float H = P.y;
		float W = P.x;
		return (H/W);
	}

	/**
	 * 旋转Bitmap
	 * @param b
	 * @param rotateDegree
	 * @return
	 */
	public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree){
		Matrix matrix = new Matrix();
		matrix.postRotate((float)rotateDegree);
		Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
		return rotaBitmap;
	}

	public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation,
									 int viewWidth, int viewHeight) {
		// Need mirror for front camera.
		matrix.setScale(mirror ? -1 : 1, 1);
		// This is the value for android.hardware.Camera.setDisplayOrientation.
		matrix.postRotate(displayOrientation);
		// Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
		// UI coordinates range from (0, 0) to (width, height).
		matrix.postScale(viewWidth / 2000f, viewHeight / 2000f);

		matrix.postTranslate(viewWidth / 2f, viewHeight / 2f);
	}

	/**

	 * @param bitmap      原图
	 * @param edgeLength  希望得到的正方形部分的边长
	 * @return  缩放截取正中部分后的位图。
	 */
	public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
	{
		if(null == bitmap || edgeLength <= 0)
		{
			return  null;
		}

		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();

		if(widthOrg > edgeLength && heightOrg > edgeLength)
		{
			//压缩到一个最小长度是edgeLength的bitmap
			int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
			int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
			int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
			Bitmap scaledBitmap;

			try{
				scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
			}
			catch(Exception e){
				return null;
			}

			//从图中截取正中间的正方形部分。
			int xTopLeft = (scaledWidth - edgeLength) / 2;
			int yTopLeft = (scaledHeight - edgeLength) / 2;

			try{
				result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
				scaledBitmap.recycle();
			}
			catch(Exception e){
				return null;
			}
		}

		return result;
	}

	/**
	 * 按正方形裁切图片
	 */
	public static Bitmap ImageCrop(Bitmap bitmap) {
		int w = bitmap.getWidth(); // 得到图片的宽，高
		int h = bitmap.getHeight();

		int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

		int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
		int retY = w > h ? 0 : (h - w) / 2;

		//下面这句是关键
		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}

}
