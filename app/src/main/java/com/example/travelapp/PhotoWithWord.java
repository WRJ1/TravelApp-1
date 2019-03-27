package com.example.travelapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoWithWord extends TitleTravelRecord {
    private ImageView picture;
    private EditText typewords;
    private File sdcardTempFile;
    private String routefile;
    private String photoPath;
    public static final int TAKE_PHOTO = 101;

    //接受前一个Intent传入的id
    private Bundle bundle;
    private int Show_Choice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_with_word);
        showTitleTextView(false);
        showBackwardView(true);
        showUploadView(false);

        Intent intentroutefile = getIntent();
        routefile = intentroutefile.getStringExtra("starttime");
        setFilename(routefile);
        Log.e("starttime",routefile);


        photoPath = Environment.getExternalStorageDirectory().getPath() +"/pictures/"+routefile + "_" + filename() + ".jpg";
        //文本框记录文字
        typewords = (EditText) findViewById(R.id.edit_content);
        typewords.addTextChangedListener(edit);

        //调用相机并显示照片
        picture   = (ImageView) findViewById(R.id.img_photo);
        bundle = this.getIntent().getExtras();
        Show_Choice = bundle.getInt("id");

        //检查各项权限
        checkCameraPression(new OnCheckCameraPermission() {
            @Override
            public void onCheckCameraPression(boolean haspermission) {
                if (haspermission) {
                    checkStoragePression(new OnCheckStoragePermission() {
                        @Override
                        public void onCheckStoragePression(boolean hasStorePermission) {
                            if (hasStorePermission) {
                                //showBottomDialog();
                                Toast.makeText(PhotoWithWord.this, "you can use camera", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        //接收Intent传递的id值，并判断，照相功能为1，打开相册功能为2
        switch (Show_Choice) {
            //如果传递为TAKE_PHOTO
            case TAKE_PHOTO: {
                sdcardTempFile = new File(photoPath);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(PhotoWithWord.this, "com.example.travelapp.fileprovider", sdcardTempFile));
                } else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sdcardTempFile));
                }
                startActivityForResult(intent, 101);
            }
            break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (Show_Choice) {
            case 101:
                if (resultCode == RESULT_OK && sdcardTempFile.exists()) {
                    try {
                        //如果需要上传照片到服务器，上传方法写这里既可
                        //String newPath = PhotoBitmapUtils.amendRotatePhoto(photoPath, this);
                        setPictureDegreeZero(photoPath);
                        final File newSdcardTempFile = new File(photoPath);
                        //FileInputStream inputStream = new FileInputStream(sdcardTempFile);
                        FileInputStream inputStream = new FileInputStream(newSdcardTempFile);
                        Log.e("photoname",photoPath);
                        //显示照片
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else finish();
                break;
            default:
                break;
        }
    }

    //修正照片的旋转角度
    public static void setPictureDegreeZero(String path) {

        try {

            ExifInterface exifInterface = new ExifInterface(path);

            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，

            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的

            //exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_ROTATE_90));
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_UNDEFINED));
            exifInterface.saveAttributes();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    //对文本框进行监听
    private TextWatcher edit = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String words = s.toString();
            setWords(words);
            setFilename(routefile);
            if (words!="")
            {
                isPhotowithword(true);
                showUploadView(true);
            }
            else {
                showUploadView(false);
            }
        }
    };
    //根据时间命名照片
    public String filename(){
        //SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //SimpleDateFormat filesdf = new SimpleDateFormat("yyyy-MM-dd HHmmss"); //文件名不能有：
        //String FileTime =timesdf.format(new Date()).toString();//获取系统时间
        //String filename = FileTime.replace(":", "");
        SimpleDateFormat hour = new SimpleDateFormat("HH");//获取小时
        SimpleDateFormat minute = new SimpleDateFormat("mm");//获取分钟
        SimpleDateFormat second = new SimpleDateFormat("ss");//获取秒
        String HH = hour.format(new Date());
        String mm = minute.format(new Date());
        String ss = second.format(new Date());
        String filename = HH+mm+ss;
        return filename;
        //MyTextView.setText("Time = "+java.nio.file.attribute.FileTime+"\nfile"+filename);
    }

}

