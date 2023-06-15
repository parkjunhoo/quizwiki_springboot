package com.multi.quizwiki.entity.pboard;

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
@Table(name = "pboard_cate")
public class PboardCateEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	String pboardCateId;
	String pboardCateName;
	String pboardCateDesc;
}
