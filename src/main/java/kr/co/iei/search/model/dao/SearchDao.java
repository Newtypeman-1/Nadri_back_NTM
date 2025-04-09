package kr.co.iei.search.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.search.model.dto.SearchDTO;

@Mapper
public interface SearchDao {

	List selectKeyword(SearchDTO search);

	List selectPlaceByKeyword(SearchDTO search);

}
