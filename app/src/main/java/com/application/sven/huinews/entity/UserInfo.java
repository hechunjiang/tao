package com.application.sven.huinews.entity;

import java.io.Serializable;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class UserInfo implements Serializable {

    /**
     * userMsg : {"nickname":"","headimg":"http://www.nr6h.cn/static/img/default_head.png","invitation_code":"S80239","gold_flag":45,"balance":"0.00","total_balance":"0.00","share_code_status":false,"ored_status":false,"redcash_status":false,"apprentice_status":false,"is_bind_wx":false,"min_draw":1,"max_draw":100}
     * login_flag : false
     */

    private UserMsg userMsg;
    private boolean login_flag;

    public UserMsg getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(UserMsg userMsg) {
        this.userMsg = userMsg;
    }

    public boolean isLogin_flag() {
        return login_flag;
    }

    public void setLogin_flag(boolean login_flag) {
        this.login_flag = login_flag;
    }

    public static class UserMsg implements Serializable {
        /**
         * nickname :
         * headimg : http://www.nr6h.cn/static/img/default_head.png
         * invitation_code : S80239
         * gold_flag : 45
         * balance : 0.00
         * total_balance : 0.00
         * share_code_status : false
         * ored_status : false
         * redcash_status : false
         * apprentice_status : false
         * is_bind_wx : false
         * min_draw : 1
         * max_draw : 100
         */

        private String nickname;
        private String headimg;
        private String invitation_code;
        private int gold_flag;
        private String balance;
        private String total_balance;
        private boolean share_code_status;
        private boolean ored_status;
        private boolean redcash_status;
        private boolean apprentice_status;
        private boolean is_bind_wx;
        private int min_draw;
        private int max_draw;
        private int diamond;
        private int is_deduction;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getInvitation_code() {
            return invitation_code;
        }

        public void setInvitation_code(String invitation_code) {
            this.invitation_code = invitation_code;
        }

        public int getGold_flag() {
            return gold_flag;
        }

        public void setGold_flag(int gold_flag) {
            this.gold_flag = gold_flag;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getTotal_balance() {
            return total_balance;
        }

        public void setTotal_balance(String total_balance) {
            this.total_balance = total_balance;
        }

        public boolean isShare_code_status() {
            return share_code_status;
        }

        public void setShare_code_status(boolean share_code_status) {
            this.share_code_status = share_code_status;
        }

        public boolean isOred_status() {
            return ored_status;
        }

        public void setOred_status(boolean ored_status) {
            this.ored_status = ored_status;
        }

        public boolean isRedcash_status() {
            return redcash_status;
        }

        public void setRedcash_status(boolean redcash_status) {
            this.redcash_status = redcash_status;
        }

        public boolean isApprentice_status() {
            return apprentice_status;
        }

        public void setApprentice_status(boolean apprentice_status) {
            this.apprentice_status = apprentice_status;
        }

        public boolean isIs_bind_wx() {
            return is_bind_wx;
        }

        public void setIs_bind_wx(boolean is_bind_wx) {
            this.is_bind_wx = is_bind_wx;
        }

        public int getMin_draw() {
            return min_draw;
        }

        public void setMin_draw(int min_draw) {
            this.min_draw = min_draw;
        }

        public int getMax_draw() {
            return max_draw;
        }

        public void setMax_draw(int max_draw) {
            this.max_draw = max_draw;
        }

        public int getDiamond() {
            return diamond;
        }

        public void setDiamond(int diamond) {
            this.diamond = diamond;
        }

        public int getIs_deduction() {
            return is_deduction;
        }

        public void setIs_deduction(int is_deduction) {
            this.is_deduction = is_deduction;
        }
    }
}
