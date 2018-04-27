package inputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import common.ExtendedGZIPInputStream;
import entity.FileHeader;
import entity.Record;
import entity.RecordContent;
import entity.RecordHeader;
import parser.FileHeaderParserGZIP;
import parser.RecordParserGZIP;

public class WarcRecordReader extends RecordReader<RecordHeader, RecordContent> {
	private long start;
	private long pos;
	private long end;
	private FSDataInputStream fileIn;
	private RecordHeader key;
	private RecordContent value;
	private ExtendedGZIPInputStream egzis;

	public WarcRecordReader() {

	}

	@Override
	public void initialize(InputSplit genericSplit, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		final FileSplit split = (FileSplit) genericSplit;
		final Configuration job = context.getConfiguration();
		this.start = split.getStart();
		this.end = this.start + split.getLength();
		final Path file = split.getPath();
		final FileSystem fs = file.getFileSystem(job);
		this.fileIn = fs.open(file);
		
		this.egzis = new ExtendedGZIPInputStream(fileIn);
		
		FileHeaderParserGZIP fileHeaderParser = new FileHeaderParserGZIP();
		FileHeader fileHeader = fileHeaderParser.parseFileHeader(egzis);
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		if (this.key == null) {
			this.key = new RecordHeader();
		}
		if (this.value == null) {
			this.value = new RecordContent();
		}

		RecordParserGZIP recordParser = new RecordParserGZIP();
		Record record;

		record = recordParser.getNextRecord(egzis);
		if (record != null) {
			this.key = record.getRecordHeader();
			this.value.setContent(record.getContent());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public RecordHeader getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return this.key;
	}

	@Override
	public RecordContent getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}
}
