# ProgressAndRefreshLib
常见加载布局(加载中,数据为空,网络异常)以及下拉刷新上拉加载
使用方法:

activity布局定义:
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.progressandrefreshdemo.MainActivity" >

    <!-- ProgressActivity用于状态页的控制 比如加载中  网络异常  无数据  适合任何页面 -->

    <com.xiemiao.progressandrefreshlib.viewtype.ProgressActivity
        xmlns:progressActivity="http://schemas.android.com/apk/res-auto"
        android:id="@+id/progress"
        android:layout_width="match_parent"
        android:layout_height="match_parent" >

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical" >

            <!-- SpringView下拉刷新 注意只能有一个孩子-->

            <com.xiemiao.progressandrefreshlib.widget.SpringView
                android:id="@+id/springview"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="#FFFFFF" >

                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:background="#eeeeee"
                    android:text="正常数据" />
            </com.xiemiao.progressandrefreshlib.widget.SpringView>
        </LinearLayout>
    </com.xiemiao.progressandrefreshlib.viewtype.ProgressActivity>

</RelativeLayout>

mainactivity代码:
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
