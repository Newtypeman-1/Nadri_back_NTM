package kr.co.iei.plan.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.plan.model.dto.PlanDTO;

@Mapper
public interface PlanDao {

	List selectNearby(double lat, double lng, int radius);

	PlanDTO verifyPlan(String memberEmail, int planNo);

	List selectPlanItineraries(int planNo);

	int isPlanOwner(String memberEmail, int planNo);

}
