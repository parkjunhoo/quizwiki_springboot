package com.multi.quizwiki.mypage.dto;



import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("point")
public class PointDTO {
	private String mypage_point_id;
	private String member_id;
	private String point_amount;
	private Date point_date;
	private String point_content;
}

