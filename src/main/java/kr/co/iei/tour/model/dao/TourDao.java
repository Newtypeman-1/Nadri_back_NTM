package kr.co.iei.tour.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TourDao {

	int insertCommon(List list);
	
}
