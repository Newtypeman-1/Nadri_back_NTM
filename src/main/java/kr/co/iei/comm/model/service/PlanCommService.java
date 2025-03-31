package kr.co.iei.comm.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.comm.model.dao.PlanCommDao;

@Service
public class PlanCommService {
	@Autowired
private PlanCommDao palnCommDao;
}
