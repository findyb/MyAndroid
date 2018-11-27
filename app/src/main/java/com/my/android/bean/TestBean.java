package com.my.android.bean;

/**
 * Created by Administrator on 2017/11/8.
 */

public class TestBean {


    /**
     * code : 0
     * msg : 获取成功
     * data : {"appToken":"886415246695468eb89f3ca5e6ae7aff","secret":"51a3194286054de980071dc65f996ac3","version":"2.0","forcedVersion":"1.0"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appToken : 886415246695468eb89f3ca5e6ae7aff
         * secret : 51a3194286054de980071dc65f996ac3
         * version : 2.0
         * forcedVersion : 1.0
         */

        private String appToken;
        private String secret;
        private String version;
        private String forcedVersion;

        public String getAppToken() {
            return appToken;
        }

        public void setAppToken(String appToken) {
            this.appToken = appToken;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getForcedVersion() {
            return forcedVersion;
        }

        public void setForcedVersion(String forcedVersion) {
            this.forcedVersion = forcedVersion;
        }
    }
}
