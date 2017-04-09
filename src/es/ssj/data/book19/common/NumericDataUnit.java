package es.ssj.data.book19.common;

public class NumericDataUnit extends AbstractDataUnit<Long> {
	
	public NumericDataUnit(int l) {
		super();
		super.length = l;
	}
	public NumericDataUnit(int l, long i) {
		super();
		super.length = l;
		this.set(i);
	}
	
	public void set(int i) {
		this.set((long)i);
	}
	
	public String toString() {
		String s = String.format("%" + this.length + "s", this.info.toString()).replace(' ', '0');
		
		if ( s.length() > this.length ) {
			throw new RuntimeException("Longitud de número(" + this.info + ") excedida. Máximo: " + this.length + " dígitos.");
		}
		
		return s;
	}
	
}
