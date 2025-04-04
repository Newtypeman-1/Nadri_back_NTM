package kr.co.iei.place.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.place.model.dto.SpotDTO;
import kr.co.iei.util.PageInfo;

@Mapper
public interface PlaceDao {

	int insertCommon(List list);

	int totalCount();

	List selectPlaceList(PageInfo pi);

	List<PlaceInfoDTO> selectSpotDetail();

	void updateDetailInfo(PlaceInfoDTO place);

	void insertSpotInfo(SpotDTO spot);

	void insertPlaceInfoList(List<PlaceInfoDTO> list);
	
}
