package org.jbltd.password.common;

public class IPassword {

    private String _usage, _content;
    
    public IPassword(String usage, String content) {
	this._usage = usage;
	this._content = content;
    }
    
    public String getName() {
	return _usage;
    }
    
    public String getPassword() {
	return _content;
    }
    
}
