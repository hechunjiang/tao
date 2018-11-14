package com.application.sven.huinews.entity.response;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/7/11
 * effect:
 */
public class BookDetailsResponse extends BaseResponse {

    private BookDetails data;

    public BookDetails getData() {
        return data;
    }

    public void setData(BookDetails data) {
        this.data = data;
    }

    public static class BookDetails implements Serializable {


        /**
         * id : 1
         * cty_id : 6
         * title : 雪中悍刀行
         * author : 烽火戏诸侯
         * score : 4
         * all_click : 9867
         * size : 457.89万
         * description : 江湖是一张珠帘。 大人物小人物，是珠子，大故事小故事，是串线。 情义二字，则是那些珠子的精气神。 ———— 开始收官中。 最终章将以那一声“小二上酒”结尾
         * pic : http://www.nr6h.cn/uploads/20180613/f8bbdbd796bb4201b5925a687b89c872.png
         * is_free : 1
         * is_vip : 1
         * is_buy : 1
         * last_up_time : 1531281383
         * book_status : 1
         * chapter_number : 6
         * cay_name : 武侠仙侠
         * lately : [{"id":23,"chapter_name":"第六章 走一个","is_gold":2},{"id":22,"chapter_name":"第五章 天下第一美人","is_gold":2},{"id":21,"chapter_name":"第四章 去那座山摘山楂","is_gold":2},{"id":20,"chapter_name":"第三章 两个酒窝","is_gold":2},{"id":17,"chapter_name":" 第二章 白狐儿脸","is_gold":1}]
         * recommen : [{"id":2,"title":"圣墟 ","pic":"http://www.nr6h.cn/uploads/20180613/e1652dd02be3ddd7e41cd5a24d2567f8.png"},{"id":5,"title":"重生完美时代","pic":"http://www.nr6h.cn/uploads/20180629/110cfbcbc016277c99a692c9c72ef526.png"},{"id":1,"title":"雪中悍刀行","pic":"http://www.nr6h.cn/uploads/20180613/f8bbdbd796bb4201b5925a687b89c872.png"},{"id":4,"title":"元尊","pic":"http://www.nr6h.cn/uploads/20180629/971235e9b0b5edc8c4bb02ac4d26befe.png"}]
         */

        private int id;
        private int cty_id;
        private String title;
        private String author;
        private int score;
        private int all_click;
        private String size;
        private String description;
        private String pic;
        private int is_free;
        private int is_vip;
        private int is_buy;
        private int last_up_time;
        private int book_status;
        private int chapter_number;
        private String cay_name;
        private List<LatelyBean> lately;
        private List<RecommenBean> recommen;
        private First first;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCty_id() {
            return cty_id;
        }

        public void setCty_id(int cty_id) {
            this.cty_id = cty_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getAll_click() {
            return all_click;
        }

        public void setAll_click(int all_click) {
            this.all_click = all_click;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getIs_free() {
            return is_free;
        }

        public void setIs_free(int is_free) {
            this.is_free = is_free;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getIs_buy() {
            return is_buy;
        }

        public void setIs_buy(int is_buy) {
            this.is_buy = is_buy;
        }

        public int getLast_up_time() {
            return last_up_time;
        }

        public void setLast_up_time(int last_up_time) {
            this.last_up_time = last_up_time;
        }

        public int getBook_status() {
            return book_status;
        }

        public void setBook_status(int book_status) {
            this.book_status = book_status;
        }

        public int getChapter_number() {
            return chapter_number;
        }

        public void setChapter_number(int chapter_number) {
            this.chapter_number = chapter_number;
        }

        public String getCay_name() {
            return cay_name;
        }

        public void setCay_name(String cay_name) {
            this.cay_name = cay_name;
        }

        public List<LatelyBean> getLately() {
            return lately;
        }

        public void setLately(List<LatelyBean> lately) {
            this.lately = lately;
        }

        public List<RecommenBean> getRecommen() {
            return recommen;
        }


        public void setRecommen(List<RecommenBean> recommen) {
            this.recommen = recommen;
        }


        public First getFirst() {
            return first;
        }

        public void setFirst(First first) {
            this.first = first;
        }

        public static class LatelyBean implements Serializable {
            /**
             * id : 23
             * chapter_name : 第六章 走一个
             * is_gold : 2
             */

            private int id;
            private String chapter_name;
            private int is_gold;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getChapter_name() {
                return chapter_name;
            }

            public void setChapter_name(String chapter_name) {
                this.chapter_name = chapter_name;
            }

            public int getIs_gold() {
                return is_gold;
            }

            public void setIs_gold(int is_gold) {
                this.is_gold = is_gold;
            }
        }

        public static class RecommenBean {
            /**
             * id : 2
             * title : 圣墟
             * pic : http://www.nr6h.cn/uploads/20180613/e1652dd02be3ddd7e41cd5a24d2567f8.png
             */

            private int id;
            private String title;
            private String pic;

            public static RecommenBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), RecommenBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }

        public static class First implements Serializable {
            private int id;
            private String chapter_name;
            private int is_gold;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getChapter_name() {
                return chapter_name;
            }

            public void setChapter_name(String chapter_name) {
                this.chapter_name = chapter_name;
            }

            public int getIs_gold() {
                return is_gold;
            }

            public void setIs_gold(int is_gold) {
                this.is_gold = is_gold;
            }
        }
    }
}
