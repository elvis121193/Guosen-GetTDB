package ariera;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import cn.com.wind.td.tdb.Future;
import cn.com.wind.td.tdb.KLine;

public class Tools {
	public static StringBuilder TickToString(KLine kline) {
		StringBuilder sb = new StringBuilder("");
		sb.append(String.valueOf(kline.getDate()));
		sb.append(",");
		sb.append(String.valueOf(kline.getTime()));
		sb.append(",");
		sb.append(String.valueOf(kline.getClose()));
		sb.append(",");
		sb.append(String.valueOf(kline.getVolume()));
		sb.append(",");
		sb.append(String.valueOf(kline.getTurover()));
		sb.append("\n");
		return sb;
	}
	
	public static StringBuilder KlineToString(KLine kline) {
		StringBuilder sb = new StringBuilder("");
		sb.append(String.valueOf(kline.getDate()));
		sb.append(",");
		sb.append(String.valueOf(kline.getTime()));
		sb.append(",");
		sb.append(String.valueOf(kline.getHigh()));
		sb.append(",");
		sb.append(String.valueOf(kline.getOpen()));
		sb.append(",");
		sb.append(String.valueOf(kline.getLow()));
		sb.append(",");
		sb.append(String.valueOf(kline.getClose()));
		sb.append(",");
		sb.append(String.valueOf(kline.getVolume()));
		sb.append(",");
		sb.append(String.valueOf(kline.getTurover()));
		sb.append("\n");
		return sb;
	}

	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			System.out.println("delete file " + fileName + "success.");
			return true;
		} else {
			return false;
		}
	}

	public static StringBuilder FutureToString(Future future) {
		StringBuilder sb = new StringBuilder("");
		sb.append(String.valueOf(future.getDate()));
		sb.append(",");
		sb.append(String.valueOf(future.getTime()));
		sb.append(",");
		sb.append(String.valueOf(future.getHigh()));
		sb.append(",");
		sb.append(String.valueOf(future.getOpen()));
		sb.append(",");
		sb.append(String.valueOf(future.getLow()));
		sb.append(",");
		sb.append(String.valueOf(future.getPrice()));
		sb.append(",");
		sb.append(String.valueOf(future.getVolume()));
		sb.append(",");
		sb.append(String.valueOf(future.getPrePosition()));
		sb.append(",");
		sb.append(String.valueOf(future.getPosition()));
		sb.append(",");
		sb.append(String.valueOf(future.getPreClose()));
		sb.append(",");
		sb.append(String.valueOf(future.getSettle()));
		sb.append(",");
		sb.append(String.valueOf(future.getPreSettle()));
		sb.append(",");
		sb.append(String.valueOf(future.getAccTurover()));
		sb.append(",");
		sb.append(String.valueOf(future.getAccVolume()));
		sb.append(",");
		sb.append(String.valueOf(future.getTurover()));
		sb.append("\n");
		return sb;
	}

	public static boolean WriteStringToFile2(String filePath, Future[] fs) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			StringBuilder temp = new StringBuilder("");
			int i = 0;
			int len = fs.length;
			String code = fs[0].getWindCode();
			while (i < len) {
				temp.append(FutureToString(fs[i]));
				i += 1;
				if (i % GetTDB.loopAppend == 0 || i == len) {
					bw.append(temp);
					temp.setLength(0);
					;
					System.out.println(">>>" + getTime() + " " + code + " " + i + "/" + len);
				}
			}
			bw.close();
			fw.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean WriteTicksToFile2(String filePath, KLine[] fs) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			StringBuilder temp = new StringBuilder("");
			int i = 0;
			int len = fs.length;
			String code = fs[0].getWindCode();
			while (i < len) {
				temp.append(TickToString(fs[i]));
				i += 1;
				if (i % GetTDB.loopAppend == 0 || i == len) {
					bw.append(temp);
					temp.setLength(0);
					System.out.println(">>>" + getTime() + " " + code + " " + i + "/" + len);
				}

			}
			bw.close();
			fw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean WriteStringToFile2(String filePath, KLine[] fs) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			StringBuilder temp = new StringBuilder("");
			int i = 0;
			int len = fs.length;
			String code = fs[0].getWindCode();
			while (i < len) {
				temp.append(KlineToString(fs[i]));
				i += 1;
				if (i % GetTDB.loopAppend == 0 || i == len) {
					bw.append(temp);
					temp.setLength(0);
					System.out.println(">>>" + getTime() + " " + code + " " + i + "/" + len);
				}

			}
			bw.close();
			fw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean WriteStringToFile2(String filePath, String str) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(str);
			bw.close();
			fw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Timestamp getTime() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}

	public static String[] GetSymbolsFromFile(String path){
		ArrayList<String> array = new ArrayList<String>();  
		try {
			FileReader reader = new FileReader(path);
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			while((str = br.readLine()) != null) {
				array.add(str);
			}
			br.close();
			reader.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Read File " + path + "Failed.");
			return null;
		}
		return (String[]) array.toArray(new String[array.size()]);
	}
}
