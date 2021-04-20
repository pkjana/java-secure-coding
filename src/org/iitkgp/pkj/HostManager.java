package org.iitkgp.pkj;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 
 * @since April ‎20, ‎2021, ‏‎4:34:59 PM
 * @author Pabitra K Jana, IIT Kharagpur
 * 
 */
public class HostManager {

	Logger logger = Logger.getLogger(HostManager.class.getName());

	public static void main(String[] args) {
		String hostName = getHostName().toUpperCase();
		System.out.println("Hostname : " + hostName);
	}

	public static String getHostName() {
		String os = System.getProperty("os.name").toLowerCase();
		String hostName = null;
		try {
			if (os.contains("win")) {
				hostName = System.getenv("COMPUTERNAME").trim();
			} else if (os.contains("nix") || os.contains("nux") || os.contains("mac os x")) {
				hostName = execReadToString("hostname").trim();
			}
		} catch (Exception e) {
			Logger.getLogger(HostManager.class.getName()).info("Hostname not found:" + e.getMessage());
		}
		return hostName;
	}

	public static String execReadToString(String execCommand) throws IOException {
		try (Scanner s = new Scanner(Runtime.getRuntime().exec(execCommand).getInputStream()).useDelimiter("\\A")) {
			return s.hasNext() ? s.next() : "";
		}
	}

}
