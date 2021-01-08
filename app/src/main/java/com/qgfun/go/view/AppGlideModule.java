package com.qgfun.go.view;

import com.bumptech.glide.annotation.GlideModule;

//import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;

/**
 * @author LLY
 */
@GlideModule
public final class AppGlideModule extends com.bumptech.glide.module.AppGlideModule {
   /* @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(okHttpClient(null));
        registry.replace(GlideUrl.class, InputStream.class, factory);
    }*/
}
