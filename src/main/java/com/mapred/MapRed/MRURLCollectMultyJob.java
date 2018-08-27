package com.mapred.MapRed;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import inputformat.RecordInputFormat;

public class MRURLCollectMultyJob {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.setBoolean("mapred.compress.map.output", true);
		conf.setClass("mapred.map.output.compression.codec", GzipCodec.class, CompressionCodec.class);
		conf.setDouble("mapred.job.shuffle.input.buffer.percent", 0.50);

		Job[] jobList = new Job[20];
		for (int i = 0; i < 20; i++) {
			jobList[i] = iniJob(conf, i);
		}

		ControlledJob[] cjobList = new ControlledJob[20];
		for (int i = 0; i < 20; i++) {
			cjobList[i] = new ControlledJob(conf);
			cjobList[i].setJob(jobList[i]);
		}

		for (int i = 1; i < 20; i++) {
			cjobList[i].addDependingJob(cjobList[i - 1]);
		}

		JobControl jc = new JobControl("MRURLIDCollect from 00 to 19");
		for (int i = 0; i < 20; i++) {
			jc.addJob(cjobList[i]);
		}

		Thread jcThread = new Thread(jc);
		jcThread.start();
		while (true) {
			if (jc.allFinished()) {
				System.out.println(jc.getSuccessfulJobList());
				jc.stop();
				System.exit(0);
			}
			if (jc.getFailedJobList().size() > 0) {
				System.out.println(jc.getFailedJobList());
				jc.stop();
				System.exit(1);
			}
		}

	}

	public static Job iniJob(Configuration conf, int num) throws Exception {

		String sNum = "";
		if (num < 10)
			sNum = "0" + num;
		else
			sNum = "" + num;

		Job job = Job.getInstance(conf);
		job.setJarByClass(MRURLCollectMultyJob.class);
		job.setJobName("MRURLIDCollect" + sNum);

		job.setNumReduceTasks(100);

		String disk = getDisk(num);
		String prePath = "/user/terrier/ClueWeb12/Corpus/" + disk + "/ClueWeb12_" + sNum + "/" + sNum;
		String allPath = new String();

		int sum = getSum(num);

		for (int i = 0; i < sum; i++) {
			String curS = String.valueOf(i);
			if (i < 10) {
				curS = "0" + curS;
			}
			String curPath = prePath + curS + "wb";
			if (i != sum - 1) {
				allPath = allPath + curPath + ",";
			} else {
				allPath = allPath + curPath;
			}
		}
//		System.out.println("sNum = " + sNum + " sum = " + sum + " allPath = " + allPath);
		FileInputFormat.addInputPaths(job, allPath);
		FileOutputFormat.setOutputPath(job, new Path("/user/s1721710/UrlIdIndex/output" + sNum));
		FileOutputFormat.setCompressOutput(job, true); // job使用压缩
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

		job.setMapperClass(MRURLCollectMapper.class);
		job.setInputFormatClass(RecordInputFormat.class);

		job.setReducerClass(MRURLCollectReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		return job;
	}

	public static String getDisk(int num) {
		String disk = "";
		switch (num) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			disk = "Disk1";
			break;
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
			disk = "Disk2";
			break;
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			disk = "Disk3";
			break;
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
			disk = "Disk4";
			break;
		}
		return disk;
	}

	public static int getSum(int num) {
		int sum = 0;
		switch (num) {
		case 0:
			sum = 14;
			break;
		case 1:
			sum = 13;
			break;
		case 2:
			sum = 13;
			break;
		case 3:
			sum = 13;
			break;
		case 4:
			sum = 13;
			break;
		case 5:
			sum = 13;
			break;
		case 6:
			sum = 13;
			break;
		case 7:
			sum = 18;
			break;
		case 8:
			sum = 19;
			break;
		case 9:
			sum = 21;
			break;
		case 10:
			sum = 22;
			break;
		case 11:
			sum = 19;
			break;
		case 12:
			sum = 19;
			break;
		case 13:
			sum = 17;
			break;
		case 14:
			sum = 17;
			break;
		case 15:
			sum = 17;
			break;
		case 16:
			sum = 18;
			break;
		case 17:
			sum = 18;
			break;
		case 18:
			sum = 16;
			break;
		case 19:
			sum = 15;
			break;
		}
		return sum;
	}

}
