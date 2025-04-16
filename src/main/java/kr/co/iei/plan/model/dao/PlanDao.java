package kr.co.iei.plan.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import kr.co.iei.plan.model.dto.ItineraryDTO;
import kr.co.iei.plan.model.dto.ItineraryWithPlaceDTO;
import kr.co.iei.admin.model.dto.AdminStatsDTO;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.plan.model.dto.PlanDTO;
import kr.co.iei.plan.model.dto.PlanRequestDTO;

@Mapper
public interface PlanDao {

	List selectNearby(double lat, double lng, int radius);

	PlanDTO verifyPlan(String loginNickname, int planNo);

//	List selectPlanItineraries(int planNo);

	int isPlanOwner(String loginNickname, int planNo);

	int insertTripPlan(PlanDTO plan);

	int insertTripItinerary(ItineraryDTO i);
	
	AdminStatsDTO selectPlanStats();

	List<PlaceInfoDTO> selectMostPlace();

	List<ItineraryWithPlaceDTO> selectPlanItinerariesWithPlace(int planNo);

	PlanDTO selectOnePlan(Integer planNo);

	int updatePlan(PlanDTO plan);

	int deleteItineraries(int planNo);

	List selectPagedNearby(Map<String, Object> map);

	int countNearby(Map<String, Object> map);

	int checkBookmark(int planNo, String memberNickname);

	int deleteBookmark(int planNo, String memberNickname);

	int insertBookmark(int planNo, String memberNickname);
	
	List selectPlanList(PlanRequestDTO request);

	int deletePlan(int planNo, String memberNickname);

//	int copyPlan(int planNo, String memberNickname);

}
