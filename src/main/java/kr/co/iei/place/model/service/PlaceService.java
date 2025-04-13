package kr.co.iei.place.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.security.Jwks.HASH;
import kr.co.iei.place.model.dao.PlaceDao;
import kr.co.iei.util.PageInfo;
import kr.co.iei.place.model.dto.CategoryDTO;
import kr.co.iei.place.model.dto.FilterMapRow;
import kr.co.iei.place.model.dto.PlaceFilterRequest;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.search.model.dto.SearchLogDTO;
import kr.co.iei.util.PageInfoUtil;

@Service
public class PlaceService {
	@Autowired
	private PlaceDao placeDao;
	@Autowired
	private PageInfoUtil pageInfoUtil;

	// placeInfo 조회(페이지인포 포함 / 기본 데이터 / 좋아요 상태)
	public Map selectALLPlaceList(int reqPage, String memberNickname, int order) {
		int numPerPage = 12;
		int pageNaviSize = 5;
		int totalCount = placeDao.totalCount();
		PageInfo pi = pageInfoUtil.getPageInfo(reqPage, numPerPage, pageNaviSize, totalCount);

		Map<String, Object> map = new HashMap<>();
		map.put("pi", pi);
		map.put("memberNickname", memberNickname);
		map.put("order", order);

		List<PlaceInfoDTO> list = placeDao.selectALLPlaceList(map);
		// placeTitle 괄호제거
		for (PlaceInfoDTO place : list) {
			String title = place.getPlaceTitle();
			if (title != null) {
				String cleanedTitle = title.replaceAll("\\(.*?\\)", "").trim();
				place.setPlaceTitle(cleanedTitle);
			}
		}

		Map<String, Object> map2 = new HashMap<>();
		map2.put("list", list);
		map2.put("pi", pi);
		map2.put("totalCount", totalCount);

		return map2;
	}

	// 상세 필터 적용 플레이스 리스트 조회
	public Map selectFilteredPlaceList(PlaceFilterRequest req) {
		// 필터맵 정보 전체 조회
		List<FilterMapRow> rows = placeDao.findFilterMapRows(req.getFilters(), req.getSelectedMenu());

		List<Integer> placeTypeIds = new ArrayList<>();
		List<String> cat2Codes = new ArrayList<>();
		List<String> cat3Codes = new ArrayList<>();

		for (FilterMapRow row : rows) {
			if (!placeTypeIds.contains(row.getPlaceTypeId())) {
				placeTypeIds.add(row.getPlaceTypeId());
			}

			if ("cat2".equals(row.getCodeType())) {
				cat2Codes.add(row.getCode());
			} else if ("cat3".equals(row.getCodeType())) {
				cat3Codes.add(row.getCode());
			}
		}
		System.out.println();
		int numPerPage = 12;
		int pageNaviSize = 5;
		int totalCount = placeDao.getFilteredPlaceCount(placeTypeIds, cat2Codes, cat3Codes);
		PageInfo pi = pageInfoUtil.getPageInfo(req.getReqPage(), numPerPage, pageNaviSize, totalCount);

		List<PlaceInfoDTO> list = placeDao.selectPlaceListByFilterPaged(placeTypeIds, cat2Codes, cat3Codes,
				req.getOrder(), pi.getStart(), pi.getEnd(), req.getMemberNickname());
		
		System.out.println(list);
		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", totalCount);
		map.put("pi", pi);
		map.put("list", list);

		return map;
	}

	// 플레이스 타입 아이디 조회
	public List<CategoryDTO> selectPlaceType() {
		List<CategoryDTO> placeType = placeDao.selectPlaceType();
		return placeType;
	}

	public Map<String, List<CategoryDTO>> selectPlaceCategory() {
		List<CategoryDTO> allList = placeDao.selectAllPlaceCategories();
		Map<String, List<CategoryDTO>> category = new HashMap<>();
		for (CategoryDTO dto : allList) {
			int index = dto.getCatIndex();
			String key = "cat" + index;

			if (!category.containsKey(key)) {
				category.put(key, new ArrayList<>());
			}
			category.get(key).add(dto);
		}
		return category;
	}

	public List<CategoryDTO> selectPlaceArea() {
		List<CategoryDTO> area = placeDao.selectArea();
		return area;
	}

	// 플레이스 상세페이지 조회(좋아요 상태 추가)
	public PlaceInfoDTO selectOnePlace(int placeId, String memberNickname) {
		PlaceInfoDTO place = placeDao.selectPlaceWithBookmarked(placeId, memberNickname);
		// placeTitle 괄호제거
		String title = place.getPlaceTitle();
		if (title != null) {
			String cleanedTitle = title.replaceAll("\\(.*?\\)", "").trim();
			place.setPlaceTitle(cleanedTitle);
		}
		return place;
	}

	// 즐겨찾기(등록 : 테이블에 추가 / 해제 : 삭제)
	@Transactional
	public int toggleBookmark(String memberNickname, int placeId) {
		// 리스트 조회 방식 기준: row 존재 여부로 판단
		int count = placeDao.checkBookmark(memberNickname, placeId);
		if (count > 0) {
			placeDao.deleteBookmark(memberNickname, placeId);
			return 0; // 해제됨
		} else {
			placeDao.insertBookmark(memberNickname, placeId);
			return 1; // 등록됨
		}
	}

	// 조회수 저장
	@Transactional
	public int increaseViewCount(int placeId) {
		int result = placeDao.updatePlaceViewCount(placeId);
		return result;
	}

}
