package com.mapred.MapRed;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import entity.RecordContent;
import entity.RecordHeader;
import parser.ContentParserGZIP;

public class MRURLCollectMapper extends Mapper<RecordHeader, RecordContent, Text, Text> {

	public void map(RecordHeader key, RecordContent value, Context context) throws IOException, InterruptedException {
		long lastProgressTS = 0; // 上一次发心跳的时间点
		long heartBeatInterval = 100000L; // 主动发心跳的间隔，100s，默认600s超时

		try {
			context.write(new Text(key.getWarcTargetUri()), new Text(""));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 主动发心跳
			if (System.currentTimeMillis() - lastProgressTS > heartBeatInterval) {
				context.progress();
				lastProgressTS = System.currentTimeMillis();
			}

		}

	}

}
