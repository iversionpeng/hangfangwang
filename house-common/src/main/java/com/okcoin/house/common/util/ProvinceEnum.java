package com.okcoin.house.common.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public enum ProvinceEnum {
    BEI_JING(1, "北京", Lists.newArrayList("北京")),
    SHANNG_HAI(2, "上海", Lists.newArrayList("上海")),
    TIAN_JING(3, "天津", Lists.newArrayList("天津")),
    CHONG_QING(4, "重庆", Lists.newArrayList("重庆")),
    HEI_LONG_JIANG(5, "黑龙江", Lists.newArrayList("哈尔滨", "齐齐哈尔", "牡丹江", "大庆", "伊春", "双鸭山", "鹤岗", "鸡西", "佳木斯", "七台河", "黑河", "绥化", "大兴安岭")),
    JI_LIN(6, "吉林", Lists.newArrayList("长春", "延边", "吉林", "白山", "白城", "四平", "松原", "辽源", "大安", "通化")),
    LIAO_LIN(7, "辽宁", Lists.newArrayList("沈阳", "大连", "葫芦岛", "旅顺", "本溪", "抚顺", "铁岭", "辽阳", "营口", "阜新", "朝阳", "锦州", "丹东", "鞍山")),
    NEI_MENG_GU(8, "内蒙古", Lists.newArrayList("呼和浩特", "呼伦贝尔", "锡林浩特", "包头", "赤峰", "海拉尔", "乌海", "鄂尔多斯", "通辽")),
    HE_BEI(9, "河北", Lists.newArrayList("石家庄", "唐山", "张家口", "廊坊", "邢台", "邯郸", "沧州", "衡水", "承德", "保定", "秦皇岛")),
    HE_NAN(10, "河南", Lists.newArrayList("郑州", "开封", "洛阳", "平顶山", "焦作", "鹤壁", "新乡", "安阳", "濮阳", "许昌", "漯河", "三门峡", "南阳", "商丘", "信阳", "周口", "驻马店")),
    SHAN_DONG(11, "山东", Lists.newArrayList("济南", "青岛", "淄博", "威海", "曲阜", "临沂", "烟台", "枣庄", "聊城", "济宁", "菏泽", "泰安", "日照", "东营", "德州", "滨州", "莱芜", "潍坊")),
    SHAN_XI(12, "山西", Lists.newArrayList("太原", "阳泉", "晋城", "晋中", "临汾", "运城", "长治", "朔州", "忻州", "大同", "吕梁")),
    JIANG_SU(13, "江苏", Lists.newArrayList("南京", "苏州", "昆山", "南通", "太仓", "吴县", "徐州", "宜兴", "镇江", "淮安", "常熟", "盐城", "泰州", "无锡", "连云港", "扬州", "常州", "宿迁")),
    AN_HUI(14, "安徽", Lists.newArrayList("合肥", "巢湖", "蚌埠", "安庆", "六安", "滁州", "马鞍山", "阜阳", "宣城", "铜陵", "淮北", "芜湖", "毫州", "宿州", "淮南", "池州")),
    SHAAN_XI(15, "陕西", Lists.newArrayList("西安", "韩城", "安康", "汉中", "宝鸡", "咸阳", "榆林", "渭南", "商洛", "铜川", "延安")),
    NING_XIA(16, "宁夏", Lists.newArrayList("银川", "固原", "中卫", "石嘴山", "吴忠")),
    GAN_SU(17, "甘肃", Lists.newArrayList("兰州", "白银", "庆阳", "酒泉", "天水", "武威", "张掖", "甘南", "临夏", "平凉", "定西", "金昌")),
    QING_HAI(18, "青海", Lists.newArrayList("西宁", "海北", "海西", "黄南", "果洛", "玉树", "海东", "海南")),
    HU_BEI(19, "湖北", Lists.newArrayList("武汉", "宜昌", "黄冈", "恩施", "荆州", "神农架", "十堰", "咸宁", "襄樊", "孝感", "随州", "黄石", "荆门", "鄂州")),
    HU_NAN(20, "湖南", Lists.newArrayList("长沙", "邵阳", "常德", "郴州", "吉首", "株洲", "娄底", "湘潭", "益阳", "永州", "岳阳", "衡阳", "怀化", "韶山", "张家界")),
    ZHE_JIANG(21, "浙江", Lists.newArrayList("杭州", "湖州", "金华", "宁波", "丽水", "绍兴", "雁荡山", "衢州", "嘉兴", "台州", "舟山", "温州")),
    JIANG_XI(22, "江西", Lists.newArrayList("南昌", "萍乡", "九江", "上饶", "抚州", "吉安", "鹰潭", "宜春", "新余", "景德镇", "赣州")),
    FU_JIAN(23, "福建", Lists.newArrayList("福州", "厦门", "龙岩", "南平", "宁德", "莆田", "泉州", "三明", "漳州")),
    GUI_ZHOU(24, "贵州", Lists.newArrayList("贵阳", "安顺", "赤水", "遵义", "铜仁", "六盘水", "毕节", "凯里", "都匀")),
    SI_SHUN(25, "四川", Lists.newArrayList("成都", "泸州", "内江", "凉山", "阿坝", "巴中", "广元", "乐山", "绵阳", "德阳", "攀枝花", "雅安", "宜宾", "自贡", "甘孜州", "达州", "资阳", "广安", "遂宁", "眉山", "南充")),
    GUANG_DONG(26, "广东", Lists.newArrayList("广州", "深圳", "潮州", "韶关", "湛江", "惠州", "清远", "东莞", "江门", "茂名", "肇庆", "汕尾", "河源", "揭阳", "梅州", "中山", "德庆", "阳江", "云浮", "珠海", "汕头", "佛山")),
    GUANG_XI(27, "广西", Lists.newArrayList("南宁", "桂林", "阳朔", "柳州", "梧州", "玉林", "桂平", "贺州", "钦州", "贵港", "防城港", "百色", "北海", "河池", "来宾", "崇左")),
    YUN_NAN(28, "云南", Lists.newArrayList("昆明", "保山", "楚雄", "德宏", "红河", "临沧", "怒江", "曲靖", "思茅", "文山", "玉溪", "昭通", "丽江", "大理")),
    HAI_NAN(29, "海南", Lists.newArrayList("海口", "三亚", "儋州", "琼山", "通什", "文昌")),
    XIN_JIANG(30, "新疆", Lists.newArrayList("乌鲁木齐", "阿勒泰", "阿克苏", "昌吉", "哈密", "和田", "喀什", "克拉玛依", "石河子", "塔城", "库尔勒", "吐鲁番", "伊宁")),;
    private Integer provinceCode;
    private String provinceName;
    private List<String> cityList;

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public List<String> getCityList() {
        return cityList;
    }

    ProvinceEnum(Integer provinceCode, String provinceName, List<String> cityList) {
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.cityList = cityList;
    }

    public static ProvinceEnum getProvinceEnum(Integer provinceCode) {
        return Arrays.stream(ProvinceEnum.values()).filter(x -> x.getProvinceCode().equals(provinceCode)).findFirst().orElseGet(null);
    }

    public static Integer getCityCode(ProvinceEnum provinceEnum, Integer cityName) {
        return provinceEnum.getProvinceCode() * 100 + cityName;
    }
}
