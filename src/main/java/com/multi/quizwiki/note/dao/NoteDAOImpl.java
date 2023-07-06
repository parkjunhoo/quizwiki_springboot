package com.multi.quizwiki.note.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.multi.quizwiki.note.entity.NoteEntity;
import com.multi.quizwiki.note.entity.NotePageEntity;
import com.multi.quizwiki.note.repository.NotePageRepository;
import com.multi.quizwiki.note.repository.NoteRepository;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Repository
public class NoteDAOImpl implements NoteDAO{
	
	private NoteRepository noteRepo;
	private NotePageRepository pageRepo;
	
	@Autowired
	public NoteDAOImpl(NoteRepository noteRepo , NotePageRepository pageRepo) {
		this.noteRepo = noteRepo;
		this.pageRepo = pageRepo;
	}

	@Override
	public NoteEntity note_insert(NoteEntity note) {
		return noteRepo.save(note);
	}

	@Override
	public List<NotePageEntity> notepage_insert(List<NotePageEntity> notePageList) {
		return pageRepo.saveAll(notePageList);
	}

	@Override
	public NoteEntity note_findById(int id) {
		return noteRepo.findById(id).get();
	}

	@Override
	public Page<NoteEntity> findByMemberId(String memberId, Pageable pageable) {
		return noteRepo.findByMemberId(memberId, pageable);
	}
	
	
}
