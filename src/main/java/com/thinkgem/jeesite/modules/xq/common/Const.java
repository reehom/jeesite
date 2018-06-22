package com.thinkgem.jeesite.modules.xq.common;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;

public class Const {
    public interface SystemLists{

        String EJIAN="E键送达平台";
        String JIXIAO="绩效管理平台";
        String SUCAI="速裁管理平台";
        List<String> systemLists = Lists.newArrayList(EJIAN,JIXIAO,SUCAI);
    }

    public interface XQResource{
        String XIANSHANG = "线上";
        String DIANHUA = "电话等级";
        String XIANCHANG = "现场工作";
        List<String> resourcesLists = Lists.newArrayList(XIANSHANG,DIANHUA,XIANCHANG);
    }
}
