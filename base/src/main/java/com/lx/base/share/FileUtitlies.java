package com.lx.base.share;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/** 
 * @author Stay  
 * @version create time：Sep 23, 2014 3:00:08 PM 
 */
public class FileUtitlies {
	private static String BASE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "appHall_sichuan_folder";
	
	//日志....
    public static String getLogPath() {
        return BASE_DIR + File.separator + "crash" + File.separator;
    }

	
	public static String getDownloadDir(){
		String dir = BASE_DIR + File.separator + "download"+ File.separator;
		return checkDir(dir);
	}
	
	public static String getShareDir(Context mContext){
		String dir = mContext.getApplicationContext().getFilesDir().getAbsolutePath()+ File.separator + "sharefile";
		return checkDir(dir);
	}

	private static String checkDir(String dir) {
		File directory = new File(dir);
		if (!directory.exists() || !directory.isDirectory()) {
			directory.mkdirs();
		}
		return dir;
	}
	
	public static String getDownloadPath(String name){
		return getDownloadDir() + File.separator + name;
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

//	//删除文件夹和文件夹里面的文件
//	public static void deleteDir(final String pPath) {
//		File dir = new File(pPath);
//		deleteDirWihtFile(dir);
//	}
//
//	public static void deleteDirWihtFile(File dir) {
//		if (dir == null || !dir.exists() || !dir.isDirectory())
//			return;
//		for (File file : dir.listFiles()) {
//			if (file.isFile())
//				file.delete(); // 删除所有文件
//			else if (file.isDirectory())
//				deleteDirWihtFile(file); // 递规的方式删除文件夹
//		}
//		dir.delete();// 删除目录本身
//	}
}
