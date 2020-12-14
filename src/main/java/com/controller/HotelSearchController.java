package com.controller;

import com.pojo.Dto;
import com.serivce.IHotelSearchSerivce;
import com.util.DtoUtil;
import com.util.EmptyUtils;
import com.util.Page;
import com.util.VO.ItripHotelVO;
import com.util.VO.SearchHotCityVO;
import com.util.VO.SearchHotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hotellist")
public class HotelSearchController {
    @Autowired
    private IHotelSearchSerivce iHotelSearchSerivce;
    public IHotelSearchSerivce getiHotelSearchSerivce() {
        return iHotelSearchSerivce;
    }
    public void setiHotelSearchSerivce(IHotelSearchSerivce iHotelSearchSerivce) {
        this.iHotelSearchSerivce = iHotelSearchSerivce;
    }
    @RequestMapping(value = "/searchItripHotelPage")
    public Dto searchItripHotelPage(@RequestBody SearchHotelVO searchHotelVO){
        System.out.println("查询酒店方法进入。。。");
        if(EmptyUtils.isEmpty(searchHotelVO.getDestination()) || EmptyUtils.isEmpty(searchHotelVO)) {
            return DtoUtil.returnFail("目的地不能为空","20002");
        }
        try {
            Page page= iHotelSearchSerivce.findHotelPageAll(searchHotelVO,searchHotelVO.getPageNo(),searchHotelVO.getPageSize());
               if(page.getRows().size()==0){
                    return DtoUtil.returnFail("传入page为空","20003");
               }else{
                    return DtoUtil.returnDataSuccess(page);
               }
        } catch (Exception e) {
                 return DtoUtil.returnFail("系统异常,获取失败","20001");
        }

    }
    @RequestMapping(value = "/searchItripHotelListByHotCity")
    public Dto searchItripHotelListByHotCity(@RequestBody SearchHotCityVO searchHotCityVO){
        System.out.println("查询热门城市方法进入。。。。");
        if(EmptyUtils.isEmpty(searchHotCityVO.getCityId()) || EmptyUtils.isEmpty(searchHotCityVO)){
            return DtoUtil.returnFail("热门城市id不能为空","20004");
        }
        try {
          //SearchHotelVO searchHotelVO=new SearchHotelVO();
           List<ItripHotelVO> list=iHotelSearchSerivce.findHotCityAll(searchHotCityVO.getCityId(),searchHotCityVO.getCount());
       //Page<ItripHotelVO> list=iHotelSearchSerivce.findHotelPageAll(searchHotelVO,searchHotCityVO.getCityId(),searchHotCityVO.getCount());
            //if(list.getPageSize()==0){
            if(list.size()==0){
           return DtoUtil.returnFail("没有找到热门城市酒店","111111");
       }else {
           return DtoUtil.returnDataSuccess(list);
       }
        } catch (Exception e) {
           return DtoUtil.returnFail(e.getMessage(),"20003");
        }
    }

}
