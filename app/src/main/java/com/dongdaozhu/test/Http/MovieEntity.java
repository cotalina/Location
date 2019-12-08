package com.dongdaozhu.test.Http;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2019/9/10.
 */

public class MovieEntity
{

    /**
     * status : 200
     * msg : ok
     * count : 1
     * result : [{"lat":[39.897806,39.8992093352,39.9054450838,39.89917624,39.898656,39.89917624],"lng":[116.413845,116.4200838182,116.4265108629,116.42004557,116.409255,116.42004557],"address":"北京市东城区崇文门外街道国瑞城西区","rids":"156110101012010","rid":"110101"}]
     * match : 1
     */

    private int status;
    private String msg;
    private int count;
    private String match;
    private List<ResultBean> result;

    public static MovieEntity objectFromData(String str) {

        return new Gson().fromJson(str, MovieEntity.class);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * lat : [39.897806,39.8992093352,39.9054450838,39.89917624,39.898656,39.89917624]
         * lng : [116.413845,116.4200838182,116.4265108629,116.42004557,116.409255,116.42004557]
         * address : 北京市东城区崇文门外街道国瑞城西区
         * rids : 156110101012010
         * rid : 110101
         */

        private String address;
        private String rids;
        private String rid;
        private List<Double> lat;
        private List<Double> lng;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRids() {
            return rids;
        }

        public void setRids(String rids) {
            this.rids = rids;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public List<Double> getLat() {
            return lat;
        }

        public void setLat(List<Double> lat) {
            this.lat = lat;
        }

        public List<Double> getLng() {
            return lng;
        }

        public void setLng(List<Double> lng) {
            this.lng = lng;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "address='" + address + '\'' +
                    ", rids='" + rids + '\'' +
                    ", rid='" + rid + '\'' +
                    ", lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", match='" + match + '\'' +
                ", result=" + result +
                '}';
    }
}