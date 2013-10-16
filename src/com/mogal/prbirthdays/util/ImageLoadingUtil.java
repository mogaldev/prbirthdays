package com.mogal.prbirthdays.util;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoadingUtil {
	private static ImageLoadingUtil instance;
	
	public static ImageLoadingUtil getInstance(Context context){
		if (instance == null)
			instance = new ImageLoadingUtil(context);
		return instance;
	}
	
	private ImageLoadingUtil(Context context) {
		ImageLoader imageLoader = ImageLoader.getInstance();		
		imageLoader.init(new ImageLoaderConfiguration.Builder(context).build());
	}
	
	public void loadImage(String uri, ImageView imageView){
		 DisplayImageOptions options = new DisplayImageOptions.Builder()
	     .showStubImage(android.R.drawable.ic_dialog_alert)
	     .cacheInMemory()
	     .cacheOnDisc()
	     .build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(uri, imageView, options);
	}
	
	
}
