package com.multi.quizwiki.note.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.multi.quizwiki.note.dao.NoteDAO;
import com.multi.quizwiki.note.entity.NoteEntity;
import com.multi.quizwiki.note.entity.NotePageEntity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
@Transactional
public class NoteServiceImpl implements NoteService{
	
	private NoteDAO dao;
	
	@Autowired
	public NoteServiceImpl(NoteDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public NoteEntity note_insert(NoteEntity note, List<NotePageEntity> notePageList) {
		NoteEntity n = dao.note_insert(note);
		int noteId = n.getNoteId();

		notePageList.forEach((e)->{
			e.setNoteId(noteId);
		});
		
		dao.notepage_insert(notePageList);
		return n;
	}

	@Override
	public NoteEntity note_findById(int id) {
		return dao.note_findById(id);
	}

	@Override
	public Page<NoteEntity> findByMemberId(String memberId, Pageable pageable) {
		return dao.findByMemberId(memberId, pageable);
	}
	
}
