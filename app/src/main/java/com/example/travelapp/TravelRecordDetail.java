package com.example.travelapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
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


    @RequiresApi(api = Build.VERSION_CODES.M)
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    public List<String> getWordsPathFromSD() {
        List<String> wordsPathList = new ArrayList<String>();
        /**
         *Todo: Does the app have the permission to access the external storage
         * (depending on your android version, requesting permission at runtime is needed on android 6 and >)
         * https://developer.android.com/training/permissions/requesting.html
         *Todo: Does the words directory exist?
         *Todo: Does the words directory contain the files you need?
         */
       //String wordsPath = Environment.getExternalStorageDirectory().getPath() + "/words";
        /***
         * START
         */

        try {
            if(ExternalStorageUtil.isExternalStorageMounted()) {

                // Check whether this app has write external storage permission or not.
                int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                // If do not grant write external storage permission.
                if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
                {
                    // Request user to grant write external storage permission.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 119);
                }else {

                    // Specify the directory to use
                    String publicDirectory = ExternalStorageUtil.getPublicExternalStorageBaseDir(Environment.DIRECTORY_DCIM);

                    File fileAll = new File(publicDirectory);
                    //Get the list of the files in this directory
                    File[] files=fileAll.listFiles();
                    for (File file: files){
                        Log.e("TAG", "File found: "+file.getName());
                    }
                    //Write a new file in this directory
                    String fileName = "my_file.txt";
                    File newFile = new File(publicDirectory, fileName);
                    FileWriter fw = new FileWriter(newFile);
                    fw.write("Insert this text");
                    fw.flush();
                    fw.close();
                    Log.e("TAG", "Saved: "+ newFile.getAbsolutePath());

                    /**
                     * Read the file content
                     */

                    // Get The Text file
                    File textFileToRead = new File(publicDirectory, fileName);

                    // Read the file Contents in a StringBuilder Object (handling long string content)
                    StringBuilder content = new StringBuilder();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(textFileToRead));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line + '\n');
                        }
                        reader.close();
                        Log.e("TAG", "Content: "+ content);

                    } catch (IOException e) {
                        Log.e("TAG", "Error reading the file requested.");

                    }



                }
            }

        }catch (Exception ex)
        {
            Log.e("TAG", "Error: "+ex.getMessage(), ex);

        }


        /***
         * END
         */
//        String wordsPath = Environment.getExternalStorageDirectory().getPath() + "/Pictures/";
//
//        Log.e("TRD","read files"+wordsPath);
//        File fileAll=new File(wordsPath);
//        File[] files=fileAll.listFiles();
//        if (files == null){
//            Log.e("error","空目录");return null;}
//         //将所有的文件存入ArrayList中,并过滤所有图片格式的文件
//        for (int i = 0; i < files.length; i++) {
//            File file = files[i];
//            if (checkIsWordsFile(file.getPath())){
//                wordsPathList.add(file.getPath());
//            }
//        }
//        return wordsPathList;
        return null;
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
