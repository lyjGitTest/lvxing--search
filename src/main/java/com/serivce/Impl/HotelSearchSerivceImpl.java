package com.serivce.Impl;

import com.mapper.BaseQuery;
import com.serivce.IHotelSearchSerivce;
import com.util.DtoUtil;
import com.util.EmptyUtils;
import com.util.Page;
import com.util.VO.ItripHotelVO;
import com.util.VO.SearchHotCityVO;
import com.util.VO.SearchHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelSearchSerivceImpl implements IHotelSearchSerivce {
    private BaseQuery<ItripHotelVO> itripHotelVOBaseQuery=new BaseQuery<>("http://127.0.0.1:8080/solr/hotel");
    @Override
    public Page<ItripHotelVO> findHotelPageAll(SearchHotelVO searchHotelVO, Integer pageNo, Integer pageSize) throws Exception {
        //查询所有
        SolrQuery solrQuery=new SolrQuery("*:*");
        StringBuffer strBuffer=new StringBuffer();
        //判断标识符
        int flag=0;
        int fr=0;
        //判断地点是否为空
        if (EmptyUtils.isNotEmpty(searchHotelVO.getDestination())){
            strBuffer.append("destination : "+searchHotelVO.getDestination());
            flag=1;
        }
      //判断酒店等级 hotelLevel
        if(EmptyUtils.isNotEmpty(searchHotelVO.getHotelLevel())){
          solrQuery.addFilterQuery("hotelLevel : "+searchHotelVO.getHotelLevel()+"");
        }
        //判断商圈TradeAreaIds
        if(EmptyUtils.isNotEmpty(searchHotelVO.getTradeAreaIds())) {
            solrQuery.addFilterQuery("tradingAreaIds :" + "*," + searchHotelVO.getTradeAreaIds() + ",*");
        }
        //判断keyword
        if(EmptyUtils.isNotEmpty(searchHotelVO.getKeywords())){
            if(flag==1){
                strBuffer.append("AND keyword : "+searchHotelVO.getKeywords());
                String str="AND keyword : null";
                if(strBuffer.indexOf(str)!= -1){
                    strBuffer.delete(strBuffer.indexOf(str),strBuffer.length());
                }
            }else{
                strBuffer.append("keyword : "+searchHotelVO.getKeywords());
         fr=1;
            }
        }
        //酒店特色
        if(EmptyUtils.isNotEmpty(searchHotelVO.getFeatureIds())){
            StringBuffer str=new StringBuffer("(");

            String featureArr[]=searchHotelVO.getFeatureIds().split(",");
            for (String featuresids:featureArr) {
                    if (fr == 0) {
                        str.append("featureIds:" + "*," + featuresids + ",*");
                    } else {
                        str.append(" OR featureIds:" + "*," + featuresids + ",*");
                    }
                    fr++;
                }
            str.append(")");
            System.out.println("str111======="+str);
            solrQuery.addFilterQuery(str.toString());
            System.out.println("sorequrey======="+solrQuery.toString());
        }
        //判断价格
        if(EmptyUtils.isNotEmpty(searchHotelVO.getMaxPrice())){
            solrQuery.addFilterQuery("minPrice:"+"[* TO "+searchHotelVO.getMaxPrice()+"]");
        }
        if(EmptyUtils.isNotEmpty(searchHotelVO.getMinPrice())){
            solrQuery.addFilterQuery("minPrice:"+"["+searchHotelVO.getMinPrice()+" TO *]");
        }

        //判断排序
        if(EmptyUtils.isNotEmpty(searchHotelVO.getAscSort())){
            solrQuery.setSort(searchHotelVO.getAscSort(),SolrQuery.ORDER.asc);
        }
        if(EmptyUtils.isNotEmpty(searchHotelVO.getDescSort())){
            solrQuery.setSort(searchHotelVO.getDescSort(),SolrQuery.ORDER.desc);
        }
        //判断查询语句
        if(EmptyUtils.isNotEmpty(strBuffer.toString())){

            solrQuery.setQuery(strBuffer.toString());
            System.out.println("strBuffer000000====="+solrQuery.toString());
        }
        Page<ItripHotelVO> itripHotelVOPage=itripHotelVOBaseQuery.queryPage(solrQuery,pageNo,pageSize,ItripHotelVO.class);
        return itripHotelVOPage;
    }
   public List<ItripHotelVO> findHotCityAll(Integer cityId, Integer pageSize) throws Exception {
       SolrQuery solrQuery=new SolrQuery("*:*");
       //判断城市id
       if(EmptyUtils.isNotEmpty(cityId)){
           solrQuery.addFilterQuery("cityId : "+cityId);
       }else {
           return null;
       }
       List<ItripHotelVO> itripHotelVOList=itripHotelVOBaseQuery.queryList(solrQuery,pageSize,ItripHotelVO.class);
        return itripHotelVOList;
    }
}
