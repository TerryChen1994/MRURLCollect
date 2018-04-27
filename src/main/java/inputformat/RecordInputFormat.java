package inputformat;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import entity.RecordContent;
import entity.RecordHeader;

public class RecordInputFormat extends FileInputFormat<RecordHeader, RecordContent>{

	@Override
	public RecordReader<RecordHeader, RecordContent> createRecordReader(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return new WarcRecordReader();
	}

	@Override
	protected boolean isSplitable(JobContext context, Path filename) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
