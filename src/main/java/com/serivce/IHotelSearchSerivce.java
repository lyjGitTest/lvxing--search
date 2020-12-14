package com.serivce;

import com.util.Page;
import com.util.VO.ItripHotelVO;
import com.util.VO.SearchHotCityVO;
import com.util.VO.SearchHotelVO;

import java.util.List;

public interface IHotelSearchSerivce {
    //查询所有酒店
    public Page<ItripHotelVO> findHotelPageAll(SearchHotelVO searchHotelVO,Integer pageNo,Integer pageSize) throws  Exception;

    //测试public Page<ItripHotelVO> findHotCityAll(Integer cityId,Integer pageNo,Integer pageSize)throws Exception ;
    //查询热门城市
    public List<ItripHotelVO> findHotCityAll(Integer cityId,Integer pageSize)throws Exception ;
}
