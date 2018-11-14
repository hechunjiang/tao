package com.application.sven.huinews.utils.rsa;

import com.application.sven.huinews.utils.LogUtil;

import java.io.InputStream;
import java.security.PrivateKey;

import static com.application.sven.huinews.utils.rsa.RSAUtils.decryptData;

/**
 * auther: sunfuyi
 * data: 2018/5/19
 * effect:
 */
public class RSAConstant {
    /**
     * 私钥
     **/
    private static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMDw5AbpogrROOfR\n" +
            "swtY6KY/rtdq6E2YYWgOuLSKA47S30bzPXgtivyG7Nz1Zb/X8ItuBDsmsM7b9bBz\n" +
            "KNiJPp43EBgbdLX8neL4+CbqY/GzCNqPC1hSMDLWvZWUNuTRuqg38Rizf14Ei49B\n" +
            "pfoSIspUEkqSNRn9sWTwJBDgdUf7AgMBAAECgYATzqcaLpH8Gk6FPfnriE0aBB+m\n" +
            "7cq7bJFTZW+A2UpUN0HEvuPomoRSOGfIp0iy0TBYoAq/J9Gu6tXmL2xaDnznA3/1\n" +
            "nHpg8RZ59WKGTxeZa3SmQvbDzvsSiTIVTJhZs+YSvnDMKYT/FQJbMh+urPMujQBo\n" +
            "vknic8xcjRqZ5JgAAQJBAOmNmkQqPxCGjgTMJ4CwzHF/pGtdvmVE+SELVQVc+qqe\n" +
            "b52YAkqgjAbBCfWuJYV5POP3gN+bK2gn0jvLoKnU1/sCQQDTfA6Q+ZeqXOxl2MGO\n" +
            "6obaHRWVVRFV+rEtJkkhqUaHTxuDYh7YPI1s15ohPh27xghoiMMM6HBYs98NkRhF\n" +
            "xlABAkB37bu6pwrtPHru4bqjLaY93fKbI28iZ3GaU0xcTat2M5dTEHyUv65HxZaM\n" +
            "rtkbme73jN+DcxWuDUy6YczliKNDAkEAtEHlWISlVTxm8lhSgrI7xuVLZEFhyVso\n" +
            "cUdUxjA2QVaYKOAVUlIqaT9LzFHZnhcpS5BmUDY+rByEXlfxzGdAAQJBAMCvzKks\n" +
            "KQtycqrj67hDwtCb4HYZLsZo6kYpaChIcVMLz5VjqGNcEgFCCTNjhf0+ghJWnrD3\n" +
            "XzxwEjOY3GecmR0=";

    /**
     * 公钥
     */
    private static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDA8OQG6aIK0Tjn0bMLWOimP67X\n" +
            "auhNmGFoDri0igOO0t9G8z14LYr8huzc9WW/1/CLbgQ7JrDO2/WwcyjYiT6eNxAY\n" +
            "G3S1/J3i+Pgm6mPxswjajwtYUjAy1r2VlDbk0bqoN/EYs39eBIuPQaX6EiLKVBJK\n" +
            "kjUZ/bFk8CQQ4HVH+wIDAQAB";


    public static void decryptRsaStr() {
        try {
            String encryptContent = "h01tVEij+SlBqFa/kbiUDYc06UUE9G7LxCqHqucQYI3hOYKUCBXTLup61e15rHDBUaEl/85I/PsHR+OzmLMRroA4RVp63M8e01o3mid52GIPaH0/2wPbKpDp8JeaVBVD8GVAcrWXkg49MDckY4Gf122V/sNDIE/ufM6XGiM/MkM=";
            //从字符串中得到私钥
            PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);

            // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
            byte[] decryptByte = decryptData(Base64Utils.decode(encryptContent), privateKey);
            String decryptStr = new String(decryptByte);
            LogUtil.showLog("Rsa deCode:" + decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
