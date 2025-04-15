package kr.co.iei.member.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import kr.co.iei.member.model.dto.MemberDTO;

@Mapper
public interface MemberDao {
	
	int exists(String memberNickname);

	int insertMember(MemberDTO member);

	int socialJoin(MemberDTO member);
	
	int existsEmail(String memberEmail);

	int updatePw(MemberDTO member);

	MemberDTO selectOneMember(String memberEmail);

	int selectMemberNo(MemberDTO member);
	
	Integer loginIsDel(int memberNo);
	
	MemberDTO socialLogin(String userEmail);
	
	int isSocial(String email);
	
	MemberDTO selectMemberInfo(String memberNickname);

	int updateMember(MemberDTO member);

	int updateMember2(MemberDTO member);
	
	int deleteMember(MemberDTO memberNickname);

	int insertDelMember(MemberDTO member);

	int deleteDelMember(MemberDTO member);

	List<MemberDTO> selectWarningMembers();

	int updateMemberLevel(int memberNo, int memberLevel);




}
