package cps.mouradalpha;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * The class <code>TimeStamp</code> represent a TimesStamp for a specific
 * message
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class TimeStamp implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * the system time when the message is published
	 */
	protected long time;
	/**
	 * the name or IP address of the machine
	 */
	protected String timestamper;

	/**
	 * default constructor : the time = current system time and timestamper = IP
	 * address of the machine
	 * 
	 * @throws Exception
	 */
	public TimeStamp() throws Exception {
		time = System.currentTimeMillis();
		timestamper = InetAddress.getLocalHost().getHostName();
	}

	/**
	 * check whether the class is class is instantiated or not
	 * 
	 * @return true if the class is instantiated
	 */
	public boolean isInitialize() {
		return time != 0 && timestamper != null;
	}

	/**
	 * 
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * 
	 * @return the timestamper
	 */
	public String getTimestamper() {
		return timestamper;
	}

	/**
	 * attribute the timestaper a value
	 * 
	 * @param timestamper
	 */
	public void setTimestamper(String timestamper) {
		this.timestamper = timestamper;
	}

	/**
	 * attribute the time a value
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * Constructor with parameters 
	 * @param time the time 
	 * @param timestamper the timestamper
	 */
	public TimeStamp(long time, String timestamper) {
		this.time = time;
		this.timestamper = timestamper;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + ((timestamper == null) ? 0 : timestamper.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeStamp other = (TimeStamp) obj;
		if (time != other.time)
			return false;
		if (timestamper == null) {
			if (other.timestamper != null)
				return false;
		} else if (!timestamper.equals(other.timestamper))
			return false;
		return true;
	}

}
