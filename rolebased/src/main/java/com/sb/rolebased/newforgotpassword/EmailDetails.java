package com.sb.rolebased.newforgotpassword;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class EmailDetails {
	private String recipient;
	@Value("")
    private String msgBody;
	@Value("")
    private String subject;
    private String attachment;
    
    
    

	@Override
	public String toString() {
		return "EmailDetails [recipient=" + recipient + ", msgBody=" + msgBody + ", subject=" + subject
				+ ", attachment=" + attachment + "]";
	}
    
    
    

}
