package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * A blog user who can post blog entries and comments.
 * 
 * @author Vedran Kolka
 *
 */
@Entity
@Table(name = "blog_users")
@NamedQueries({
	@NamedQuery(name = "BlogUser.getByNick", query = "SELECT b FROM BlogUser b WHERE b.nick=:nick"),
	@NamedQuery(name = "BlogUser.getAll", query = "SELECT b FROM BlogUser b")
})
public class BlogUser {
	/** identification number of the user */
	private Long id;
	/** first name of the user */
	private String firstName;
	/** last name of the user */
	private String lastName;
	/** unique nickname of the user */
	private String nick;
	/** users email */
	private String email;
	/** calculated hash of the users password */
	private String passwordHash;
	/** list of blog entries of the user */
	private List<BlogEntry> blogEntries = new ArrayList<>();

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false, length = 30)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(nullable = false, length = 30)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(nullable = false, length = 50, unique = true)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(nullable = false, length = 30)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = false, length = 40)
	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	@OneToMany(mappedBy="creator", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("lastModifiedAt")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BlogUser))
			return false;
		BlogUser other = (BlogUser) obj;
		return Objects.equals(id, other.id);
	}

}
