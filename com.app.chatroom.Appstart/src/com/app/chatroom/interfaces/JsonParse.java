package com.app.chatroom.interfaces;

import java.util.ArrayList;

public interface JsonParse<E> {
	ArrayList<E> parseJson(String jsonstr);
}
