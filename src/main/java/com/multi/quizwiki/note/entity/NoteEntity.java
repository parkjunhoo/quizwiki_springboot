package com.multi.quizwiki.note.entity;

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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class NoteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noteId;
	String memberId;
	String noteTitle;
	@CreationTimestamp
	Timestamp noteRegdate;
	boolean notePrivate;
	int noteShowCount;
	boolean noteDeleted;

	@Formula("(SELECT COUNT(1) FROM note_page np WHERE np.note_id = note_id)")
	private int pageCount;
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "noteId")
	private List<NotePageEntity> notePageList = new ArrayList<>();
	
}
