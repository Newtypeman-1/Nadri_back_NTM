package kr.co.iei.plan.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanDao {

	List selectNearby(double lat, double lng, int radius);

}
