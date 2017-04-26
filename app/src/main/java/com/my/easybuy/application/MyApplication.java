package com.my.easybuy.application;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.avos.avoscloud.AVOSCloud;
import com.my.easybuy.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by user999 on 2017/2/14.
 */

public class MyApplication extends Application {
    private static Context mContext;
    //默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH= Environment.getExternalStorageDirectory()+ File.separator+"商城"+ File.separator+"Images"+ File.separator;
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"AbsKeQwElE42XODSrgyr76nh-gzGzoHsz","Xaihpf0IKCMAyel5GO6dnE1N");
        mContext=getApplicationContext();

        initImageLoader();
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        DisplayImageOptions options=new DisplayImageOptions.Builder().showImageForEmptyUri(R.color.bg_no_photo)
                .showImageOnFail(R.color.bg_no_photo).showImageOnLoading(R.color.bg_no_photo).cacheInMemory(true)
                .cacheOnDisk(true).build();

        File cacheDir=new File(DEFAULT_SAVE_IMAGE_PATH);
        ImageLoaderConfiguration imageLoaderConfiguration=new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(200)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(options).build();

        ImageLoader.getInstance().init(imageLoaderConfiguration);

    }
}
