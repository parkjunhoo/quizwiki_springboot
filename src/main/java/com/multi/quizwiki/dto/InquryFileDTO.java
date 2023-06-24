package com.multi.quizwiki.dto;



import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("inquryFile")
public class InquryFileDTO {
	private String inqury_file_id;
	private String inqury_id;
	private String inqury_origin;
	private String inqury_store;
	
}

