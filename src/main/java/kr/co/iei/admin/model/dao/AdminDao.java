package kr.co.iei.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.admin.model.dto.CompanyDTO;
import kr.co.iei.admin.model.dto.EventDTO;

@Mapper
public interface AdminDao {
	int insertEvent(EventDTO event);

	List selectOnGoingEvent(String date);

	List selectMonthEvent(String month);

	int updateEvent(EventDTO event);

	String selectDelFile(int eventNo);

	List selectEndEvent(String date);

	int deleteEvent(int eventNo);

	CompanyDTO selectCompanyInfo();
}
