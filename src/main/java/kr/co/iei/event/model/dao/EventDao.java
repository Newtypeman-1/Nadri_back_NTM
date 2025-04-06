package kr.co.iei.event.model.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.event.model.dto.EventDTO;

@Mapper
public interface EventDao {

	int insertEvent(EventDTO event);
	
}
