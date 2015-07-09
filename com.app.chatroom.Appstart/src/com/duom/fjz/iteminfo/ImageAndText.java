package com.duom.fjz.iteminfo;

public class ImageAndText {
	    private String imageUrl;
	    private String text;
	    private String date ;

	    public ImageAndText(String imageUrl, String text ,String date) {
	        this.imageUrl = imageUrl;
	        this.text = text;
	        this.date = date ;
	    }
	    public String getImageUrl() {
	        return imageUrl;
	    }
	    public String getText() {
	        return text;
	    }
	    public String getDate() {
			return date;
		}
}

