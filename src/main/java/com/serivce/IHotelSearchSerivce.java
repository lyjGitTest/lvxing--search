package com.serivce;

import com.util.Page;
import com.util.VO.ItripHotelVO;
import com.util.VO.SearchHotelVO;

public interface IHotelSearchSerivce {
    //查询所有酒店
    public Page<ItripHotelVO> findHotelPageAll(SearchHotelVO searchHotelVO,Integer pageNo,Integer pageSize) throws  Exception;
}
