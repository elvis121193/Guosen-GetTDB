package ariera;

import java.util.ArrayList;
import java.util.Arrays;
import cn.com.wind.td.tdb.*;

public class GetTDB {

	public static int loopAppend = 1000000;
	
	public TDBClient client = new TDBClient();
	public ResLogin err;
	public GetTDB() {
		// TODO Auto-generated constructor stub
		//initial setting parameters
		//System.setProperty("java.library.path", "C:\\Users\\guosen\\Desktop\\java\\x64"); 
		OPEN_SETTINGS settings = new OPEN_SETTINGS();
		settings.setIP("172.24.182.19");
		settings.setPort(Integer.toString(10010));
		settings.setUser("zhaoyd");
		settings.setPassword("135246");
		
		settings.setRetryCount(3);
		settings.setRetryGap(10);
		settings.setTimeOutVal(500);

		err = client.open(settings);
		System.out.println(err == null);
		System.out.println(err.getInfo());
	}
	public int close(){
		int rs = 0;
		if(client != null){
			rs = client.close();
			System.out.println("client is not null: "+rs);
			return rs;
		}
		else
			return 0;
	}
	
	public String[] readSymbol(){
		if (this.client != null){
			Code[] codes = this.client.getCodeTable("");
			ArrayList<String> codesList  = new ArrayList<String>();
			for (int i = 0; i < codes.length ;i++) {
				//System.out.println(i + "\t" + codes[i].getWindCode() + "\t" + codes[i].getCNName() );
				//if(	codes[i].getWindCode().contains("SH") ){
				//	codesList.add(codes[i].getWindCode());
				//}
				codesList.add(codes[i].getWindCode());
			}
			return codesList.toArray(new String[codesList.size()]);
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		String errorCodePath = "error.csv";
		String CodePath = "success.csv";
		GetTDB getTdb = new GetTDB();
//		System.out.println(">>>"+Tools.getTime()+ " Total codes " +codes.length +"...");
		String[] codesArray = Tools.GetSymbolsFromFile("#symbols.csv");
		Arrays.sort(codesArray);
		System.out.println(">>>"+Tools.getTime()+ " After codes " + codesArray.length +"...");
		
		int startIndex = 2529;
		int endIndex = 3000;
		
		
		for (int i = startIndex ;i < endIndex ; i++) { //codesArray.length
			String code = codesArray[i];
			System.out.println(">>>" + Tools.getTime()+ " Start downloading  data of the " + i +"/"+ codesArray.length +" " + code +"...");
			ReqKLine req = new ReqKLine();
			req.setCycType(0);
			req.setBeginDate(20100101);
			req.setEndDate(20170605);
			req.setCode(code);
			KLine[] klines = getTdb.client.getKLine(req);
			
//			ReqFuture reqF = new ReqFuture();
//			reqF.setBeginDate(20150101);
//			reqF.setEndDate(20170615);
//			reqF.setCode(code);
//			
//			Future [] klines = getTdb.client.getFuture(reqF);
			
			
//			getTdb.client.getFuture(arg0);
			if(klines == null || klines.length <= 0){
				System.out.println(">>>" + Tools.getTime()+ " No data with  " + i +"th "+ code +" .");
				Tools.WriteStringToFile2(CodePath , Tools.getTime()+","+i+","+ code +","+ "NoData"+"\n");
				continue;
			}
			
			System.out.println(">>>" + Tools.getTime()+ " Download  data of the " + i +"th "+ code +" successed:" + klines.length);
//			String temp = "";
			
//			for (Future kLine : fs) {
//				temp += FutureToString(kLine)+"\n";
//				//System.out.println(KlineToString(kLine));
//			}
			Tools.deleteFile(code + ".csv");
			if(Tools.WriteTicksToFile2(code+".csv",klines))
			{
				System.out.println(">>>" + Tools.getTime()+ " "+ code +" success.\n");
				Tools.WriteStringToFile2(CodePath , Tools.getTime()+","+i+","+ code +","+ klines.length+"\n");
			}
			else
			{
				System.out.println(">>>" + Tools.getTime()+ " "+ code +" failed.<<<<<<<<<ERROR>>>>>>>>>>>>>\n");
				Tools.WriteStringToFile2(CodePath , Tools.getTime()+","+i+","+ code +","+ "ERROR!"+"\n");
			}
		}
		getTdb.close();

	}
}
