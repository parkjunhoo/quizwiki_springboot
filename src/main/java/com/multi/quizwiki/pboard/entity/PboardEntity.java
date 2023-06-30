package com.multi.quizwiki.pboard.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.multi.quizwiki.problem.entity.ProblemEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pboard")
public class PboardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pboardId;
	String memberId;
	int pboardCateId;
	String pboardTitle;
	String pboardContent;
	@CreationTimestamp
	Timestamp pboardRegDate;
	@UpdateTimestamp 
	Timestamp pboardEditDate;
	int pboardShowCount;

	@Formula("(SELECT COUNT(1) FROM pboard_like pl WHERE pl.pboard_id = pboard_id)")
	private int pboardLikeCount;
	
	@Formula("(SELECT COUNT(1) FROM pboard_reply pr WHERE pr.pboard_id = pboard_id)")
	private int pboardReplyCount;
	
	@Transient
	private String previewHTML;
	
	
	String pboardStatus;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "pboardId")
	@Where(clause = "problem_status = 'r'")
	private List<ProblemEntity> problemList = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "pboardId")
	private List<PboardReplyEntity> pboardReplyList = new ArrayList<>();
	
}
