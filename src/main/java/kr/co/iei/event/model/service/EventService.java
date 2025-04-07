package kr.co.iei.event.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.event.model.dao.EventDao;
import kr.co.iei.event.model.dto.EventDTO;

@Service
public class EventService {
	@Autowired
	private EventDao eventDao;
	
	public List selectOnGoingEvent(String date) {
		List eventList = eventDao.selectOnGoingEvent(date);
		return eventList;
	}
	public List selectMonthEvent(String month) {
		List eventList = eventDao.selectMonthEvent(month);
		return eventList;
	}
	@Transactional
	public int insertEvent(EventDTO event) {
		int result= eventDao.insertEvent(event);
		return result;
	}
	@Transactional
	public String updateEvent(EventDTO event) {
		String filepath = null;
		if(event.getEventImg()!=null) {
			filepath = eventDao.selectDelFile(event.getEventNo());
		}
		int result= eventDao.updateEvent(event);
		return filepath;
	}
	public List selectEndEvent(String date) {
		List eventList = eventDao.selectEndEvent(date);
		return eventList;
	}
	@Transactional
	public String deleteEvent(int eventNo) {
		String filepath= eventDao.selectDelFile(eventNo);
		int result = eventDao.deleteEvent(eventNo);
		if(result>0) {
			return filepath;
		}
		return null;
	}
}
