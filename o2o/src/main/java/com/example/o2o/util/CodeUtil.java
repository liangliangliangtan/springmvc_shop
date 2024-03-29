package com.example.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;


public class CodeUtil {
    /*
    * check the verification code inputted by the customer with the actual one
    * */
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String verifyCodeExpected = (String) request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");

        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected))
            return false;
        return true;
    }
}
