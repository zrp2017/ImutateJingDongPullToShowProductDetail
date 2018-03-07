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
import com.zrp.library.ScreenUtil;
import com.zrp.library.ScrollViewContainer;
import com.zrp.library.StatusBarUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

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
    @Bind(R.id.nlv_good_detial_imgs)
    NoScrollListView nlvImgs;//图片详情
    @Bind(R.id.flipper)
    ViewFlipper flipper;
    @Bind(R.id.tab_title)
    TabLayout tabTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //透明状态栏
        StatusBarUtil.setTranslucentForImageView(this, llOffset);
        initViews();
        //initImgDatas();
        initListeners();
        initTab();
    }

    private void initViews() {
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) llOffset.getLayoutParams();
        params1.setMargins(0, -StatusBarUtil.getStatusBarHeight(this) / 4, 0, 0);
        llOffset.setLayoutParams(params1);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
        params.height = (int) (ScreenUtil.getScreenHeight(this) * 0.4);
        iv.setLayoutParams(params);
    }

    private void initTab() {
        tabTitle.addTab(tabTitle.newTab().setText(R.string.product));
        tabTitle.addTab(tabTitle.newTab().setText(R.string.detail));
        tabTitle.addTab(tabTitle.newTab().setText(R.string.comment));
    }

    private void initListeners() {
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
}
