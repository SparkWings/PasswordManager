package org.jbltd.password.common;

public class IPassword {

    private String usage, content;
    
    public IPassword(String usage, String content) {
	this.usage = usage;
	this.content = content;
    }
    
    public String getName() {
	return usage;
    }
    
    public String getPassword() {
	return content;
    }
    
}
