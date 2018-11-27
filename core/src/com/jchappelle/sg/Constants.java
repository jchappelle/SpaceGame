package com.jchappelle.sg;

public final class Constants {
    public static final float PIXELS_TO_METERS = 100f;
    public static final short CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
    public static final short CATEGORY_ENEMY =  0x0002; //  0000000000000010 in binary
    public static final short CATEGORY_BOUNDS = 0x0004; //  0000000000000100 in binary

    public static final short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_BOUNDS;
    public static final short MASK_ENEMY = CATEGORY_PLAYER;
    public static final short MASK_BOUNDS = CATEGORY_PLAYER;
}

