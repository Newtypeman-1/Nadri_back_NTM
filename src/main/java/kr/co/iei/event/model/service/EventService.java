package kr.co.iei.event.model.service;

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
}
