package entity;

import common.ExtendedBufferedInputStream;

public class RecordContent {
	private ExtendedBufferedInputStream content;
	
	public RecordContent(){
		content = null;
	}
	
	public ExtendedBufferedInputStream getContent() {
		return content;
	}
	public void setContent(ExtendedBufferedInputStream content) {
		this.content = content;
	}
	
}
