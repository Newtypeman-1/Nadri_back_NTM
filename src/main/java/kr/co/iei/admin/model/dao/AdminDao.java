package kr.co.iei.admin.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.admin.model.dto.CompanyDTO;
import kr.co.iei.admin.model.dto.EventDTO;
import kr.co.iei.admin.model.dto.KeywordDTO;
import kr.co.iei.review.model.dto.ReportDTO;

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

	int updateCompanyInfo(CompanyDTO company);

	KeywordDTO selectKeywordInfo(String keyword);

	int upsertKeywordInfo(KeywordDTO keyword);

	int updateReport(ReportDTO report);

	int updateWarning(String memberNickname);
}
