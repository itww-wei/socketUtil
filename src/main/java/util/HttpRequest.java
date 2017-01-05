package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
//		String res = sendGet("http://220.180.62.4:8099/web/getWebDetail","id=297edff858d1dbe70158d1fa60670012");
//		String res = sendPost("http://192.168.7.18:8080/user-login-sys-new/userlogin","loginName=superuser&passWord=123456");
//		System.err.println(res);
//		String res1 = sendPost("http://192.168.7.18:8080/business/projectmanage/list","roleId=buyer");
//		System.err.println(res1);
    	
//    	String responseFile="竞标授权书，授权书后面还需附授权代表的身份证复印件@竞标授权代表的社保证明（法定代表人无须社保证明，只需提供法定代表人身份证复印件）@有效的营业执照、税务登记证、组织机构代码证(复印件)等，或“三证合一”的营业执照@竞标函@报价表@竞标响应表@无重大违法记录声明函@业绩：近     年内（   年    月至    年    月）单项合同不低于20万元的  业绩合同书；@其它资料@竞价内容@-@-@";
//    	List<String> resList = Arrays.asList(responseFile.split("@"));
//    	
////    	for (String s : resList) {
////			System.err.println(s);
////		}
//    	System.err.println(resList.indexOf("竞价内容"));
    	
    	String res = sendPost("http://60.173.247.222:8080/zsipas/j_spring_security_check","j_username=wwang&j_password=wwang.123");
    	sendPost("http://60.173.247.222:8080/zsipas/zs/ZsRbzb/checkTime","");
//    	http://60.173.247.222:8080/zsipas/zs/ZsRbzb/save
//    		bt:【070汪玮】2016年12月06日工作日报
//    		ewnum:1
//    		mrnum:0
//    		zrnum:
//    		xfnum:
//    		ew1:项目操作菜单加入缓存中
//    		ewwczt1:1
//    		ewwcbl1:
//    		ewsm1:项目操作菜单加入缓存中
//    		fysj:
//    		fyje:
//    		sfpp:
//    		fysm:需要说明费用使用情况，不匹配原因，如果是多项费用，需分项说明
    	
    	
		System.err.println(res);
	}
}