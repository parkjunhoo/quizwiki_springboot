package com.multi.quizwiki.note.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.multi.quizwiki.note.entity.NoteEntity;
import com.multi.quizwiki.note.entity.NotePageEntity;

public interface NoteDAO {
	public NoteEntity note_insert(NoteEntity note);
	public List<NotePageEntity> notepage_insert(List<NotePageEntity> notePageList);
	
	public NoteEntity note_findById(int id);
	public Page<NoteEntity> findByMemberId(String memberId, Pageable pageable);
}
