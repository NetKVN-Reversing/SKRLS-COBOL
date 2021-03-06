package org.jemiahlabs.skrls.kdm.models.code;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("model")
public class CodeModel extends XmiEntity {
	@XStreamAsAttribute
	private String name;
	@XStreamImplicit
	private List<CodeElement> codeElements;
	
	public CodeModel() {
		codeElements = new ArrayList<CodeElement>();
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addCodeElement(CodeElement element) {
		codeElements.add(element);
	}
	
	public List<CodeElement> getCodeElements() {
		return codeElements;
	}
}
