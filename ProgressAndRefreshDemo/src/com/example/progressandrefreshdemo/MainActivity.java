package com.example.progressandrefreshdemo;

import com.xiemiao.progressandrefreshlib.container.AcFunFooter;
import com.xiemiao.progressandrefreshlib.container.AcFunHeader;
import com.xiemiao.progressandrefreshlib.container.AliFooter;
import com.xiemiao.progressandrefreshlib.container.AliHeader;
import com.xiemiao.progressandrefreshlib.container.MeituanFooter;
import com.xiemiao.progressandrefreshlib.container.MeituanHeader;
import com.xiemiao.progressandrefreshlib.container.RotationFooter;
import com.xiemiao.progressandrefreshlib.container.RotationHeader;
import com.xiemiao.progressandrefreshlib.viewtype.ProgressActivity;
import com.xiemiao.progressandrefreshlib.widget.SpringView;
import com.xiemiao.progressandrefreshlib.widget.SpringView.OnFreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements OnFreshListener {

    private ProgressActivity progress;
    private SpringView springView;
    private int[] pullAnimSrcs = new int[] { R.drawable.mt_pull,
	    R.drawable.mt_pull01, R.drawable.mt_pull02, R.drawable.mt_pull03,
	    R.drawable.mt_pull04, R.drawable.mt_pull05 };
    private int[] refreshAnimSrcs = new int[] { R.drawable.mt_refreshing01,
	    R.drawable.mt_refreshing02, R.drawable.mt_refreshing03,
	    R.drawable.mt_refreshing04, R.drawable.mt_refreshing05,
	    R.drawable.mt_refreshing06 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	progress = (ProgressActivity) findViewById(R.id.progress);
	springView = (SpringView) findViewById(R.id.springview);
	// 设置下拉刷新监听
	springView.setListener(this);
	// 设置下拉刷新样式(雪花)
	// springView.setHeader(new RotationHeader(this));
	// springView.setFooter(new RotationFooter(this));
	// 设置下拉刷新样式(背景图)
	// springView.setType(SpringView.Type.OVERLAP);
	// springView.setHeader(new AcFunHeader(this, R.drawable.acfun_header));
	// springView.setFooter(new AcFunFooter(this, R.drawable.acfun_header));
	// 设置下拉刷新样式(世界触手可及)
//	springView.setType(SpringView.Type.FOLLOW);
//	springView.setHeader(new AliHeader(this, R.drawable.ali, true)); // 参数为：logo图片资源，是否显示文字
//	springView.setFooter(new AliFooter(this, R.drawable.ali,true));
	//美团版下拉刷新
	springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new MeituanHeader(this,pullAnimSrcs,refreshAnimSrcs));
        springView.setFooter(new MeituanFooter(this, refreshAnimSrcs));
	requestData();
    }

    private void requestData() {
	// 设置页面为加载中..
	progress.showLoading();
	new Thread(new Runnable() {
	    public void run() {
		try {
		    Thread.sleep(2000);
		    runOnUiThread(new Runnable() {
			public void run() {
			    springView.onFinishFreshAndLoad();// 刷新完成
			    progress.showContent();
			}
		    });
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    }
	}).start();
    }

    @Override
    public void onRefresh() {
	// 设置无数据显示页面
	progress.showEmpty(getResources().getDrawable(R.drawable.monkey_cry),
		"没有找到数据", "换个条件试试吧");
    }

    @Override
    public void onLoadmore() {
	// 设置加载错误页显示
	progress.showError(getResources().getDrawable(R.drawable.monkey_cry),
		"网络连接异常", "请检查网络后重试", "重试", new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			requestData();
		    }
		});
    }

}
