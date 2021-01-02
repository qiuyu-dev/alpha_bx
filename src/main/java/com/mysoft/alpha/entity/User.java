package com.mysoft.alpha.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户(User)实体类
 *
 * @author makejava
 * @since 2020-08-02 16:14:12
 */
@Entity
@Table(name = "user")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class User implements Serializable {
    private static final long serialVersionUID = -51064457421366468L;
    /**
     * 用户主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sup_userid")
    private Integer supUserid;
    
    /**
     * 单位，所属团队
     */
    @Column(name = "team")
    private String team;
    
    /**
     * 职位
     */
    @Column(name = "post")
    private String post;
    /**
     * 账户名，验证，唯一
     */
    @Column(name = "username")
    private String username;
    /**
     * 密码，md5加密
     */
//    @JsonIgnore
    @Column(name = "password")
    private String password;
    /**
     * 盐值
     */
    @JsonIgnore
    @Column(name = "salt")
    private String salt;
    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;
    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;
    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;
    
    /**
     * 海报展示内容
     */
    @Column(name = "display")
    private String display;
    /**
     * 企业主体ID
     */
    @Column(name = "alpha_subject_id")
    private Integer alphaSubjectId;
    /**
     * 是否可用，0不可用，1可用
     */
    @Column(name = "enabled")
    private Integer enabled;
    /**
     * 操作员
     */
    @Column(name = "operator")
    private String operator;
    /**
     * 创建时间
     */
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 所属企业
     */

    @Transient
    private AlphaSubject alphaSubject;

    /**
     * Transient property for storing role owned by current user.
     */
    @Transient
    private List<AdminRole> roles;

    @Transient
    private BxPromotion bxPromotion;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAlphaSubjectId() {
        return alphaSubjectId;
    }

    public void setAlphaSubjectId(Integer alphaSubjectId) {
        this.alphaSubjectId = alphaSubjectId;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public AlphaSubject getAlphaSubject() {
        return alphaSubject;
    }

    public void setAlphaSubject(AlphaSubject alphaSubject) {
        this.alphaSubject = alphaSubject;
    }

    public List<AdminRole> getRoles() {
        return roles;
    }

    public void setRoles(List<AdminRole> roles) {
        this.roles = roles;
    }

    public Integer getSupUserid() {
        return supUserid;
    }

    public void setSupUserid(Integer supUserid) {
        this.supUserid = supUserid;
    }

    public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public BxPromotion getBxPromotion() {
        return bxPromotion;
    }

    public void setBxPromotion(BxPromotion bxPromotion) {
        this.bxPromotion = bxPromotion;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", salt='").append(salt).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", alphaSubjectId=").append(alphaSubjectId).append('\'');;
        sb.append(", enabled=").append(enabled).append('\'');;
        sb.append(", operator='").append(operator).append('\'');
        sb.append(", createTime=").append(createTime).append('\'');;
        sb.append(", post=").append(post).append('\'');
        sb.append(", display=").append(display).append('\'');
        sb.append(", team=").append(team).append('\'');
        sb.append(", supUserid=").append(supUserid).append('\'');
        sb.append(", alphaSubject=").append(alphaSubject).append('\'');
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}