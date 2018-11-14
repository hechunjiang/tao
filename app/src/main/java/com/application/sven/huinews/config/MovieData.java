package com.application.sven.huinews.config;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * auther: sunfuyi
 * data: 2018/6/11
 * effect:
 */
public class MovieData {

    /**
     * timestamp : 20180611160239
     * code : A00000
     * data : {"fsc":7,"vidl":[{"drmType":1,"dr":-1,"m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/b87cfc8462656f1b78f02fb07057b736/afbe8fd3d73448c9//20180510/8e/c4/1a244fe8f2b9e72df989891168cf14ac.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=b7f7a7070e49c879a87a960bf3e4aa3c&qypid=1007619200_04000000001000000000_1&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8756bebd66017c7177cfddb5c4c98a91&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/b87cfc8462656f1b78f02fb07057b736/afbe8fd3d73448c9//20180510/8e/c4/1a244fe8f2b9e72df989891168cf14ac.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=b7f7a7070e49c879a87a960bf3e4aa3c&qypid=1007619200_04000000001000000000_1&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8756bebd66017c7177cfddb5c4c98a91&np_tag=nginx_part_tag","code":2,"vd":1,"unencryptedDuration":0,"screenSize":"640x360","vid":"b87cfc8462656f1b78f02fb07057b736"},{"drmType":1,"dr":-1,"m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/f426a6e6faf28282a99b84b624cd9d4a/afbe8fd3d73448c9//20180510/dd/bf/4fe2ca6baac36623f24c008d91701a2a.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=654668df3e45f86507633c767bf6034a&qypid=1007619200_04000000001000000000_96&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=673733dd20d100012beb592c023fc466&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/f426a6e6faf2     8282a99b84b624cd9d4a/afbe8fd3d73448c9//20180510/dd/bf/4fe2ca6baac36623f24c008d91701a2a.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=654668df3e45f86507633c767bf6034a&qypid=1007619200_04000000001000000000_96&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=673733dd20d100012beb592c023fc466&np_tag=nginx_part_tag","code":2,"vd":96,"unencryptedDuration":0,"screenSize":"384x216","vid":"f426a6e6faf28282a99b84b624cd9d4a"},{"drmType":1,"fileFormat":"H265","dr":-1,"vid":"07467f990a09101786f5d2432e6058dd","m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/07467f990a09101786f5d2432e6058dd/afbe8fd3d73448c9//20180510/8d/d8/feada62a12f8a6951fc815fe9c4b29f9.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=56737d41c3695b702e3413f8bd83a2d7&qypid=1007619200_04000000001000000000_21&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=1&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8e56d4b00415898d74e5f35e8df32cab&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/07467f990a09101786f5d2432e6058dd/afbe8fd3d73448c9//20180510/8d/d8/feada62a12f8a6951fc815fe9c4b29f9.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=56737d41c3695b702e3413f8bd83a2d7&qypid=1007619200_04000000001000000000_21&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=1&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8e56d4b00415898d74e5f35e8df32cab&np_tag=nginx_part_tag","vd":21,"unencryptedDuration":0,"code":1,"screenSize":"896x504"},{"drmType":1,"dr":-1,"m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/87dbcb12c7dda4732c8110f82bb     56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/87dbcb12c7dda4732c8110f82bb56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag","code":2,"vd":2,"unencryptedDuration":0,"screenSize":"896x504","vid":"87dbcb12c7dda4732c8110f82bb56ef1"}],"pano":{"type":1,"rType":0},"bossStatus":0,"ugc":0,"audio":[{"bit":{},"lid":1,"name":"国语","ispre":1}],"vid":"87dbcb12c7dda4732c8110f82bb56ef1","cacheTime":1528683496726,"lgh":[],"m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/87dbcb12c7dda4732c8110f82bb56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag","um":0,"tipType":"","m3u":"http://cache.m.iqiyi.com/mus/214499601/87dbcb12c7dda4732c8110f     82bb56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag","ad":1,"exclusive":1,"tvid":1007619200,"screenSize":"896x504","isdol":false,"ff":"f4v","adDuration":0,"head":90,"duration":2555,"rp":0,"aid":214499601,"vd":2,"rTime":"","clientIp":"218.89.239.169","previewType":"","prv":"","isProduced":0,"code":2,"wmarkPos":0,"tail":2463,"thdt":1,"cid":2,"acf":"1:English (aac) (2.0 ch) (iso639-2:eng), 48000Hz, 255999bps, dolby_flag:2, id 0x:1","du":"http://data.video.ptqy.gitv.tv/videos","ds":"A00012","dr":-1,"vipTypes":[0]}
     */

