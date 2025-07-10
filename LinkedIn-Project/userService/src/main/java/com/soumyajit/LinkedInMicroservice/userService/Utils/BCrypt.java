package com.soumyajit.LinkedInMicroservice.userService.Utils;

public class BCrypt {
    public static String hashString(String s){
        return org.mindrot.jbcrypt.BCrypt.hashpw(s, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    public static boolean match(String passwordPlainText, String passwordHashed){
        return org.mindrot.jbcrypt.BCrypt.checkpw(passwordPlainText,passwordHashed);
    }
}
