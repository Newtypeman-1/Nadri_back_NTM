package kr.co.iei.review.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.util.PageInfo;

@Mapper
public interface ReviewDao {

	int totalCount(HashMap<String, Object> map);

	List selectBoardList(HashMap<String, Object> map);

	int reviewTotalCount();

	List allBoardList(PageInfo pi);





}
