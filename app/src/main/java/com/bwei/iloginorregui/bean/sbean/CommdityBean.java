package com.bwei.iloginorregui.bean.sbean;

import java.util.List;

public class CommdityBean {


    private List<RxxpBean> rxxp;
    private List<PzshBean> pzsh;
    private List<MlssBean> mlss;

    public List<RxxpBean> getRxxp() {
        return rxxp;
    }

    public void setRxxp(List<RxxpBean> rxxp) {
        this.rxxp = rxxp;
    }

    public List<PzshBean> getPzsh() {
        return pzsh;
    }

    public void setPzsh(List<PzshBean> pzsh) {
        this.pzsh = pzsh;
    }

    public List<MlssBean> getMlss() {
        return mlss;
    }

    public void setMlss(List<MlssBean> mlss) {
        this.mlss = mlss;
    }

    public static class RxxpBean {


        private int id;
        private String name;
        private List<CommodityListBean> commodityList;

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

        public List<CommodityListBean> getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(List<CommodityListBean> commodityList) {
            this.commodityList = commodityList;
        }

        public static class CommodityListBean {

            private int commodityId;
            private String commodityName;
            private String masterPic;
            private int price;
            private int saleNum;

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public String getMasterPic() {
                return masterPic;
            }

            public void setMasterPic(String masterPic) {
                this.masterPic = masterPic;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }
        }
    }

    public static class PzshBean {

        private int id;
        private String name;
        private List<CommodityListBeanX> commodityList;

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

        public List<CommodityListBeanX> getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(List<CommodityListBeanX> commodityList) {
            this.commodityList = commodityList;
        }

        public static class CommodityListBeanX {
            /**
             * commodityId : 6
             * commodityName : 轻柔系自然裸妆假睫毛
             * masterPic : http://172.17.8.100/images/small/commodity/mzhf/cz/4/1.jpg
             * price : 39
             * saleNum : 0
             */

            private int commodityId;
            private String commodityName;
            private String masterPic;
            private int price;
            private int saleNum;

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public String getMasterPic() {
                return masterPic;
            }

            public void setMasterPic(String masterPic) {
                this.masterPic = masterPic;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }
        }
    }

    public static class MlssBean {
        /**
         * commodityList : [{"commodityId":32,"commodityName":"唐狮女鞋冬季女鞋休闲鞋子女士女鞋百搭帆布鞋女士休闲鞋子女款板鞋休闲女鞋帆布鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/fbx/1/1.jpg","price":88,"saleNum":0},{"commodityId":24,"commodityName":"百搭小白鞋 女款 时尚舒适板鞋","masterPic":"http://172.17.8.100/images/small/commodity/nx/bx/7/1.jpg","price":149,"saleNum":0}]
         * id : 1003
         * name : 魔力时尚
         */

        private int id;
        private String name;
        private List<CommodityListBeanXX> commodityList;

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

        public List<CommodityListBeanXX> getCommodityList() {
            return commodityList;
        }

        public void setCommodityList(List<CommodityListBeanXX> commodityList) {
            this.commodityList = commodityList;
        }

        public static class CommodityListBeanXX {


            private int commodityId;
            private String commodityName;
            private String masterPic;
            private int price;
            private int saleNum;

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public String getMasterPic() {
                return masterPic;
            }

            public void setMasterPic(String masterPic) {
                this.masterPic = masterPic;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(int saleNum) {
                this.saleNum = saleNum;
            }
        }
    }
}
