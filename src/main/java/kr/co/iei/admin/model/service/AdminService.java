package kr.co.iei.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.admin.model.dao.AdminDao;
import kr.co.iei.admin.model.dto.CompanyDTO;
import kr.co.iei.admin.model.dto.EventDTO;
import kr.co.iei.admin.model.dto.KeywordDTO;
import kr.co.iei.review.model.dto.ReportDTO;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;
	
	public List selectOnGoingEvent(String date) {
		List eventList = adminDao.selectOnGoingEvent(date);
		return eventList;
	}
	public List selectMonthEvent(String month) {
		List eventList = adminDao.selectMonthEvent(month);
		return eventList;
	}
	@Transactional
	public int insertEvent(EventDTO event) {
		int result= adminDao.insertEvent(event);
		return result;
	}
	@Transactional
	public String updateEvent(EventDTO event) {
		String filepath = null;
		if(event.getEventImg()!=null) {
			filepath = adminDao.selectDelFile(event.getEventNo());
		}
		int result= adminDao.updateEvent(event);
		return filepath;
	}
	public List selectEndEvent(String date) {
		List eventList = adminDao.selectEndEvent(date);
		return eventList;
	}
	@Transactional
	public String deleteEvent(int eventNo) {
		String filepath= adminDao.selectDelFile(eventNo);
		int result = adminDao.deleteEvent(eventNo);
		if(result>0) {
			return filepath;
		}
		return null;
	}
	public CompanyDTO selectCompanyInfo() {
		CompanyDTO company = adminDao.selectCompanyInfo();
		return company;
	}
	@Transactional
	public int updateCompanyInfo(CompanyDTO company) {
		int result = adminDao.updateCompanyInfo(company);
		return result;
	}
	public KeywordDTO selectKeywordInfo(String keyword) {
		KeywordDTO keywordInfo = adminDao.selectKeywordInfo(keyword);
		return keywordInfo;
	}
	@Transactional
	public int upsertKeywordInfo(KeywordDTO keyword) {
		int result = adminDao.upsertKeywordInfo(keyword);
		return result;
	}
	@Transactional
	public int updateReport(ReportDTO report) {
		int result = adminDao.updateReport(report);
		switch(report.getReportStatus()) {
		case 2:
			//접수
			result += adminDao.updateWarning(report.getMemberNickname());
			break;
		case 3:
			//반려
			result += adminDao.updateWarning(report.getReportNickname());
			break;
		}
		return result;
	}

}
