package kr.co.iei.member.model.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.co.iei.member.model.dao.MemberDao;

@Service
public class MemberService {
	@Autowired
	private MemberDao memberDao; 
	
	public int exists(String memberNickname) {
		int result = memberDao.exists(memberNickname);
		return result;
	}
}
