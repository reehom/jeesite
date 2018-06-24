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

    public interface XQStatus{
        String TO_BE_AUDITED = "0";  //待审核
        String DELETE = "1";         //删除
        String  PASS= "2";           //审核通过
        String NO_PASS = "3";        //审核没通过
        String CODING = "4";         //开发中
        String FINISH = "5";         //已完成
        String ACCESS = "access";   //通过
        String DENY = "deny" ;      //不通过
    }
}
