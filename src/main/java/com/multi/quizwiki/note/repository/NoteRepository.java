package com.multi.quizwiki.note.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.multi.quizwiki.note.entity.NoteEntity;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer>{
	public Page<NoteEntity> findByMemberId(String memberId, Pageable pageable);
}
