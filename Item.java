package com.website.rssreader;

public class Item {

	    private String title;
	    private String link;

	    public Item(String title, String link) {
	        setTitle(title);
	        setLink(link);
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public void setLink(String link) {
	        this.link = link;
	    }

	    public String getTitle() {
	        return this.title;
	    }

	    public String getLink() {
	        return this.link;
	    }

}
