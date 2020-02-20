package com.example.retrofitdemo.domain;

public class CommentItem {

	public CommentItem(String articleId, String commentContent) {
		this.articleId = articleId;
		this.commentContent = commentContent;
	}

	/**
	 * articleId : 234123
	 * commentContent : 这是评论内容
	 */

	private String articleId;
	private String commentContent;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	@Override
	public String toString() {
		return "CommentItem{" +
				"articleId='" + articleId + '\'' +
				", commentContent='" + commentContent + '\'' +
				'}';
	}
}
