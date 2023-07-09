package com.multi.quizwiki.qboard.paging;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;


@Data
public class PagingResponse<T> {
	private List<T> list = new ArrayList<>();
    private Pagination pagination;

    public PagingResponse(List<T> list, Pagination pagination) {
        this.list.addAll(list);
        this.pagination = pagination;
    }

}
