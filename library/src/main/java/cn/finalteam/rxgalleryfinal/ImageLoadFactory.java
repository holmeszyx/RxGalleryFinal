package cn.finalteam.rxgalleryfinal;

import java.util.ArrayList;

import cn.finalteam.rxgalleryfinal.imageloader.AbsImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.FrescoImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.GlideImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.PicassoImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.UniversalImageLoader;

/**
 * The factory to build Image loaders.
 * And to put own custom loader
 * Created by holmes on 2017/7/23.
 */
public final class ImageLoadFactory {

    private static ImageLoadFactory sInstance = new ImageLoadFactory();

    public static ImageLoadFactory getInstance() {
        return sInstance;
    }

    private ImageLoadGetInterceptor mBuildIn;
    private ArrayList<ImageLoadGetInterceptor> mCustomLoader;

    private ImageLoadFactory() {
        mBuildIn = new BuildInLoaders();
    }

    public void addImageLoaderGetInterceptor(ImageLoadGetInterceptor interceptor) {
        if (mCustomLoader == null) {
            mCustomLoader = new ArrayList<>(2);
        }
        mCustomLoader.add(interceptor);
    }

    public AbsImageLoader getImageLoader(int type) {
        AbsImageLoader result;
        result = mBuildIn.getImageLoader(type);
        if (result == null && mCustomLoader != null) {
            for (ImageLoadGetInterceptor loader : mCustomLoader) {
                if (loader != null) {
                    result = loader.getImageLoader(type);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }

        return result;
    }

    private class BuildInLoaders implements ImageLoadGetInterceptor {

        @Override
        public AbsImageLoader getImageLoader(int type) {
            switch (type) {
                case 1:
                    return new PicassoImageLoader();
                case 2:
                    return new GlideImageLoader();
                case 3:
                    return new FrescoImageLoader();
                case 4:
                    return new UniversalImageLoader();
            }
            return null;
        }
    }

    public static interface ImageLoadGetInterceptor {

        /**
         * Get a image loader
         *
         * @param type
         * @return if there is no loader found, the null will be return
         */
        AbsImageLoader getImageLoader(int type);

    }

}
