package com.mapred.MapRed;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import entity.TextPair;
public class MRURLCollectReducer extends Reducer<TextPair, Text, Text, Text> {
	public void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		Iterator<Text> iter = values.iterator();
		Text tUrl = new Text(iter.next());
		if(iter.hasNext()){
			Text tPagerank = new Text(iter.next());
			try{
				URL url = new URL(tUrl.toString());
				url.getAuthority();
				double pagerank = Double.parseDouble(tPagerank.toString());
			} catch (Exception e){
				e.printStackTrace();
			}
			
			String outputValue = key.getFirst().toString() + "\t" + tPagerank.toString();
			context.write(tUrl, new Text(outputValue));
		}

	}
}
