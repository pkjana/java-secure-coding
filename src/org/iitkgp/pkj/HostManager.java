package org.iitkgp.pkj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 
 * @since April ‎20, ‎2021, ‏‎4:34:59 PM
 * @author Pabitra K Jana, IIT Kharagpur
 * 
 */
public class HostManager {

	static Logger logger = Logger.getLogger(HostManager.class.getName());

	public static void main(String[] args) {
		String hostName = getHostNameByEnvironmentProperties().toUpperCase();
		System.out.println("Hostname : " + hostName);
	}

	/**
	 * 
	 * The information returned by the call to getLocalHost() is not trustworthy.
	 * Attackers may spoof DNS entries. Do not rely on DNS for security.
	 * 
	 * try InetAddress.LocalHost first NOTE:
	 * InetAddress.getLocalHost().getHostName() will not work in certain
	 * environments.
	 * 
	 * @return
	 */
	public static String getHostName() {
		String hostName = "";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			// failed; try alternate means.
			logger.info("Hostname not found:" + e.getMessage());
		}
		return hostName;
	}

	// try environment properties.
	public static String getHostNameByEnvironmentProperties() {
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

	// try from host file in Linux
	public static String getHostNameFromHostFile() {
		String os = System.getProperty("os.name").toLowerCase();
		String hostName = null;
		try {
			if (os.contains("win")) {
				hostName = System.getenv("COMPUTERNAME").trim();
			} else if (os.contains("nix") || os.contains("nux") || os.contains("mac os x")) {
				hostName = execReadToString("cat /etc/hostname").trim();
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

	// To get hostname through Cross platform (Windows-Linux-Unix-Mac(Unix))
	// No DNS required
	public static String getCrossPlatformHostName() {
		String hostname = "";
		try {
			hostname = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("hostname").getInputStream()))
					.readLine();
		} catch (IOException e) {
			logger.info("Hostname not found:" + e.getMessage());
		}
		return hostname;
	}

}
