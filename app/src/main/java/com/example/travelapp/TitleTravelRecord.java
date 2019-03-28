package com.example.travelapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TitleTravelRecord extends Activity implements View.OnClickListener  {

    //private RelativeLayout mLayoutTitleBar;
    protected OnCheckCameraPermission mOnCheckCameraPermission;
    private OnCheckStoragePermission mOnCheckStoragePermission;
    private TextView mTitleTextView;
    private Button mBackwardbButton;
    private Button mUploaddbButton;
    private FrameLayout mContentLayout;
    private String words;
    private boolean isPhoroWithWord = false;
    protected final int PERMISSIONS_WRITE_STORAGE = 1;
    public final int PERMISSIONS_CAMERA = 2;
    private String routefile;
    private String wordsPath;
    private String photoWithWordsPath;
    /*public TitleTravelRecord(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.titile_travelrecord, this);
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();   //加载 activity_title_travel_record 布局 ，并获取标题及按钮
        /*Intent intentroutefile = getIntent();
        routefile = intentroutefile.getStringExtra("routefiletowords");*/
        /*wordsPath = Environment.getExternalStorageDirectory().getPath() +"/words/"+ routefile + "myWords" + filename() + ".txt";
        Log.e("TTR",wordsPath);
        photoWithWordsPath = Environment.getExternalStorageDirectory().getPath() + "/words" + routefile + filename() + ".txt";
        Log.e("TTR",photoWithWordsPath);*/
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
        Toast.makeText(this, "点击返回，可在此处调用finish()", Toast.LENGTH_LONG).show();
        finish();
        /*Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);*/

    }

    /**
     * 提交按钮点击后触发
     *
     * @param uploadView
     */
    protected void onUpload(View uploadView) {

        Toast.makeText(this, "点击提交，可在此处调用finish()", Toast.LENGTH_LONG).show();
        wordsPath = Environment.getExternalStorageDirectory().getPath() +"/words/"+ routefile + "myWords" +"_"+ filename() + ".txt";
        Log.e("TTR",wordsPath);
        photoWithWordsPath = Environment.getExternalStorageDirectory().getPath() + "/words/" + routefile +"_"+ filename() + ".txt";
        Log.e("TTR",photoWithWordsPath);
        final String path;
        if(isPhoroWithWord){
            path = photoWithWordsPath;
        }
        else path = wordsPath;
        Log.e("TTR",path+words);

        //检测文件输入权限
        checkStoragePression(new OnCheckStoragePermission() {
            @Override
            public void onCheckStoragePression(boolean haspermission) {
                if (haspermission){
                    try {
                        //打开文件输出流
                        FileOutputStream outputStream = new FileOutputStream(path);
                        //写数据到文件中
                        outputStream.write(words.getBytes());
                        outputStream.close();
                        Log.e("TTR","write successful");

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //打开文件输入流
        try {
            StringBuilder sb = new StringBuilder("");
            FileInputStream inputStream = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int len = inputStream.read(buffer);
            //读取文件内容
            while(len > 0){
                sb.append(new String(buffer,0,len));

                //继续将数据放到buffer中
                len = inputStream.read(buffer);
            }
            //关闭输入流
            inputStream.close();
            String newwords = sb.toString();
            Log.e("store success",path+newwords);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //记录输入的文字
    public void setWords(String typewords){
        words = typewords;
    }

    //区别两种文字输入类型
    public void isPhotowithword(boolean type){
        isPhoroWithWord = type;
    }

    //设置文字文件名
    public void setFilename(String fileneme){
        routefile = fileneme;
        Log.e("TTR","getfilename"+routefile);
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

    /****************权限管理****************************/
    /**
     * android6.0动态权限申请：SD卡读写权限
     **/
    public void checkStoragePression(PhotoWithWord.OnCheckStoragePermission callback) {
        mOnCheckStoragePermission = callback;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCamerapression = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCamerapression != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, PERMISSIONS_WRITE_STORAGE);
                return;
            }
            mOnCheckStoragePermission.onCheckStoragePression(true);
            return;
        }
        mOnCheckStoragePermission.onCheckStoragePression(true);
    }

    /**
     * android6.0动态权限申请：相机使用权限
     **/
    public void checkCameraPression(PhotoWithWord.OnCheckCameraPermission callback) {
        mOnCheckCameraPermission = callback;
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCamerapression = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (checkCamerapression != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_CAMERA);
                return;
            }
            mOnCheckCameraPermission.onCheckCameraPression(true);
            return;
        }
        mOnCheckCameraPermission.onCheckCameraPression(true);
    }

    /**
     * 权限申请
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permit = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        switch (requestCode) {
            case PERMISSIONS_CAMERA:
                if (mOnCheckCameraPermission != null) {
                    mOnCheckCameraPermission.onCheckCameraPression(permit);
                }
                break;
            case PERMISSIONS_WRITE_STORAGE:
                if (mOnCheckStoragePermission != null) {
                    mOnCheckStoragePermission.onCheckStoragePression(permit);
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Uri 转 绝对路径
     */
    public static String getFilePathFromContentUri(Uri selectedVideoUri, ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
        //也可用下面的方法拿到cursor
        //Cursor  cursor  =  this.context.managedQuery(selectedVideoUri,  filePathColumn,  null,  null,  null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    /**
     * 读写SD卡权限申请后回调
     **/
    public interface OnCheckStoragePermission {
        /**
         * @param haspermission true 允许  false 拒绝
         **/
        void onCheckStoragePression(boolean haspermission);
    }

    /**
     * 拍照权限申请后回调
     **/
    public interface OnCheckCameraPermission {
        /**
         * @param haspermission true 允许  false 拒绝
         **/
        void onCheckCameraPression(boolean haspermission);
    }

    /***************************************************************/

    //根据时间命名文件
    public String filename(){
        /*SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat filesdf = new SimpleDateFormat("yyyy-MM-dd HHmmss"); //文件名不能有：
        String FileTime =timesdf.format(new Date()).toString();//获取系统时间
        String filename = FileTime.replace(":", "");*/
        SimpleDateFormat hour = new SimpleDateFormat("HH");//获取小时
        SimpleDateFormat minute = new SimpleDateFormat("mm");//获取分钟
        SimpleDateFormat second = new SimpleDateFormat("ss");//获取秒
        String HH = hour.format(new Date());
        String mm = minute.format(new Date());
        String ss = second.format(new Date());
        String filename = HH+mm+ss;
        return filename;
    }


}
