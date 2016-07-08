package net.micourse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class SortByDateUtil {
	public static List<HashMap<String, Object>> sortByDate(List<HashMap<String, Object>> list) throws ParseException{//逆序排列
		int size=list.size();
		for(int i=0;i<size;i++){
			for(int j=0;j<(size-1);j++){
				String dateStr1=(String)(list.get(j).get("date"));
				String dateStr2=(String)(list.get(j+1).get("date"));
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long date1=sdf.parse(dateStr1).getTime();
				long date2=sdf.parse(dateStr2).getTime();
				if(date1<date2){
					HashMap<String, Object> tmp=list.get(j);
					HashMap<String, Object> tmp1=list.get(j+1);
					list.set(j,tmp1);
					list.set(j+1, tmp);
				}
			}
			
		}
		return list;
		
	}
	
	public static List<HashMap<String, Object>> sortByDate2(List<HashMap<String, Object>> list) throws ParseException{//顺序排列
		int size=list.size();
		for(int i=0;i<size;i++){
			for(int j=0;j<(size-1);j++){
				String dateStr1=(String)(list.get(j).get("date"));
				String dateStr2=(String)(list.get(j+1).get("date"));
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long date1=sdf.parse(dateStr1).getTime();
				long date2=sdf.parse(dateStr2).getTime();
				if(date1>date2){
					HashMap<String, Object> tmp=list.get(j);
					HashMap<String, Object> tmp1=list.get(j+1);
					list.set(j,tmp1);
					list.set(j+1, tmp);
				}
			}
			
		}
		return list;
		
	}

	

}
