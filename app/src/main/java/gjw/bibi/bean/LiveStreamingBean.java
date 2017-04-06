package gjw.bibi.bean;

import java.util.List;

/**
 * Created by 皇上 on 2017/3/22.
 */

public class LiveStreamingBean {

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BannerBean> banner;
        private List<EntranceIconsBean> entranceIcons;
        private List<PartitionsBean> partitions;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<EntranceIconsBean> getEntranceIcons() {
            return entranceIcons;
        }

        public void setEntranceIcons(List<EntranceIconsBean> entranceIcons) {
            this.entranceIcons = entranceIcons;
        }

        public List<PartitionsBean> getPartitions() {
            return partitions;
        }

        public void setPartitions(List<PartitionsBean> partitions) {
            this.partitions = partitions;
        }

        public static class BannerBean {
            /**
             * title : 对象？你尽管抢，抢到算我输
             * img : http://i0.hdslb.com/bfs/live/2807719e244e45714a3e08548b1c889815eaa1f6.png
             * remark : 情人节2.0
             * link : http://live.bilibili.com/AppBanner/index?id=467
             */

            private String title;
            private String img;
            private String remark;
            private String link;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class EntranceIconsBean {
            /**
             * id : 9
             * name : 绘画专区
             * entrance_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/big/hdpi/9_big.png?201703141338","height":"66","width":"66"}
             */

            private int id;
            private String name;
            private EntranceIconBean entrance_icon;

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

            public EntranceIconBean getEntrance_icon() {
                return entrance_icon;
            }

            public void setEntrance_icon(EntranceIconBean entrance_icon) {
                this.entrance_icon = entrance_icon;
            }

            public static class EntranceIconBean {
                /**
                 * src : http://static.hdslb.com/live-static/images/mobile/android/big/hdpi/9_big.png?201703141338
                 * height : 66
                 * width : 66
                 */

                private String src;
                private String height;
                private String width;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }
            }
        }

        public static class PartitionsBean {

            private PartitionBean partition;
            private List<LivesBean> lives;

            public PartitionBean getPartition() {
                return partition;
            }

            public void setPartition(PartitionBean partition) {
                this.partition = partition;
            }

            public List<LivesBean> getLives() {
                return lives;
            }

            public void setLives(List<LivesBean> lives) {
                this.lives = lives;
            }

            public static class PartitionBean {
                /**
                 * id : 9
                 * name : 绘画专区
                 * area : draw
                 * sub_icon : {"src":"http://static.hdslb.com/live-static/images/mobile/android/small/hdpi/9.png?201703141338","height":"32","width":"32"}
                 * count : 63
                 */

                private int id;
                private String name;
                private String area;
                private SubIconBean sub_icon;
                private int count;

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

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public SubIconBean getSub_icon() {
                    return sub_icon;
                }

                public void setSub_icon(SubIconBean sub_icon) {
                    this.sub_icon = sub_icon;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public static class SubIconBean {
                    /**
                     * src : http://static.hdslb.com/live-static/images/mobile/android/small/hdpi/9.png?201703141338
                     * height : 32
                     * width : 32
                     */

                    private String src;
                    private String height;
                    private String width;

                    public String getSrc() {
                        return src;
                    }

                    public void setSrc(String src) {
                        this.src = src;
                    }

                    public String getHeight() {
                        return height;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }

                    public String getWidth() {
                        return width;
                    }

                    public void setWidth(String width) {
                        this.width = width;
                    }
                }
            }

            public static class LivesBean {
                /**
                 * owner : {"face":"http://i1.hdslb.com/bfs/face/db342a57a24aca75c212a6f951e720bf73301f7b.jpg","mid":1976885,"name":"金罐佳多宝"}
                 * cover : {"src":"http://i0.hdslb.com/bfs/live/d4ab0b4ac24d443c83d369b26b823cb35bcff6de.jpg","height":180,"width":320}
                 * title : 田工头原画大讲堂之日式风格如何绘制
                 * room_id : 3291311
                 * check_version : 0
                 * online : 2257
                 * area : 绘画专区
                 * area_id : 9
                 * playurl : http://dl.live-play.acgvideo.com/live-dl/932947/live_1976885_5076078.flv?wsSecret=0bb36ce357bcbc1cdb7c756018cc4451&wsTime=1490153762
                 * accept_quality : 4
                 * broadcast_type : 1
                 * is_tv : 0
                 */

                private OwnerBean owner;
                private CoverBean cover;
                private String title;
                private int room_id;
                private int check_version;
                private int online;
                private String area;
                private int area_id;
                private String playurl;
                private String accept_quality;
                private int broadcast_type;
                private int is_tv;

                public OwnerBean getOwner() {
                    return owner;
                }

                public void setOwner(OwnerBean owner) {
                    this.owner = owner;
                }

                public CoverBean getCover() {
                    return cover;
                }

                public void setCover(CoverBean cover) {
                    this.cover = cover;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getRoom_id() {
                    return room_id;
                }

                public void setRoom_id(int room_id) {
                    this.room_id = room_id;
                }

                public int getCheck_version() {
                    return check_version;
                }

                public void setCheck_version(int check_version) {
                    this.check_version = check_version;
                }

                public int getOnline() {
                    return online;
                }

                public void setOnline(int online) {
                    this.online = online;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public int getArea_id() {
                    return area_id;
                }

                public void setArea_id(int area_id) {
                    this.area_id = area_id;
                }

                public String getPlayurl() {
                    return playurl;
                }

                public void setPlayurl(String playurl) {
                    this.playurl = playurl;
                }

                public String getAccept_quality() {
                    return accept_quality;
                }

                public void setAccept_quality(String accept_quality) {
                    this.accept_quality = accept_quality;
                }

                public int getBroadcast_type() {
                    return broadcast_type;
                }

                public void setBroadcast_type(int broadcast_type) {
                    this.broadcast_type = broadcast_type;
                }

                public int getIs_tv() {
                    return is_tv;
                }

                public void setIs_tv(int is_tv) {
                    this.is_tv = is_tv;
                }

                public static class OwnerBean {
                    /**
                     * face : http://i1.hdslb.com/bfs/face/db342a57a24aca75c212a6f951e720bf73301f7b.jpg
                     * mid : 1976885
                     * name : 金罐佳多宝
                     */

                    private String face;
                    private int mid;
                    private String name;

                    public String getFace() {
                        return face;
                    }

                    public void setFace(String face) {
                        this.face = face;
                    }

                    public int getMid() {
                        return mid;
                    }

                    public void setMid(int mid) {
                        this.mid = mid;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class CoverBean {
                    /**
                     * src : http://i0.hdslb.com/bfs/live/d4ab0b4ac24d443c83d369b26b823cb35bcff6de.jpg
                     * height : 180
                     * width : 320
                     */

                    private String src;
                    private int height;
                    private int width;

                    public String getSrc() {
                        return src;
                    }

                    public void setSrc(String src) {
                        this.src = src;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }
                }
            }
        }
    }
}
