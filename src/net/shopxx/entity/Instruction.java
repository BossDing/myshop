package net.shopxx.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 产品说明书
 * @author hao
 *
 */
@Entity
@Table(name = "xx_instruction")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_instruction_sequence")
public class Instruction extends BaseEntity {
	private static final long serialVersionUID = -919443093302118349L;
	/**说明书名称*/
	private String name;
//	/**产品名称*/
//	private String productName;
	/**链接*/
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
/*	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}*/
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
