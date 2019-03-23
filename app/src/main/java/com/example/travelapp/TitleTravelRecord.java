package com.example.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TitleTravelRecord extends Activity implements View.OnClickListener  {

    //private RelativeLayout mLayoutTitleBar;
    private TextView mTitleTextView;
    private Button mBackwardbButton;
    private Button mUploaddbButton;
    private FrameLayout mContentLayout;


    /*public TitleTravelRecord(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titile_travelrecord, this);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();   //加载 activity_title_travel_record 布局 ，并获取标题及按钮
    }

    private void setupViews() {
        super.setContentView(R.layout.activity_title_travel_record);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);
        mBackwardbButton = (Button) findViewById(R.id.title_back);
        mUploaddbButton = (Button)findViewById(R.id.btn_upload);

    }

    /**
     * 是否显示返回按钮
     *
     * @param show          true则显示
     */

    protected void showBackwardView(boolean show) {
        if (mBackwardbButton != null) {
            if (show) {
                //mBackwardbButton.setText(backwardResid);
                mBackwardbButton.setVisibility(View.VISIBLE);
            } else {
                mBackwardbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 是否显示提交按钮
     *
     * @param show          true则显示
     */

    protected void showUploadView(boolean show) {
        if (mUploaddbButton != null) {
            if (show) {
                //mBackwardbButton.setText(backwardResid);
                mUploaddbButton.setVisibility(View.VISIBLE);
            } else {
                mUploaddbButton.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 是否显示标题文字
     *
     * @param show          true则显示
     */

    protected void showTitleTextView(boolean show) {
        if (mTitleTextView != null) {
            if (show) {
                //mBackwardbButton.setText(backwardResid);
                mTitleTextView.setVisibility(View.VISIBLE);
            } else {
                mTitleTextView.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }


    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
        //mBackwardbButton.setOnClickListener();
        Toast.makeText(this, "点击返回，可在此处调用finish()", Toast.LENGTH_LONG).show();
        /*Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);*/
        finish();
    }

    /**
     * 提交按钮点击后触发
     *
     * @param uploadView
     */
    protected void onUpload(View uploadView) {
        //mBackwardbButton.setOnClickListener();
        Toast.makeText(this, "点击提交，可在此处调用finish()", Toast.LENGTH_LONG).show();
        /*Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);*/
        finish();
    }



    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        mTitleTextView.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }


    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     * 按钮点击调用的方法
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                onBackward(v);
                break;
            case R.id.btn_upload:
                onUpload(v);
                break;
            default:
                break;
        }

    }
}
