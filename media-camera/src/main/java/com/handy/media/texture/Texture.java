package com.handy.media.texture;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;

/**
 * date: 2020-12-15
 * description:
 */
public class Texture {

    /**
     * Android 内部使用的拓展纹理类型,如SurfaceTexture用到的纹理
     */
    public static final int TEXTURE_EXTERNAL_OES = 0x8D65;

    /**
     * 纹理资源ID
     */
    private final int textureId;

    /**
     * 纹理类型
     */
    public final int textureType;

    public Texture(int textureType) {
        this(generateTextureId(), textureType);
    }

    public Texture(int textureId, int textureType) {
        this.textureId = textureId;
        this.textureType = textureType;
    }

    public SurfaceTexture asSurfaceTexture() {
        if (textureType == TEXTURE_EXTERNAL_OES) {
            return new SurfaceTexture(textureId);
        } else {
            throw new RuntimeException("textureType is error");
        }
    }

    private static int generateTextureId() {
        int[] texture = {0};
        GLES20.glGenTextures(texture.length, texture, 0);
        return texture[0];
    }

}
