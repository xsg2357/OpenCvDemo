package com.yx.opencvdemo.opengles;

import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import static com.yx.opencvdemo.constants.AppConstants.LOG_TAG;

/**
 * *******************************************
 * 标题 :  检查设备是否支持OpenGl 3.x          *
 * 编辑 : 向绍谷                               *
 * 日期 : 2020/8/18                             *
 * 描述 :                                     *
 * *******************************************
 */
public  class OpenGlEsContextFactory implements GLSurfaceView.EGLContextFactory {

    private static double glVersion = 3.0;

    private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

    @Override
    public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
        Log.w(LOG_TAG, "creating OpenGL ES " + glVersion + " context");
        int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, (int) glVersion,
                EGL10.EGL_NONE };
        // attempt to create a OpenGL ES 3.0 context
        EGLContext context = egl.eglCreateContext(
                display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
        return context; // returns null if 3.0 is not supported;
    }

    @Override
    public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {

    }
}
