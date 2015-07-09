package com.app.chatroom.interfaces;

public interface Expression {
	void deteleDialog();

	void addDialog(int i, int w, int h, int[] x_y);

	void changeExpressionCount(int age);

	/**
	 * 向输入框加入文字表情
	 * 
	 * @param i
	 */
	void addMessage(int i);
}
