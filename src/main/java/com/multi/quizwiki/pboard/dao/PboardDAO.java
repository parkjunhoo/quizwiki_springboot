package com.multi.quizwiki.pboard.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.multi.quizwiki.dto.PboardDTO;
import com.multi.quizwiki.pboard.entity.PboardCateEntity;
import com.multi.quizwiki.pboard.entity.PboardEntity;
import com.multi.quizwiki.pboard.entity.PboardLikeEntity;
import com.multi.quizwiki.pboard.entity.PboardReplyEntity;

public interface PboardDAO {
	public PboardEntity insert(PboardEntity pboard);
	public void pboard_edit(PboardEntity pboard);
	public void reply_insert(PboardReplyEntity pboardReply);
	
	public Page<PboardEntity> pboard_findByCate(int pboardCateId,Pageable pageable);
	public Page<PboardEntity> pboard_findByCateTitleContaining(int pboardCateId,String keyword,Pageable pageable);
	public Page<PboardEntity> pboard_findByCateMemberIdContaining(int pboardCateId, String keyword,Pageable pageable);
	public PboardEntity pboard_findByPboardId(int pboardId);
	
	public List<PboardCateEntity> pboardCate_findAll();
	
	public PboardLikeEntity pboardlike_findByMemeberIdAndPboardId(String memberId, int pboardId);
	public PboardLikeEntity pboardlike_insert(PboardLikeEntity pboardLike);
	
	public List<PboardDTO> pboard_findOrderByLikeCount(int limit);
	
	public List<PboardEntity>findTop10ByPboardStatusNotOrderByPboardShowCountDesc();
	
	public void incrementShowCount(int pboardId);
}
