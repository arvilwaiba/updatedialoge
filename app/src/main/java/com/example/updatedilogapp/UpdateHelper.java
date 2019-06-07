package com.example.updatedilogapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;


public class UpdateHelper {

    public static String KEY_UPDATE_ENABLE="isUpdate";
    public static String KEY_UPDATE_VERSION="version";
    public static String KEY_UPDATE_url="update_url";

    public interface OnUpdateCheckListener{
        void OnUpdateCheckListener(String urlApp);
    }
public static Builder with(Context context){
        return new Builder(context);
}

    private OnUpdateCheckListener onUpdateCheckListener;
    private Context context;

    public UpdateHelper( Context context,OnUpdateCheckListener onUpdateCheckListener) {
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }

    public static class Builder{

        private Context context;
        private OnUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context) {
            this.context = context;
            this.onUpdateCheckListener = onUpdateCheckListener;
        }

        public Builder onUpdateCheck(OnUpdateCheckListener onUpdateCheckListener){
            this.onUpdateCheckListener=onUpdateCheckListener;
            return this;
        }

        public void check(){

            FirebaseRemoteConfig firebaseRemoteConfig=FirebaseRemoteConfig.getInstance();
            if(firebaseRemoteConfig.getBoolean(KEY_UPDATE_ENABLE)){
                String currentVersion=firebaseRemoteConfig.getString(KEY_UPDATE_VERSION);
                String appVersion=getAppVersion(context);
                String updateURL=firebaseRemoteConfig.getString(KEY_UPDATE_url);

                if(!TextUtils.equals(currentVersion,appVersion)&& onUpdateCheckListener!=null){
                    onUpdateCheckListener.OnUpdateCheckListener(updateURL);
                }
            }

        }

        private String getAppVersion(Context context) {

            String result="";
            try {
                result=context.getPackageManager().getPackageInfo(context.getPackageName(),0)
                        .versionName;
                result=result.replaceAll("[a-za-z]|-","");

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return  result;

        }

        public UpdateHelper build(){

            return new UpdateHelper(context,onUpdateCheckListener);
        }

//        public UpdateHelper Check(){
//
//            UpdateHelper updateHelper= build();
//            updateHelper.check();
//            return updateHelper;
//        }


    }



}
