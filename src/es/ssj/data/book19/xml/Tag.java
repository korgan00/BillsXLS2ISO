package es.ssj.data.book19.xml;

import java.util.HashMap;

import es.ssj.data.book19.common.AlphaDataUnit;
import es.ssj.data.book19.common.NumericDataUnit;

public class Tag {
	public static String endl = "\r\n";//System.getProperty( "line.separator" );
	
	private int depth;
	private String strContent;
	private String tagName;
	private Tag[] tagsContent;
	private HashMap<String, String> attrs;

	public Tag(String tagName, String content) {
		this.tagName = tagName;
		this.strContent = content;
		this.tagsContent = null;
		this.depth = 1;
		this.attrs = new HashMap<String, String>();
	}
	public Tag(String tagName, NumericDataUnit content) {
		this.tagName = tagName;
		this.strContent = content.toString();
		this.tagsContent = null;
		this.depth = 1;
		this.attrs = new HashMap<String, String>();
	}
	public Tag(String tagName, int content) {
		this.tagName = tagName;
		this.strContent = content + "";
		this.tagsContent = null;
		this.depth = 1;
		this.attrs = new HashMap<String, String>();
	}
	public Tag(String tagName, double content) {
		this.tagName = tagName;
		this.strContent = content + "";
		this.tagsContent = null;
		this.depth = 1;
		this.attrs = new HashMap<String, String>();
	}
	public Tag(String tagName, AlphaDataUnit content) {
		this.tagName = tagName;
		this.strContent = content.toString();
		this.tagsContent = null;
		this.depth = 1;
		this.attrs = new HashMap<String, String>();
	}
	public Tag(String tagName, Tag...tags) {
		this.tagName = tagName;
		this.strContent = null;
		this.tagsContent = tags;
		this.depth = 1;
		this.attrs = new HashMap<String, String>();
		this.updateDepth();
	}

	private void updateDepth() {
		if (this.tagsContent == null) return;
		for (Tag t: this.tagsContent) {
			t.setDepth(this.depth + 1);
		}
	}
	public HashMap<String, String> attributes() {
		return this.attrs;
	}
	
	private void setDepth(int newDepth) {
		this.depth = newDepth;
		this.updateDepth();
	}
	
	private String strTabs() {
		String tabStr = "";
		for (int t = 0; t < depth; t++) { tabStr += "\t"; };
		return tabStr;
	}
	
	private String tagsContentToString() {
		String str = "";
		for (Tag t: this.tagsContent) {
			str += t.toString();
		}
		return str;
	}
	
	private String attrsToString() {
		String s = "";
		for (String k: attrs.keySet()) {
			s += " " + k + "=\"" + attrs.get(k) + "\"";
		}
		return s;
	}
	
	public String toString() {
		return strTabs() + 
				"<" + tagName + attrsToString() + ">" + 
					(strContent  != null ? strContent.trim() : tagsContent != null ? endl + tagsContentToString() + strTabs() : "") + 
			    "</" + tagName + ">" + endl;
	}
	
}