    private String timestamp;
    private String code;
    private DataBean data;



    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fsc : 7
         * vidl : [{"drmType":1,"dr":-1,"m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/b87cfc8462656f1b78f02fb07057b736/afbe8fd3d73448c9//20180510/8e/c4/1a244fe8f2b9e72df989891168cf14ac.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=b7f7a7070e49c879a87a960bf3e4aa3c&qypid=1007619200_04000000001000000000_1&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8756bebd66017c7177cfddb5c4c98a91&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/b87cfc8462656f1b78f02fb07057b736/afbe8fd3d73448c9//20180510/8e/c4/1a244fe8f2b9e72df989891168cf14ac.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=b7f7a7070e49c879a87a960bf3e4aa3c&qypid=1007619200_04000000001000000000_1&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8756bebd66017c7177cfddb5c4c98a91&np_tag=nginx_part_tag","code":2,"vd":1,"unencryptedDuration":0,"screenSize":"640x360","vid":"b87cfc8462656f1b78f02fb07057b736"},{"drmType":1,"dr":-1,"m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/f426a6e6faf28282a99b84b624cd9d4a/afbe8fd3d73448c9//20180510/dd/bf/4fe2ca6baac36623f24c008d91701a2a.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=654668df3e45f86507633c767bf6034a&qypid=1007619200_04000000001000000000_96&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=673733dd20d100012beb592c023fc466&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/f426a6e6faf2     8282a99b84b624cd9d4a/afbe8fd3d73448c9//20180510/dd/bf/4fe2ca6baac36623f24c008d91701a2a.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=654668df3e45f86507633c767bf6034a&qypid=1007619200_04000000001000000000_96&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=673733dd20d100012beb592c023fc466&np_tag=nginx_part_tag","code":2,"vd":96,"unencryptedDuration":0,"screenSize":"384x216","vid":"f426a6e6faf28282a99b84b624cd9d4a"},{"drmType":1,"fileFormat":"H265","dr":-1,"vid":"07467f990a09101786f5d2432e6058dd","m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/07467f990a09101786f5d2432e6058dd/afbe8fd3d73448c9//20180510/8d/d8/feada62a12f8a6951fc815fe9c4b29f9.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=56737d41c3695b702e3413f8bd83a2d7&qypid=1007619200_04000000001000000000_21&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=1&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8e56d4b00415898d74e5f35e8df32cab&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/07467f990a09101786f5d2432e6058dd/afbe8fd3d73448c9//20180510/8d/d8/feada62a12f8a6951fc815fe9c4b29f9.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=56737d41c3695b702e3413f8bd83a2d7&qypid=1007619200_04000000001000000000_21&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=1&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8e56d4b00415898d74e5f35e8df32cab&np_tag=nginx_part_tag","vd":21,"unencryptedDuration":0,"code":1,"screenSize":"896x504"},{"drmType":1,"dr":-1,"m3utx":"http://cache.m.iqiyi.com/mus/text/214499601/87dbcb12c7dda4732c8110f82bb     56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag","m3u":"http://cache.m.iqiyi.com/mus/214499601/87dbcb12c7dda4732c8110f82bb56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag","code":2,"vd":2,"unencryptedDuration":0,"screenSize":"896x504","vid":"87dbcb12c7dda4732c8110f82bb56ef1"}]
         * pano : {"type":1,"rType":0}
         * bossStatus : 0
         * ugc : 0
         * audio : [{"bit":{},"lid":1,"name":"国语","ispre":1}]
         * vid : 87dbcb12c7dda4732c8110f82bb56ef1
         * cacheTime : 1528683496726
         * lgh : []
         * m3utx : http://cache.m.iqiyi.com/mus/text/214499601/87dbcb12c7dda4732c8110f82bb56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag
         * um : 0
         * tipType :
         * m3u : http://cache.m.iqiyi.com/mus/214499601/87dbcb12c7dda4732c8110f     82bb56ef1/afbe8fd3d73448c9//20180510/87/6a/fd2377f489c98ef62423a8905302a05f.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159863&qd_p=da59efa9&qd_asc=0f921ef5acc9d3db7ca6728f7fd9bb41&qypid=1007619200_04000000001000000000_2&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=be9dbb3261d436d617d6d21b307b13a0&np_tag=nginx_part_tag
         * ad : 1
         * exclusive : 1
         * tvid : 1007619200
         * screenSize : 896x504
         * isdol : false
         * ff : f4v
         * adDuration : 0
         * head : 90
         * duration : 2555
         * rp : 0
         * aid : 214499601
         * vd : 2
         * rTime :
         * clientIp : 218.89.239.169
         * previewType :
         * prv :
         * isProduced : 0
         * code : 2
         * wmarkPos : 0
         * tail : 2463
         * thdt : 1
         * cid : 2
         * acf : 1:English (aac) (2.0 ch) (iso639-2:eng), 48000Hz, 255999bps, dolby_flag:2, id 0x:1
         * du : http://data.video.ptqy.gitv.tv/videos
         * ds : A00012
         * dr : -1
         * vipTypes : [0]
         */

