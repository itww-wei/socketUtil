package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

import util.XmlParserUtil;


public class Server {
	

	private ServerSocket ss;
	
	private InputStreamReader inputStreamReader; 
	
	private BufferedReader bufferedReader;
	
	public void server(){

		try {
			//让服务器端程序开始监听来自9101端口的客户端请求
			if (ss==null) {
				 ss = new ServerSocket(9101);
				 System.out.println("服务器启动...");
			}
			
			//服务器无穷的循环等待客户端的请求
			while(true){	
				/*
				 *accept()方法会在等待用户的socket连接时闲置着，当用户链接
				 *上来时，此方法会返回一个socket(在不同的端口上)以便与客户端
				 *通信。Socket与ServerSocket的端口不同，因此ServerSocket可以
				 *空闲出来等待其他客户端
				 */
				//这个方法会停下来等待要求到达之后再继续
				Socket s = ss.accept();
				
				inputStreamReader = new InputStreamReader(s.getInputStream());
				bufferedReader = new BufferedReader(inputStreamReader);
				
				StringBuffer request =new StringBuffer(); //bufferedReader.readLine();
				String reply = null;
				while (!((reply = bufferedReader.readLine()) == null)) {
					request.append(reply);
				}
			        
				System.out.println("接收到了客户端的请求:"+request);
				//PrintWriter printWriter = new PrintWriter(s.getOutputStream());
				
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "gb2312"));
				
				String advice = "I am Server";
				//回复信息
				advice=replyMessage(request.toString());
				System.out.println("回复信息: "+advice);
				//printWriter.println(advice);
				//printWriter.close();
				writer.write(advice);
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private String test1="<?xml version='1.0' encoding='gb2312'?><root><head><TransCode>3002</TransCode><TransDate>20161027</TransDate><TransTime>115615</TransTime><SeqNo>1477540575893</SeqNo></head><body><TransRltMsg>创建帐户成功</TransRltMsg><IAcctNo>76700188000120394</IAcctNo><AuthCode>3ns7lb</AuthCode></body></root>";
	private static String test2="<?xml version='1.0' encoding='gb2312'?><root><head><TransCode>3012</TransCode><TransDate>20161028</TransDate><TransTime>013820</TransTime><SeqNo>1477633100930</SeqNo></head><body><Result>1</Result><AddWord>保证金入账明细查询成功!!</AddWord><bank><InDate>20161025</InDate><InTime>143000</InTime><InAmount>1000</InAmount><InName>投标人员1</InName><InAcct>0123456789</InAcct><InMemo>76700188000121288</InMemo><HstSeqNum>20151211767001880001142220000240397</HstSeqNum><PunInst></PunInst><Gernal></Gernal></bank><bank><InDate>20161025</InDate><InTime>143000</InTime><InAmount>1000</InAmount><InName>投标人员2</InName><InAcct>1234512345</InAcct><InMemo>76700188000121288</InMemo><HstSeqNum>20151211767001880001142220000140389</HstSeqNum><PunInst></PunInst><Gernal></Gernal></bank><bank><InDate>20151211</InDate><InTime>161200</InTime><InAmount>1000</InAmount><InName>投标人员3</InName><InAcct>9003010200030390</InAcct><InMemo>76700188000121288</InMemo><HstSeqNum>2015121176700188000114483000023821</HstSeqNum><PunInst></PunInst><Gernal></Gernal></bank><bank><InDate>20151211</InDate><InTime>161200</InTime><InAmount>80</InAmount><InName>投标人员4</InName><InAcct>76680106760000001</InAcct><InMemo>76700188000121288</InMemo><HstSeqNum>2015121176700188000114483000013814</HstSeqNum><PunInst></PunInst><Gernal></Gernal></bank></body></root>";
	private String test3="<?xml version='1.0' encoding='gb2312'?><root><head><TransCode>3032</TransCode><TransDate>20161027</TransDate><TransTime>010726</TransTime><SeqNo>7b7d66c2-e224-4e8f</SeqNo></head><body><AddWord>接收成功</AddWord><BanK><HstSeqNum>20151211767001880001142220000240397</HstSeqNum><InAcctNo>0123456789</InAcctNo><InName>投标人员1</InName><Result>1</Result></BanK></body></root>";
	private String test4="<?xml version='1.0' encoding='gb2312'?><root><body><TransCode>3042</TransCode><Result>1</Result><AddWord>项目开标时间维护成功</AddWord></body></root>";
	private String test5="<?xml version='1.0' encoding='gb2312'?><root><head><TransCode>3052</TransCode><TransDate>20161107</TransDate><TransTime>112701</TransTime><SeqNo>1478489221902</SeqNo></head><body><Result>2</Result><AddWord>已退款</AddWord><bank><InDate>20161028</InDate><InTime>103144</InTime><InAmount>1000</InAmount><InName>投标人员1</InName><InAcct>76700188000120133</InAcct><InMemo>76700188000121288</InMemo><HstSeqNum>20151211767001880001142220000240397</HstSeqNum><PunInst>0.07</PunInst><BjPunInst></BjPunInst><Result>3</Result><AddWord>已退款</AddWord></bank><bank><InDate>20161102</InDate><InTime>103628</InTime><InAmount>1000</InAmount><InName>投标人员2</InName><InAcct>9003010200030390</InAcct><InMemo>76700188000121288</InMemo><HstSeqNum>2015121176700188000114483000023821</HstSeqNum><PunInst>7.27</PunInst><BjPunInst></BjPunInst><Result>1</Result><AddWord>初始接收</AddWord></bank></body></root>";
	
	/**
	 * 回复信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private String replyMessage(String request) throws Exception {
		JSONObject json = XmlParserUtil.xmlParserJson(request);
		
		JSONObject root = json.getJSONObject("root");
		JSONObject head = root.getJSONObject("head");
		String transCode = head.getString("TransCode");
		System.out.println("响应码："+transCode);

		String path="";
		String message="";
		switch (transCode) {
		case "3001"://推送信息
			message=test1;
			break;
		case "4011"://入账查询
			message=test2;
			break;
//		case "3021":
//			break;
		case "3031"://保证金退款
			message=test3;
			break;
		case "3041"://开标时间维护
			message=test4;
			break;
		case "3051"://退款明细查询
			message=test5;
			break;
		default:
			message="响应码错误";
			break;
		}
		return message;
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.server();
		
	}
	
	
}

