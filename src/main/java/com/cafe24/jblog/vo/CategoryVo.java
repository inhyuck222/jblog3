package com.cafe24.jblog.vo;

public class CategoryVo {

	private Long no;
	private String name;
	private Long blogNo;
	private String description;
	private Long postNo;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getBlogNo() {
		return blogNo;
	}

	public void setBlogNo(Long blogNo) {
		this.blogNo = blogNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPostNo() {
		return postNo;
	}

	public void setPostNo(Long postNo) {
		this.postNo = postNo;
	}

}
