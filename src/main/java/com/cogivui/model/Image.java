package com.cogivui.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name = "IMAGE")
@AttributeOverride(name = "id", column = @Column(name = "IMAGE_ID"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Image extends BaseEntity {

	private static final long serialVersionUID = 1586920493817237903L;

	private String filename;

	private byte[] content;

	@Column(name = "FILENAME")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Lob
	@Column(name = "CONTENT")
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
