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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoWithWord extends TitleTravelRecord {
    protected OnCheckCameraPermission mOnCheckCameraPermission;
    private OnCheckStoragePermission mOnCheckStoragePermission;
    private ImageView picture;
    private File sdcardTempFile;
    public static final int TAKE_PHOTO = 101;
    protected final int PERMISSIONS_WRITE_STORAGE = 1;
   public final int PERMISSIONS_CAMERA = 2;

    //接受前一个Intent传入的id
    private Bundle bundle;
    private int Show_Choice;
    String photoPath = Environment.getExternalStorageDirectory().getPath() + "/pictures/myPhoto" + SystemClock.currentThreadTimeMillis() + ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_with_word);
        showTitleTextView(false);
        showBackwardView(true);
        showUploadView(true);
        picture = (ImageView) findViewById(R.id.img_photo);
        bundle = this.getIntent().getExtras();
        Show_Choice = bundle.getInt("id");

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


    /**
     * android6.0动态权限申请：SD卡读写权限
     **/
    public void checkStoragePression(OnCheckStoragePermission callback) {
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
    public void checkCameraPression(OnCheckCameraPermission callback) {
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

                        //显示照片
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        picture.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

}

