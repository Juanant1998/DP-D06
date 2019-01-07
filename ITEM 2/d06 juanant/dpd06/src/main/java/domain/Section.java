
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	private String	title;		// notBlank
	private String	text;		//notBlank
	private String	pictures;	//notBlank


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getText() {
		return this.text;
	}
	public void setText(final String text) {
		this.text = text;
	}
	@NotBlank
	@URL
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

}
