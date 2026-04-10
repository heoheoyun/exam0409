package com.example.util;

import lombok.Getter;
import lombok.ToString;

// 페이지 번호, 내비게이션 범위, 이전/다음 버튼 표시 여부 계산
@Getter
@ToString
public class PageHandler {

	private int totalCount;
	private int pageSize;
	private int naviSize = 5;
	private int totalPage;
	private int page;
	private int beginPage;
	private int endPage;
	private boolean showPrev;
	private boolean showNext;

	public PageHandler(int totalCount, int page, int pageSize) {
		this.totalCount = totalCount;
		this.page = page;
		this.pageSize = pageSize;

		this.totalPage = (int) Math.ceil(totalCount / (double) pageSize);
		this.beginPage = (page - 1) / naviSize * naviSize + 1;
		this.endPage = Math.min(beginPage + naviSize - 1, totalPage);
		this.showPrev = beginPage != 1;
		this.showNext = endPage != totalPage;
	}
}
