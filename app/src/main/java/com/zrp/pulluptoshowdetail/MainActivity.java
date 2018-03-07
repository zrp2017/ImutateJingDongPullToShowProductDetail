package com.zrp.pulluptoshowdetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.zrp.library.GradationScrollView;
import com.zrp.library.MyImageLoader;
import com.zrp.library.NoScrollListView;
import com.zrp.library.ScrollViewContainer;
import com.zrp.library.StatusBarUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GradationScrollView.ScrollViewListener {

    @Bind(R.id.scrollview)
    GradationScrollView scrollView;
    @Bind(R.id.iv_good_detai_img)
    ImageView iv;
    @Bind(R.id.ll_good_detail)
    RelativeLayout llTitle;
    @Bind(R.id.ll_offset)
    LinearLayout llOffset;
    @Bind(R.id.iv_good_detai_collect_select)
    ImageView ivCollectSelect;//收藏选中
    @Bind(R.id.iv_good_detai_collect_unselect)
    ImageView ivCollectUnSelect;//收藏未选中
    @Bind(R.id.sv_container)
    ScrollViewContainer container;
//    @Bind(R.id.tv_good_detail_title_good)
//    TextView tvGoodTitle;
    @Bind(R.id.nlv_good_detial_imgs)
    NoScrollListView nlvImgs;//图片详情
    @Bind(R.id.flipper)
    ViewFlipper flipper;
    @Bind(R.id.tab_title)
    TabLayout tabTitle;
    private QuickAdapter<String> imgAdapter;
    private List<String> imgsUrl;

    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //透明状态栏
        StatusBarUtil.setTranslucentForImageView(this, llOffset);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) llOffset.getLayoutParams();
        params1.setMargins(0, -StatusBarUtil.getStatusBarHeight(this) / 4, 0, 0);
        llOffset.setLayoutParams(params1);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
        params.height = (int) (getScreenHeight(this) * 0.4);
        iv.setLayoutParams(params);
        initImgDatas();
        initListeners();
        initTab();
        container.setScrollEndListener(new ScrollViewContainer.ScrollEndListener() {
            @Override
            public void onScrollEnd() {
                int childAt = flipper.getDisplayedChild();
                if (childAt == 0) {
                    flipper.setInAnimation(MainActivity.this, R.anim.anim_tab_come_in);
                    flipper.setOutAnimation(MainActivity.this, R.anim.anim_tab_get_out);
                } else {
                    flipper.setInAnimation(MainActivity.this, R.anim.anim_detail_come_in);
                    flipper.setOutAnimation(MainActivity.this, R.anim.anim_detail_get_out);
                }
                flipper.showNext();
            }
        });
    }

    private void initTab() {
        tabTitle.addTab(tabTitle.newTab().setText("商品"));
        tabTitle.addTab(tabTitle.newTab().setText("详情"));
        tabTitle.addTab(tabTitle.newTab().setText("评价"));
    }

    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    // TODO: 16/8/21 模拟图片假数据
    private void initImgDatas() {
        width = getScreenWidth(getApplicationContext());
        imgsUrl = new ArrayList<>();
        imgsUrl.add("https://img.alicdn.com/imgextra/i4/714288429/TB2dLhGaVXXXXbNXXXXXXXXXXXX-714288429.jpg");
        imgsUrl.add("https://img.alicdn.com/imgextra/i3/726966853/TB2vhJ6lXXXXXbJXXXXXXXXXXXX_!!726966853.jpg");
        imgsUrl.add("https://img.alicdn.com/imgextra/i4/2081314055/TB2FoTQbVXXXXbuXpXXXXXXXXXX-2081314055.png");
        imgAdapter = new QuickAdapter<String>(this, R.layout.adapter_good_detail_imgs) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                ImageView iv = helper.getView(R.id.iv_adapter_good_detail_img);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
                params.width = width;
                params.height = width / 2;
                iv.setLayoutParams(params);
                MyImageLoader.getInstance().displayImageCen(getApplicationContext(), item, iv, width, width / 2);
            }
        };
        imgAdapter.addAll(imgsUrl);
        nlvImgs.setAdapter(imgAdapter);
    }

    private void initListeners() {

        ViewTreeObserver vto = iv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                scrollView.setScrollViewListener(MainActivity.this);
            }
        });
    }

    /**
     * 滑动监听
     *
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
    }
}