        private int fsc;
        private PanoBean pano;
        private int bossStatus;
        private int ugc;
        private String vid;
        private long cacheTime;
        private String m3utx;
        private int um;
        private String tipType;
        private String m3u;
        private int ad;
        private int exclusive;
        private int tvid;
        private String screenSize;
        private boolean isdol;
        private String ff;
        private int adDuration;
        private int head;
        private int duration;
        private int rp;
        private int aid;
        private int vd;
        private String rTime;
        private String clientIp;
        private String previewType;
        private String prv;
        private int isProduced;
        private int code;
        private int wmarkPos;
        private int tail;
        private int thdt;
        private int cid;
        private String acf;
        private String du;
        private String ds;
        private int dr;
        private List<VidlBean> vidl;
        private List<AudioBean> audio;
        private List<?> lgh;
        private List<Integer> vipTypes;

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public int getFsc() {
            return fsc;
        }

        public void setFsc(int fsc) {
            this.fsc = fsc;
        }

        public PanoBean getPano() {
            return pano;
        }

        public void setPano(PanoBean pano) {
            this.pano = pano;
        }

        public int getBossStatus() {
            return bossStatus;
        }

        public void setBossStatus(int bossStatus) {
            this.bossStatus = bossStatus;
        }

        public int getUgc() {
            return ugc;
        }

        public void setUgc(int ugc) {
            this.ugc = ugc;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public long getCacheTime() {
            return cacheTime;
        }

        public void setCacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
        }

        public String getM3utx() {
            return m3utx;
        }

        public void setM3utx(String m3utx) {
            this.m3utx = m3utx;
        }

        public int getUm() {
            return um;
        }

        public void setUm(int um) {
            this.um = um;
        }

        public String getTipType() {
            return tipType;
        }

        public void setTipType(String tipType) {
            this.tipType = tipType;
        }

        public String getM3u() {
            return m3u;
        }

        public void setM3u(String m3u) {
            this.m3u = m3u;
        }

        public int getAd() {
            return ad;
        }

        public void setAd(int ad) {
            this.ad = ad;
        }

        public int getExclusive() {
            return exclusive;
        }

        public void setExclusive(int exclusive) {
            this.exclusive = exclusive;
        }

        public int getTvid() {
            return tvid;
        }

        public void setTvid(int tvid) {
            this.tvid = tvid;
        }

        public String getScreenSize() {
            return screenSize;
        }

        public void setScreenSize(String screenSize) {
            this.screenSize = screenSize;
        }

        public boolean isIsdol() {
            return isdol;
        }

        public void setIsdol(boolean isdol) {
            this.isdol = isdol;
        }

        public String getFf() {
            return ff;
        }

        public void setFf(String ff) {
            this.ff = ff;
        }

        public int getAdDuration() {
            return adDuration;
        }

        public void setAdDuration(int adDuration) {
            this.adDuration = adDuration;
        }

        public int getHead() {
            return head;
        }

