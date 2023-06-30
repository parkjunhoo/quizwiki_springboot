package com.multi.quizwiki.member.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.multi.quizwiki.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@Slf4j
@Table(name="member2")
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String memberId; // 아이디
	private String memberPass; // 비번
	private String memberName; //이름
	private String memberGrade; //학년
	private String memberGender; // 성별
	private String email; // 이메일
	private String memberTelnum; // 연락처
	private String memberZipcode; // 우편번호
	private String memberRoadAddr; // 도로명 주소
	private String memberJibunAddr; // 지번주소
	private String memberDetailAddr; // 상세주소
	private String memberExtraAddr; // 참고항목
	private String memberMktOpt; // 마케팅 수신 동의 여부 
	//private String member_mkt_sms; // 마케팅 sms
	//private String member_mkt_email; // 마케팅 email
	private Date memberRegdate; // 가입날짜 
	private int memberPoint; // 포인트
	private String memberPhoto; // 플필 이미지
	private String memberType; // 회원타입 - 1:청소년, 2:대학생
	private String memberMajor; // 전공
	private String universityName; // 대학교명
	private int memberState; // 상태값 : 회원가입시 1을 주고 없는 회원에게는 0을 준다
	private String kakaoID; // 카카오 간편 로그인

	
	  public static MemberEntity toEntity(MemberDTO dto) { 
		  return MemberEntity.builder()
				  .memberId(dto.getMember_id()) 
				  .memberPass(dto.getMember_pass())
				  .memberName(dto.getMember_name())
				  .memberGrade(dto.getMember_grade())
				  .memberGender(dto.getMember_gender())
				  .email(dto.getEmail())
				  .memberTelnum(dto.getMember_telnum())
				  .memberZipcode(dto.getMember_zipcode())
				  .memberRoadAddr(dto.getMember_road_addr())
				  .memberJibunAddr(dto.getMember_jibun_addr())
				  .memberDetailAddr(dto.getMember_detail_addr())
				  .memberExtraAddr(dto.getMember_extra_addr())
				  .memberMktOpt(dto.getMember_mkt_opt())
				  .memberRegdate(dto.getMember_regdate())
				  .memberPoint(dto.getMember_point())
				  .memberPhoto(dto.getMember_photo())
				  .memberType(dto.getMember_type())
				  .memberMajor(dto.getMember_major())
				  .universityName(dto.getUniversity_name())
				  .memberState(dto.getMember_state())
				  .kakaoID(dto.getKakaoID())
				  .build();
	  }
	 

}
