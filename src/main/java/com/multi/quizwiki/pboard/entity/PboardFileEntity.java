package com.multi.quizwiki.pboard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pboard_file")
public class PboardFileEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int pboardFileId;
	String pboard_file_origin;
	String pboard_file_store;
	int pboardId;
}
