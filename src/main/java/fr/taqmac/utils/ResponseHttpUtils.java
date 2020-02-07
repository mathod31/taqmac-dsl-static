package fr.taqmac.utils;

/**
 * Classe Tuple String,Int
 *
 */
public class ResponseHttpUtils {
	
	private int resultCode;
	private String resultContent;
	
	public ResponseHttpUtils(String content, int code) {
		this.resultContent = content;
		this.resultCode = code;
	}
	
	public void setResultCode(int code) {
		this.resultCode = code;
	}
	
	public int getResultCode() {
		return this.resultCode;
	}
	
	public void setResultContent(String content) {
		this.resultContent = content;
	}
	
	public String getResultContent() {
		return this.resultContent;
	}
}
