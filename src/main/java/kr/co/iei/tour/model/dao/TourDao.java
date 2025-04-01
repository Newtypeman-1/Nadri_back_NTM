package kr.co.iei.tour.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.util.PageInfo;

@Mapper
public interface TourDao {

	int insertCommon(List list);

	int totalCount();

	List selectTourList(PageInfo pi);
	
}
