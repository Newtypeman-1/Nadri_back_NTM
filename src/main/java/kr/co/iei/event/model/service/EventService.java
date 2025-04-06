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
	@Transactional
	public int insertEvent(EventDTO event) {
		int result= eventDao.insertEvent(event);
		return result;
	}
	public List selectOnGoingEvent(String date) {
		List eventList = eventDao.selectOnGoingEvent(date);
		return eventList;
	}
	public List selectMonthEvent(String month) {
		List eventList = eventDao.selectMonthEvent(month);
		return eventList;
	}
}
