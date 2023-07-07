package com.multi.quizwiki.qboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multi.quizwiki.qboard.entity.QboardEntity;

public interface QboardRepository extends JpaRepository<QboardEntity, Integer>{
	
	public List<QboardEntity>findTop10ByDeleteYnNotOrderByViewCountDesc(int deleteYn);

}
