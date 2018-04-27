package parser;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import common.ExtendedBufferedInputStream;
import reader.ContentReaderGZIP;


public class ContentParserGZIP {
	protected ContentReaderGZIP contentReaderGzip;
	protected ByteArrayOutputStream baos;

	public ContentParserGZIP() {
		init();
	}

	public void init() {
		contentReaderGzip = new ContentReaderGZIP();
		baos = new ByteArrayOutputStream();
	}

	public ByteArrayOutputStream extractAnchorText(ExtendedBufferedInputStream ebis, String uri) throws Exception {
		baos = contentReaderGzip.extractAnchorTextProcessed(ebis, uri);
		// baos = contentReader.extractAnchorUnprocessed(ebis);
		// System.out.println(baos.toString());
		return baos;
	}


}
