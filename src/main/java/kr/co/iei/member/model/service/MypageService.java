package kr.co.iei.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.member.model.dao.MypageDao;
import kr.co.iei.place.model.dto.PlaceInfoDTO;
import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.util.PageInfo;

@Service
public class MypageService {
	@Autowired
	private MypageDao mypageDao;

	//내가 쓴 리뷰 불러오기
	public Map reviewsList(String nickname, String value) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("nickname",nickname);
		map.put("value",value);
		List list = mypageDao.reviewsList(map);
		map.put("list",list);
		return map;
	}

	public Map bookmarkList(String nickname, String value) {
		Map<String, Object> map = new HashMap<>();
		System.out.println(nickname);
		System.out.println(value);
		map.put("nickname", nickname);
		map.put("value", value);
		
		List<PlaceInfoDTO> list = mypageDao.bookmarkList(map);
		System.out.println(list);
		  //placeTitle 괄호제거
		  for (PlaceInfoDTO place : list) {
	            String title = place.getPlaceTitle();
	            if (title != null) {
	                String cleanedTitle = title.replaceAll("\\(.*?\\)", "").trim();
	                place.setPlaceTitle(cleanedTitle);
	            }
	        }
		map.put("list", list);
		System.out.println(list);
		
		return map;
	}

	public Map plannerList(String nickname, String value) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("nickname",nickname);
		map.put("value",value);
		List list = mypageDao.plannerList(map);
		map.put("list",list);
		return map;
	}
}
