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
}
