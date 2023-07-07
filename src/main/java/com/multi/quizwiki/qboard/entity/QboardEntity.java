package com.multi.quizwiki.qboard.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="qboard")
public class QboardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int qboardId;
	private String memberId;
	private String category;
	private String title;
	private String content;
	private Timestamp regdate;
	private String editdate;
	private int viewCount;
	private int likeCount;
	private int commentCount;
	private int deleteYn;
	
}

