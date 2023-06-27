package com.multi.quizwiki.qboard.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.multi.quizwiki.qboard.dao.FileMapper;
import com.multi.quizwiki.qboard.dto.FileRequest;
import com.multi.quizwiki.qboard.dto.FileResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
	
	private final FileMapper filemapper;

	@Transactional
	public void saveFiles(Long qboard_Id, List<FileRequest> files) {
		if(CollectionUtils.isEmpty(files)) {
			return;
			
		}
		for (FileRequest file: files) {
			file.setQboardId(qboard_Id);
			log.info("파일 실행");
		}
		filemapper.saveAll(files);
	}
	
	public List<FileResponse> findAllByQboardId(Long qboard_id) {
		return filemapper.findAllByQboardId(qboard_id);
	}

	
	
	public List<FileResponse> findAllById(List <Long> ids) {
				if (CollectionUtils.isEmpty(ids)) {
			        return Collections.emptyList();
			    }
					return filemapper.findAllByIds(ids);
	}
	
	@Transactional
	public void deleteAllByIds(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        filemapper.deleteAllByIds(ids);
    }
	
	
	//파일 상세정보 조회
	 public FileResponse findById(final Long id) {
	        return filemapper.findById(id);
	    }
}
