package pmf.mina.bjelica.travelholic.model.dto;

public class EmailDto {

	private String fromUsername;
	private String toUsername;
	private String text;
	
	public EmailDto() {
		// TODO Auto-generated constructor stub
	}
	
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getToUsername() {
		return toUsername;
	}
	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}
