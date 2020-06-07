package cps.mouradalpha;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The class <code>Proprieties</code> defines a set of properties used inside
 * the <code>Message</code> class.
 *
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class Proprieties implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * the map used to handle the properties
	 */
	protected HashMap<String, Object> map;

	/**
	 * default constructor
	 */
	public Proprieties() {
		map = new HashMap<String, Object>();
	}

	/**
	 * 
	 * @return the map
	 */
	public HashMap<String, Object> getMap() {
		return map;
	}

	/**
	 * set the map
	 * 
	 * @param map
	 */
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

	/**
	 * attribute a boolean property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the boolean value of the property
	 */
	public void putProp(String name, Boolean v) {
		map.put(name, v);
	}

	/**
	 * attribute a Byte property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the Byte value of the property
	 */
	public void putProp(String name, Byte v) {
		map.put(name, v);
	}

	/**
	 * attribute a Character property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the Character value of the property
	 */
	public void putProp(String name, Character v) {
		map.put(name, v);
	}

	/**
	 * attribute a Double property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the Double value of the property
	 */
	public void putProp(String name, Double v) {
		map.put(name, v);
	}

	/**
	 * attribute a Float property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the Float value of the property
	 */
	public void putProp(String name, Float v) {
		map.put(name, v);
	}

	/**
	 * attribute a Integer property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the Integer value of the property
	 */
	public void putProp(String name, Integer v) {
		map.put(name, v);
	}

	/**
	 * attribute a Long property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the Long value of the property
	 */
	public void putProp(String name, Long v) {
		map.put(name, v);
	}

	/**
	 * attribute a Short property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the Short value of the property
	 */
	public void putProp(String name, Short v) {
		map.put(name, v);
	}

	/**
	 * attribute a String property to the message
	 * 
	 * @param name the name of the property
	 * @param v    the String value of the property
	 */
	public void putProp(String name, String v) {
		map.put(name, v);
	}

	/**
	 * get the boolean property
	 * 
	 * @param name the name of the property
	 * @return the boolean value of the property
	 */
	public Boolean getBooleanProp(String name) {
		try {
			Boolean b = (Boolean) map.get(name);
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the Bye property
	 * 
	 * @param name the name of the property
	 * @return the Byte value of the property
	 */
	public Byte getByteProp(String name) {
		try {
			Byte b = (Byte) map.get(name);
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the Character property
	 * 
	 * @param name the name of the property
	 * @return the Character value of the property
	 */
	public Character getCharProp(String name) {
		try {
			Character c = (Character) map.get(name);
			return c;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the Double property
	 * 
	 * @param name the name of the property
	 * @return the Double value of the property
	 */
	public Double getDoubleProp(String name) {
		try {
			Double d = (Double) map.get(name);
			return d;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the Float property
	 * 
	 * @param name the name of the property
	 * @return the Float value of the property
	 */
	public Float getFloatProp(String name) {
		try {
			Float f = (Float) map.get(name);
			return f;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the Integer property
	 * 
	 * @param name the name of the property
	 * @return the Integer value of the property
	 */
	public Integer getIntProp(String name) {
		try {
			Integer i = (Integer) map.get(name);
			return i;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the Long property
	 * 
	 * @param name the name of the property
	 * @return the Long value of the property
	 */
	public Long getLongProp(String name) {
		try {
			Long l = (Long) (map.get(name));
			return l;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the Short property
	 * 
	 * @param name the name of the property
	 * @return the Short value of the property
	 */
	public Short getShortProp(String name) {
		try {
			Short s = (Short) map.get(name);
			return s;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the String property
	 * 
	 * @param name the name of the property
	 * @return the String value of the property
	 */
	public String getStringProp(String name) {
		try {
			String s = (String) map.get(name);
			return s;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
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
		Proprieties other = (Proprieties) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		return true;
	}

}
