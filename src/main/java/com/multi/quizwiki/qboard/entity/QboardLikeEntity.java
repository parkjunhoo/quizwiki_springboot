package com.multi.quizwiki.qboard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="qboard_like")
public class QboardLikeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String memberId;
	Long qboardId;
	
	public QboardLikeEntity(String memberId, Long qboardId) {
		super();
		this.memberId = memberId;
		this.qboardId = qboardId;
	}
}

