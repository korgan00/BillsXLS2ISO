package es.ssj.data.book19.common;

public abstract class AbstractDataUnit<T> {
	
	protected int length;
	protected T info;

	//public void setLength(int l) { this.length = l; }
	public void set(T i) { this.info = i; }
	public T get() { return this.info; }
	public int getLength() { return this.length; }
	
	public String toString() {
		String s = String.format("%-" + this.length + "s", this.info.toString());

		if ( s.length() > this.length ) {
			s = s.substring(0, this.length);
		}
		
		return s;
	}
}

