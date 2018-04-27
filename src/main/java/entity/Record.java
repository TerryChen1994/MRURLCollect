package entity;

import common.ExtendedBufferedInputStream;

public class Record {
	private RecordHeader recordHeader;
	private ExtendedBufferedInputStream content;
	
	public Record(){
		recordHeader = null;
		content = null;
	}
	
	public RecordHeader getRecordHeader() {
		return recordHeader;
	}
	public void setRecordHeader(RecordHeader recordHeader) {
		this.recordHeader = recordHeader;
	}
	public ExtendedBufferedInputStream getContent() {
		return content;
	}
	public void setContent(ExtendedBufferedInputStream content) {
		this.content = content;
	}
	
}
