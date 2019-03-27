package com.example.travelapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TravelRecordDetail extends TitleTravelRecord {

    private ImageView imgMap;
    private List<RecordDetail> recordDetailList = new ArrayList<>();
    private List<RecordDetail> mRecord = new ArrayList<>();
    private List<String> mRecordPhoto = new ArrayList<>();
    private List<String> mRecordWords = new ArrayList<>();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_record_detail);
        setTitle("TRAVEL RECORD");
        showTitleTextView(true);
        showBackwardView(true);
        showUploadView(false);

        imgMap = (ImageView)findViewById(R.id.img_map);
        initRecordDetail();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecordDetailAdapter adapter=new RecordDetailAdapter(this,recordDetailList);
        //RecordDetailAdapter adapter=new RecordDetailAdapter(this,mRecord);
        recyclerView.setAdapter(adapter);
    }


    private void initRecordDetail() {

        mRecordWords = getWordsPathFromSD();
        mRecordPhoto = getImagePathFromSD();
        String photodescribe;
        int length;
       // if (mRecordWords.size()>mRecordPhoto.size()){
            length = mRecordWords.size();
      //  }else length = mRecordPhoto.size();
        for (int i = 0; i < length; i++) {
            photodescribe = readFileOnLine(mRecordWords.get(i));
            String photoid = mRecordPhoto.get(i);
            RecordDetail view = new RecordDetail(photodescribe, photoid);
            Log.e("Words_descripe", mRecordWords.get(i) + photodescribe);
            recordDetailList.add(view);
        }
        /*for (int i = 0 ; i < Images.imageUrls.length ; i++){
            String data = Images.imageUrls[i];
            mDatas.add(data);
        }*/

        /*RecordDetail view =new RecordDetail("好看好看好看！",R.mipmap.view);
        recordDetailList.add(view);
        RecordDetail view1 =new RecordDetail("好看好看好看好看好看好看好看好看好看好看好看好看好看好看！",R.mipmap.view1);
        recordDetailList.add(view1);
        RecordDetail view2 =new RecordDetail("好看！",R.mipmap.view2);
        recordDetailList.add(view2);
        RecordDetail view3 =new RecordDetail("好看好看好看好看好看好看好看好看！",R.mipmap.view3);
        recordDetailList.add(view3);
        RecordDetail view4 =new RecordDetail("好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看好看！",R.mipmap.view4);
        recordDetailList.add(view4);
        RecordDetail view5 =new RecordDetail("好看好看好看！",R.mipmap.view5);
        recordDetailList.add(view5);
        RecordDetail view6 =new RecordDetail("好看好看！",R.mipmap.view6);
        recordDetailList.add(view6);
        RecordDetail view7 =new RecordDetail("好看好看好看好看好看好看好看好看好看好看！",R.mipmap.view7);
        recordDetailList.add(view7);*/
    }

    /**
     * 从sd卡获取图片资源
     * @return
     */
    private List<String> getImagePathFromSD() {
        // 图片列表
        List<String> imagePathList = new ArrayList<String>();
        // 得到sd卡内image文件夹的路径   File.separator(/)
            String photosPath = Environment.getExternalStorageDirectory().getPath()+"/pictures";
            Log.e("TRD", "read files" + photosPath);
            Log.e("TRD", Environment.getExternalStorageDirectory().getAbsolutePath() );
            // 得到该路径文件夹下所有的文件
            File fileAll = new File(photosPath);
            File[] files = fileAll.listFiles();
            // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (!files[i].isDirectory()) {
                    String filename = files[i].getName();
                    Log.e("file name", filename);
                }
                if (checkIsImageFile(file.getPath())) {
                    imagePathList.add(file.getPath());
                }
            }
        // 返回得到的图片列表
        return imagePathList;
    }


    /**
     * 从sd卡获取txt文件资源
     * @return
     */
    public List<String> getWordsPathFromSD() {
        List<String> wordsPathList = new ArrayList<String>();
       String wordsPath = Environment.getExternalStorageDirectory().getPath() + "/words";
        Log.e("TRD","read files"+wordsPath);
        File fileAll=new File(wordsPath);
        File[] files=fileAll.listFiles();
        //if (files == null){
          //  Log.e("error","空目录");return null;}
        // 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (checkIsWordsFile(file.getPath())){
                wordsPathList.add(file.getPath());
            }
        }
        return wordsPathList;
    }
    /**
     * 检查扩展名，得到图片格式的文件
     * @param fName  文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsImageFile(String fName) {
        boolean isImageFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("jpg") || FileEnd.equals("png") || FileEnd.equals("gif")
                || FileEnd.equals("jpeg")|| FileEnd.equals("bmp") ) {
            isImageFile = true;
        } else {
            isImageFile = false;
        }
        return isImageFile;
    }

    /**
     * 检查扩展名，得到txt格式的文件
     * @param fName  文件名
     * @return
     */
    @SuppressLint("DefaultLocale")
    private boolean checkIsWordsFile(String fName) {
        boolean isWordsFile = false;
        // 获取扩展名
        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("txt")) {
            isWordsFile = true;
        } else {
            isWordsFile = false;
        }
        return isWordsFile;
    }

    //读取txt文件内容
    public String readFileOnLine(String filePath){//输入文件路径
        String content="";
        String path = filePath;
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputStreamReader = null;
                try {
                    inputStreamReader = new InputStreamReader(instream, "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer sb = new StringBuffer("");
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                content = sb.toString();
            }
            catch (java.io.FileNotFoundException e)
            {
                Log.d("TestFile", "The File doesn't not exist.");
            }
            /*catch (IOException e)
            {
                Log.d("TestFile", e.getMessage());
            }*/
        }
        return content;
    }

}
