package test;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class BackupDatabase{

	private static String HOST = "192.168.1.192";
	private static int    PORT = 22;
	private static String ID = "root";
	private static String PW = "hsck@2301";
	private static String FILE_PATH = "/usr/local/mariadb-10.1/cmd/isbs/"; 	// 리눅스 패스
	//private static String EXE_CMD = "/usr/local/mariadb-10.1/bin/mysqldump --routines -u isbs -pisbs2301 isbsdb --socket=/data/dbms/mysql.sock >";
	private static String EXE_CMD = "/usr/local/mariadb-10.1/cmd/dump.sh";
	
	public static void main(String[] args) throws IOException {
		StringBuffer bakupFileNm = new StringBuffer();
		bakupFileNm.append(FILE_PATH);
		bakupFileNm.append("isbsdb_");
		bakupFileNm.append(getDateTime());
		bakupFileNm.append(".dmp");
				
		execute(bakupFileNm);
	}
	
	public static void execute(StringBuffer fileNm) {
		Session session = null;
        Channel channel = null;
        
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(ID, HOST, PORT);
            session.setPassword(PW);
         
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
         
            session.connect();
         
            channel = session.openChannel("exec");
         
            ChannelExec channelExec = (ChannelExec) channel;
            
            ((ChannelExec)channel).setCommand("ls -ltr");
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();
            
            System.out.println("==> Connected to " + HOST);
            
            channelExec.setCommand("pwd && " + "cd " + FILE_PATH + " && " + EXE_CMD);
            channelExec.connect();
            
            byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception e){}
	        }
      
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {  
                channel.disconnect();    
            }
            if (session != null) {
                session.disconnect();
            }
        }
	}

	public static String getDateTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String dateTime = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));

		return dateTime;
	}


}
