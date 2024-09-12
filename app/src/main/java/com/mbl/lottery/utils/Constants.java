package com.mbl.lottery.utils;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String KEY_SHARE_PREFERENCES = "KEY_SHARE_PREFERENCES";
    public static final String KEY_EMPLOYEE = "KEY_EMPLOYEE";
    public static final String KEY_DATE_TIME_NOW = "KEY_DATE_TIME_NOW";
    public static final String KEY_DIFF_PRINT_SECOND = "30";

    public static String TICKET_SHOW_AMOUNT = "TICKET_SHOW_AMOUNT";
    public static String TICKET_NO_AMOUNT = "TICKET_NO_AMOUNT";
    public static final String BLUETOOTH_NAME = "BLUETOOTH_NAME";
    public static final String PRODUCT_ID = "PRODUCT_ID";

    public static final String ORDER_MODEL = "ORDER_MODEL";
    public static final String DRAW_MODEL = "DRAW_MODEL";
    public static final String MODE = "MODE";
    public static final String AREA = "AREA";
    public static final String IMAGE_BEFORE = "IMAGE_BEFORE";
    public static final String IMAGE_AFTER = "IMAGE_AFTER";

    public static final String KENO_EVEN = "CHAN";
    public static final String KENO_ODD = "LE";
    public static final String KENO_SMALL = "NHO";
    public static final String KENO_BIG = "LON";
    public static final String KENO_BIG_SMALL = "HOA_LN";
    public static final String KENO_EVEN_11_12 = "C1112";
    public static final String KENO_EVEN_ODD = "HOA";
    public static final String KENO_ODD_11_12 = "L1112";

    public static final int PRODUCT_NORMAL = 0;
    public static final int PRODUCT_MEGA = 1;
    public static final int PRODUCT_POWER = 2;
    public static final int PRODUCT_MAX3D = 4;
    public static final int PRODUCT_MAX3D_PLUS = 6;
    public static final int PRODUCT_KENO = 3;
    public static final int PRODUCT_MAX3D_PRO = 5;
    public static final int PRODUCT_LOTO235 = 12;
    public static final int PRODUCT_LOTO636 = 8;
    public static final int PRODUCT_LOTO234CAPSO = 13;

    public static final String CHAN = "CHAN";
    public static final String HOA = "HOA";
    public static final String LE = "LE";
    public static final String C1112 = "C1112";
    public static final String L1112 = "L1112";
    public static final String LON = "LON";
    public static final String HOA_LN = "HOA_LN";
    public static final String NHO = "NHO";

    //x,1,l,y,m,M,<,u,k,N,q,t,d,F,i,Z
    //x,Z,l,y,m,M,<,u,k,N,q,t,d,F,i,Z
    public static final List<String> POSKeyArray = Arrays.asList("x", "Z", "l", "y", "m", "M", "<", "u", "k", "N", "q", "t", "d", "F", "i", "Z");
    public static final long SymbolSpecial = 1450;
    public static final long SymbolBase = 500;
    public static final long SymbolNumber = 200;//150;

    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public  static  final String CHANNEL = "PRINTER";
}
