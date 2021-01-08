package com.qgfun.go.view.render;

import android.content.Context;

import com.dueeeke.videoplayer.render.IRenderView;
import com.dueeeke.videoplayer.render.RenderViewFactory;
import com.dueeeke.videoplayer.render.TextureRenderView;

public class SurfaceRenderViewFactory extends RenderViewFactory {

    public static SurfaceRenderViewFactory create() {
        return new SurfaceRenderViewFactory();
    }

    @Override
    public IRenderView createRenderView(Context context) {
        return new SurfaceRenderView(context);
    }
}
