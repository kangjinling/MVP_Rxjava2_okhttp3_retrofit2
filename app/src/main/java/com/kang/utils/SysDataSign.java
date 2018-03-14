package com.kang.utils;

/**
* @author ：kangjinling
* 邮箱 ：401205099@qq.com
* 功能描述 ： 用户信息加密
*
*/

public final class SysDataSign {

    public static String unenc(String encryptedPassword) {
        return new String(Base64.base64ToByteArray(encryptedPassword));
    }

    public static String enc(String pass) {
        return Base64.byteArrayToBase64String(pass.getBytes());
    }

    public static class Base64 {
        public static String byteArrayToBase64String(byte[] byteArray) {
            String base64String = "";
            int totalLength = byteArray.length;
            int groupNumber = totalLength / 3;
            int residualNumber = totalLength % 3;
            for (int i = 0; i < groupNumber; i++) {
                int j = i * 3;
                base64String += toBase64(byteArray[j], byteArray[j + 1],
                        byteArray[j + 2]);
            }
            if (residualNumber == 1) {
                base64String += toBase64(byteArray[totalLength - 1]);
            } else if (residualNumber == 2) {
                base64String += toBase64(byteArray[totalLength - 2],
                        byteArray[totalLength - 1]);
            } else {
            }
            return base64String;
        }

        public static byte[] base64ToByteArray(String base64String)
                throws NumberFormatException {
            String t = null;
            if (base64String.indexOf('=') != -1) {
                t = base64String.substring(0, base64String.indexOf('='));
            } else {
                t = base64String;
            }

            int len = t.length();
            int arrayLength = 3 * (len / 4);
            switch (len % 4) {
                case 1:
                    throw new NumberFormatException();
                case 2:
                    len += 2;
                    arrayLength += 1;
                    t += "==";
                    break;
                case 3:
                    len += 1;
                    arrayLength += 2;
                    t += "=";
                    break;
            }
            byte[] b = new byte[arrayLength];
            for (int i = 0, n = len / 4; i < n; i++) {
                byte[] temp = base64ToBytes(t.substring(4 * i, 4 * (i + 1)));
                for (int j = 0; j < temp.length; j++) {
                    b[3 * i + j] = temp[j];
                }
            }
            return b;
        }

        private static byte[] base64ToBytes(String base64String) {
            int arrayLen = 0;
            int totalLength = base64String.length();
            for (int i = 0; i < totalLength; i++) {
                if (base64String.charAt(i) != '=') {
                    arrayLen++;
                }
            }
            int[] digit = new int[arrayLen];
            for (int i = 0; i < arrayLen; i++) {
                char c = base64String.charAt(i);
                if (c >= 'A' && c <= 'Z')
                    digit[i] = c - 'A';
                else if (c >= 'a' && c <= 'z')
                    digit[i] = c - 'a' + 26;
                else if (c >= '0' && c <= '9')
                    digit[i] = c - '0' + 52;
                else if (c == '+')
                    digit[i] = 62;
                else if (c == '/')
                    digit[i] = 63;
                else {
                }
            }
            byte[] b = new byte[arrayLen - 1];
            switch (arrayLen) {
                case 4:
                    b[2] = (byte) ((((digit[2]) & 0x03) << 6) | digit[3]);
                case 3:
                    b[1] = (byte) ((((digit[1]) & 0x0F) << 4) | ((digit[2] & 0x3C) >>> 2));
                case 2:
                    b[0] = (byte) ((digit[0] << 2) | ((digit[1] & 0x30) >>> 4));
            }
            return b;
        }

        private static String toBase64(byte b1, byte b2, byte b3) {
            int[] digit = new int[4];
            digit[0] = (b1 & 0xFC) >>> 2;
            digit[1] = (b1 & 0x03) << 4;
            digit[1] |= (b2 & 0xF0) >> 4;
            digit[2] = (b2 & 0x0F) << 2;
            digit[2] |= (b3 & 0xC0) >> 6;
            digit[3] = (b3 & 0x3F);

            String result = "";
            for (int i = 0, n = digit.length; i < n; i++) {
                result += base64Digit(digit[i]);
            }

            return result;
        }

        private static String toBase64(byte b1, byte b2) {
            int[] digit = new int[3];
            digit[0] = (b1 & 0xFC) >>> 2;
            digit[1] = (b1 & 0x03) << 4;
            digit[1] |= (b2 & 0xF0) >> 4;
            digit[2] = (b2 & 0x0F) << 2;

            String result = "";
            for (int i = 0; i < digit.length; i++) {
                result += base64Digit(digit[i]);
            }
            result += "=";

            return result;
        }

        private static String toBase64(byte b1) {
            int[] digit = new int[2];
            digit[0] = (b1 & 0xFC) >>> 2;
            digit[1] = (b1 & 0x03) << 4;

            String result = "";
            for (int i = 0; i < digit.length; i++) {
                result += base64Digit(digit[i]);
            }
            result += "==";

            return result;
        }

        private static char base64Digit(int i) {
            if (i < 26)
                return (char) ('A' + i);
            else if (i < 52)
                return (char) ('a' + (i - 26));
            else if (i < 62)
                return (char) ('0' + (i - 52));
            else if (i == 62)
                return '+';
            else
                return '/';
        }

        public static void main(String args[]) {
            String username = "abc123";
            String str = Base64.byteArrayToBase64String(username.getBytes());
            System.out.println(str);
            String t = "NjU0MzIx";
            //System.out.println(new String(Base64.base64ToByteArray(t)));

        }

    }

}
