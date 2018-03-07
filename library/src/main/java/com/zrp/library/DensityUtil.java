package com.zrp.library;

import android.content.Context;

/**
 * Created by zeng on 2018/3/5.
 */

public class DensityUtil {

    /**
     * 根据手机的分辨率将dp单位转成px(像素)
     * @param context
     * @param dpValue
     * @return
     */
    public static int dpConvertpx(Context context, float dpValue){
        /**
         手机显示的逻辑密度。这是独立像素密度单位的比例因子，在一个160dpi的手机屏上1DIP就是1pixel。
         因此在160dpi的手机屏上该density的值为1；在120dpi的手机屏上该density值将会是0.75，以此类推。
         根据真实的手机屏幕大小，该值还不是完全准确的，也就是说基于不同的手机分辨率，该density的值会有所变化，
         进而能使换算的结果更加的精确。举个例子来说，在一个240*320分辨率的手机屏幕上，density的值为1（即屏幕大小是：1.5''*2''）；
         但是如果屏幕的分辨率是320*480，且屏幕大小需要维持为：1.5''*2''，那么这时候density的值将会改变（由1变为1.5）
         */
        final float scale = context.getResources().getDisplayMetrics().density;
        // 四舍五入，使换算结果更加的精确
        return (int)(dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率将px（像素）单位转成dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxConvertdp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
}