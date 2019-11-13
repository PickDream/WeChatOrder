package com.pickdream.wechatorder.utils;

import java.net.URLEncoder;

public class QRCodeUtils {
    public static String generateQRCode(String url){
        return "http://tool.oschina.net/action/qrcode/generate?data="+ URLEncoder.encode(url)+
                "&output=image%2Fjpeg&error=L&type=0&margin=8&size=4";
    }
}