        public void setHead(int head) {
            this.head = head;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getRp() {
            return rp;
        }

        public void setRp(int rp) {
            this.rp = rp;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getVd() {
            return vd;
        }

        public void setVd(int vd) {
            this.vd = vd;
        }

        public String getRTime() {
            return rTime;
        }

        public void setRTime(String rTime) {
            this.rTime = rTime;
        }

        public String getClientIp() {
            return clientIp;
        }

        public void setClientIp(String clientIp) {
            this.clientIp = clientIp;
        }

        public String getPreviewType() {
            return previewType;
        }

        public void setPreviewType(String previewType) {
            this.previewType = previewType;
        }

        public String getPrv() {
            return prv;
        }

        public void setPrv(String prv) {
            this.prv = prv;
        }

        public int getIsProduced() {
            return isProduced;
        }

        public void setIsProduced(int isProduced) {
            this.isProduced = isProduced;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getWmarkPos() {
            return wmarkPos;
        }

        public void setWmarkPos(int wmarkPos) {
            this.wmarkPos = wmarkPos;
        }

        public int getTail() {
            return tail;
        }

        public void setTail(int tail) {
            this.tail = tail;
        }

        public int getThdt() {
            return thdt;
        }

        public void setThdt(int thdt) {
            this.thdt = thdt;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getAcf() {
            return acf;
        }

        public void setAcf(String acf) {
            this.acf = acf;
        }

        public String getDu() {
            return du;
        }

        public void setDu(String du) {
            this.du = du;
        }

        public String getDs() {
            return ds;
        }

        public void setDs(String ds) {
            this.ds = ds;
        }

        public int getDr() {
            return dr;
        }

        public void setDr(int dr) {
            this.dr = dr;
        }

        public List<VidlBean> getVidl() {
            return vidl;
        }

        public void setVidl(List<VidlBean> vidl) {
            this.vidl = vidl;
        }

        public List<AudioBean> getAudio() {
            return audio;
        }

        public void setAudio(List<AudioBean> audio) {
            this.audio = audio;
        }

        public List<?> getLgh() {
            return lgh;
        }

        public void setLgh(List<?> lgh) {
            this.lgh = lgh;
        }

        public List<Integer> getVipTypes() {
            return vipTypes;
        }

        public void setVipTypes(List<Integer> vipTypes) {
            this.vipTypes = vipTypes;
        }

        public static class PanoBean {
            /**
             * type : 1
             * rType : 0
             */

            private int type;
            private int rType;

            public static PanoBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), PanoBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getRType() {
                return rType;
            }

            public void setRType(int rType) {
                this.rType = rType;
            }
        }

        public static class VidlBean {
            /**
             * drmType : 1
             * dr : -1
             * m3utx : http://cache.m.iqiyi.com/mus/text/214499601/b87cfc8462656f1b78f02fb07057b736/afbe8fd3d73448c9//20180510/8e/c4/1a244fe8f2b9e72df989891168cf14ac.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=b7f7a7070e49c879a87a960bf3e4aa3c&qypid=1007619200_04000000001000000000_1&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8756bebd66017c7177cfddb5c4c98a91&np_tag=nginx_part_tag
             * m3u : http://cache.m.iqiyi.com/mus/214499601/b87cfc8462656f1b78f02fb07057b736/afbe8fd3d73448c9//20180510/8e/c4/1a244fe8f2b9e72df989891168cf14ac.m3u8?qd_originate=tmts_py&tvid=1007619200&bossStatus=0&qd_vip=0&px=&qd_src=2_20_201&prv=&previewType=&previewTime=&from=&qd_time=1528704159862&qd_p=da59efa9&qd_asc=b7f7a7070e49c879a87a960bf3e4aa3c&qypid=1007619200_04000000001000000000_1&qd_k=e18c8569df82f1bcb7b75482f2fbe204&isdol=0&code=2&iswb=0&sgti=12_2123ef4383823698d77ff336ca8a5136_1528690963&vf=8756bebd66017c7177cfddb5c4c98a91&np_tag=nginx_part_tag
             * code : 2
             * vd : 1
             * unencryptedDuration : 0
             * screenSize : 640x360
             * vid : b87cfc8462656f1b78f02fb07057b736
             * fileFormat : H265
             */

            private int drmType;
            private int dr;
            private String m3utx;
            private String m3u;
            private int code;
            private int vd;
            private int unencryptedDuration;
            private String screenSize;
            private String vid;
            private String fileFormat;

            public static VidlBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), VidlBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public int getDrmType() {
                return drmType;
            }

            public void setDrmType(int drmType) {
                this.drmType = drmType;
            }

            public int getDr() {
                return dr;
            }

            public void setDr(int dr) {
                this.dr = dr;
            }

            public String getM3utx() {
                return m3utx;
            }

            public void setM3utx(String m3utx) {
                this.m3utx = m3utx;
            }

            public String getM3u() {
                return m3u;
            }

            public void setM3u(String m3u) {
                this.m3u = m3u;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public int getVd() {
                return vd;
            }

            public void setVd(int vd) {
                this.vd = vd;
            }

            public int getUnencryptedDuration() {
                return unencryptedDuration;
            }

            public void setUnencryptedDuration(int unencryptedDuration) {
                this.unencryptedDuration = unencryptedDuration;
            }

            public String getScreenSize() {
                return screenSize;
            }

            public void setScreenSize(String screenSize) {
                this.screenSize = screenSize;
            }

            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getFileFormat() {
                return fileFormat;
            }

            public void setFileFormat(String fileFormat) {
                this.fileFormat = fileFormat;
            }
        }

        public static class AudioBean {
            /**
             * bit : {}
             * lid : 1
             * name : 国语
             * ispre : 1
             */

            private BitBean bit;
            private int lid;
            private String name;
            private int ispre;

            public static AudioBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), AudioBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public BitBean getBit() {
                return bit;
            }

            public void setBit(BitBean bit) {
                this.bit = bit;
            }

            public int getLid() {
                return lid;
            }

            public void setLid(int lid) {
                this.lid = lid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIspre() {
                return ispre;
            }

            public void setIspre(int ispre) {
                this.ispre = ispre;
            }

            public static class BitBean {
                public static BitBean objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), BitBean.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            }
        }
    }
}
