package kr.co.iei.review.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.review.model.dao.ReviewDao;
import kr.co.iei.review.model.dto.ReportDTO;

@Service
public class ReportService {
	@Autowired
	private ReviewDao reviewDao;
	@Transactional
	public int insertReport(ReportDTO reportDTO) {
		int result = reviewDao.insertReport(reportDTO);
		return result;
	}
	public List reportList(int reviewNo) {
		List list= reviewDao.reportList(reviewNo);
		return list;
	}
}
