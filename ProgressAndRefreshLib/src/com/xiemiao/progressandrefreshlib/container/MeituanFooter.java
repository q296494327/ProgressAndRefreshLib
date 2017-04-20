package com.xiemiao.progressandrefreshlib.container;

import com.xiemiao.progressandrefreshlib.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Created by Administrator on 2016/3/21.
 */
public class MeituanFooter extends BaseFooter {

    private AnimationDrawable animationLoading;

    private Context context;
    private ImageView footer_img;
    private int[] loadingAnimSrcs ;

    public MeituanFooter(Context context){
        this(context,null);
    }
    public MeituanFooter(Context context,int[] loadingAnimSrcs){
        this.context = context;
        if (loadingAnimSrcs!=null) this.loadingAnimSrcs = loadingAnimSrcs;
        animationLoading = new AnimationDrawable();
        for (int src: this.loadingAnimSrcs) {
            animationLoading.addFrame(context.getResources().getDrawable(src),150);
            animationLoading.setOneShot(false);
        }
    }

    @Override
    public View getView(LayoutInflater inflater,ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.meituan_footer, viewGroup, true);
        footer_img = (ImageView) view.findViewById(R.id.meituan_footer_img);
        if (animationLoading!=null)
            footer_img.setImageDrawable(animationLoading);
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {
        animationLoading.stop();
        if (animationLoading!=null && animationLoading.getNumberOfFrames()>0)
            footer_img.setImageDrawable(animationLoading.getFrame(0));
    }

    @Override
    public void onDropAnim(View rootView, int dy) {
    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
    }

    @Override
    public void onStartAnim() {
        if (animationLoading!=null)
            footer_img.setImageDrawable(animationLoading);
        animationLoading.start();
    }

    @Override
    public void onFinishAnim() {
        animationLoading.stop();
        if (animationLoading!=null && animationLoading.getNumberOfFrames()>0)
            footer_img.setImageDrawable(animationLoading.getFrame(0));
    }
}