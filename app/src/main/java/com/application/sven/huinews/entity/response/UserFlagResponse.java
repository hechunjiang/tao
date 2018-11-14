package com.application.sven.huinews.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/5/23
 * effect:
 */
public class UserFlagResponse extends BaseResponse {

    private UserFlag data;

    public UserFlag getData() {
        return data;
    }

    public void setData(UserFlag data) {
        this.data = data;
    }

    public static class UserFlag implements Serializable {

        /**
         * is_have_apprentice : 2
         * invitation_code : S80239
         * multiple : 2
         * next_need_apprentice_num : 2
         * next_need_apprentice_difference : 2
         * grade : [{"id":1,"name":"普通师傅","need_apprentice_num":2,"multiple":2},{"id":2,"name":"铜牌师傅","need_apprentice_num":4,"multiple":5},{"id":3,"name":"银牌师傅","need_apprentice_num":7,"multiple":10},{"id":5,"name":"金牌师傅","need_apprentice_num":14,"multiple":21}]
         * now_grade : 1
         */

        private int is_have_apprentice;
        private String invitation_code;
        private int multiple;
        private int next_need_apprentice_num;
        private int next_need_apprentice_difference;
        private int now_grade;
        private List<GradeBean> grade;

        public int getIs_have_apprentice() {
            return is_have_apprentice;
        }

        public void setIs_have_apprentice(int is_have_apprentice) {
            this.is_have_apprentice = is_have_apprentice;
        }

        public String getInvitation_code() {
            return invitation_code;
        }

        public void setInvitation_code(String invitation_code) {
            this.invitation_code = invitation_code;
        }

        public int getMultiple() {
            return multiple;
        }

        public void setMultiple(int multiple) {
            this.multiple = multiple;
        }

        public int getNext_need_apprentice_num() {
            return next_need_apprentice_num;
        }

        public void setNext_need_apprentice_num(int next_need_apprentice_num) {
            this.next_need_apprentice_num = next_need_apprentice_num;
        }

        public int getNext_need_apprentice_difference() {
            return next_need_apprentice_difference;
        }

        public void setNext_need_apprentice_difference(int next_need_apprentice_difference) {
            this.next_need_apprentice_difference = next_need_apprentice_difference;
        }

        public int getNow_grade() {
            return now_grade;
        }

        public void setNow_grade(int now_grade) {
            this.now_grade = now_grade;
        }

        public List<GradeBean> getGrade() {
            return grade;
        }

        public void setGrade(List<GradeBean> grade) {
            this.grade = grade;
        }

        public static class GradeBean {
            /**
             * id : 1
             * name : 普通师傅
             * need_apprentice_num : 2
             * multiple : 2
             */

            private int id;
            private String name;
            private int need_apprentice_num;
            private int multiple;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getNeed_apprentice_num() {
                return need_apprentice_num;
            }

            public void setNeed_apprentice_num(int need_apprentice_num) {
                this.need_apprentice_num = need_apprentice_num;
            }

            public int getMultiple() {
                return multiple;
            }

            public void setMultiple(int multiple) {
                this.multiple = multiple;
            }
        }
    }
}
