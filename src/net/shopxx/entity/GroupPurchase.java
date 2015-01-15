package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Entity
@Table(name = "xx_group_purchases") 
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "xx_group_purchase_sequences")
public class GroupPurchase extends OrderEntity{

	private static final long serialVersionUID = 1L;
	
	
 	/**团购名称*/
	private String name;
	
	/** 类型 */
	private String type;
	

	/**图片路径*/
    private String path;
	
	
	/**团购描述*/
	private String grdesc;
	
	
	/**预热开始时间*/ 
	private Date  prbegindate;
	
	/**预热结束时间*/
	private Date prenddate;
	
	/**开团时间*/
	private Date begindate;
	  
	/**团购结束时间*/  
	private Date enddate;
	
	/**所有详情图片*/
	private String images;
	   
    /**开团提醒人数
    private int wantcount;*/
	

	/**参团人数1*/
    private int wantcount1;
    /**参团人数2*/
    private int wantcount2;
    /**参团人数3*/
    private int wantcount3;
    /**参团人数4*/
    private int wantcount4;
    /**参团人数5*/
    private int wantcount5;
	
	/**对应团购价1*/
    private BigDecimal purchasePrice1;
    /**对应团购价2*/
    private BigDecimal purchasePrice2;
    /**对应团购价3*/
    private BigDecimal purchasePrice3;
    /**对应团购价4*/
    private BigDecimal purchasePrice4;
    /**对应团购价5*/
    private BigDecimal purchasePrice5;
    
    /**团购价*/
    private BigDecimal purchasePrice;
    
    

	/**市场价*/
    private BigDecimal marketPrice;
    
    /**定金*/
    private BigDecimal previousPrice;
    
	/**关联商品*/
    private Product product;
    
    /** 允许参与商品 */
	 private Set<Product> products = new HashSet<Product>();
    
	/**购买人数*/
    private int buycount; 
    
    
    /**开团提醒*/
    private Set<GroupRemind> remind=new HashSet<GroupRemind>();
     
    /** 
     * 获取开团提醒
     * @r
     * eturn
     */
    @NotNull
    @OneToMany(mappedBy = "groupPurchase", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    public Set<GroupRemind> getRemind() {
		return remind;
	}
	public void setRemind(Set<GroupRemind> remind) {
		this.remind = remind;
	}
	
	/**
     * 获取团购名称
     * @return
     */
    @NotNull
    @Length(max=255)
    @Column(nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取预热开始时间
	 * @return
	 */
	@NotNull
	@Column(nullable = false)
	public Date getPrbegindate() {
		return prbegindate;
	}
	public void setPrbegindate(Date prbegindate) {
		this.prbegindate = prbegindate;
	}
	/**
	 * 获取预热结束时间
	 * @return
	 */
	@NotNull
	@Column(nullable = false)
	public Date getPrenddate() {
		return prenddate;
	}
	public void setPrenddate(Date prenddate) {
		this.prenddate = prenddate;
	}
	/**
	 * 获取开团开始时间
	 * @return
	 */
	@NotNull
	@Column(nullable = false)
	public Date getBegindate() {
		return begindate;
	}
	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}
	/**
	 * 获取开团结束时间
	 * @return
	 */
	@NotNull
	@Column(nullable = false)
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	/**
	 * 获取市场价格
	 * @return
	 */
	@NotNull
	@Column(nullable = false)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	/**
	 * 获取团购价格
	 * @return
	 */
	@NotNull
	@Column(nullable = false)
	public BigDecimal getPurchasePrice1() {
		return purchasePrice1;
	}
	public void setPurchasePrice1(BigDecimal purchasePrice1) {
		this.purchasePrice1 = purchasePrice1;
	}
	
	@NotNull
	@Column(nullable = false)
	public BigDecimal getPurchasePrice2() {
		return purchasePrice2;
	}
	public void setPurchasePrice2(BigDecimal purchasePrice2) {
		this.purchasePrice2 = purchasePrice2;
	}
	
	@NotNull
	@Column(nullable = false)
	public BigDecimal getPurchasePrice3() {
		return purchasePrice3;
	}
	public void setPurchasePrice3(BigDecimal purchasePrice3) {
		this.purchasePrice3 = purchasePrice3;
	}
	
	@NotNull
	@Column(nullable = false)
	public BigDecimal getPurchasePrice4() {
		return purchasePrice4;
	}
	public void setPurchasePrice4(BigDecimal purchasePrice4) {
		this.purchasePrice4 = purchasePrice4;
	}
	
	@NotNull
	@Column(nullable = false)
	public BigDecimal getPurchasePrice5() {
		return purchasePrice5;
	}
	public void setPurchasePrice5(BigDecimal purchasePrice5) {
		this.purchasePrice5 = purchasePrice5;
	}
	
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	@Field(store = Store.YES, index = Index.TOKENIZED, analyzer = @Analyzer(impl = IKAnalyzer.class))
	@Lob
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	
	/**
	 * 获取参团人数
	 * @return 
	 */
	@NotNull
	public int getWantcount1() {
		return wantcount1;
	}
	public void setWantcount1(int wantcount1) {
		this.wantcount1 = wantcount1;
	}
	
	@NotNull
	public int getWantcount2() {
		return wantcount2;
	}
	public void setWantcount2(int wantcount2) {
		this.wantcount2 = wantcount2;
	}
	
	@NotNull
	public int getWantcount3() {
		return wantcount3;
	}
	public void setWantcount3(int wantcount3) {
		this.wantcount3 = wantcount3;
	}
	
	@NotNull
	public int getWantcount4() {
		return wantcount4;
	}
	public void setWantcount4(int wantcount4) {
		this.wantcount4 = wantcount4;
	}
	
	@NotNull
	public int getWantcount5() {
		return wantcount5;
	}
	public void setWantcount5(int wantcount5) {
		this.wantcount5 = wantcount5;
	}
	
	
	
	
	/**
	 * 获取定金
	 * @return
	 */
//	@NotNull
//	@Column(nullable = false)
	public BigDecimal getPreviousPrice() {
		return previousPrice;
	}
	public void setPreviousPrice(BigDecimal previousPrice) {
		this.previousPrice = previousPrice;
	}
	
	/**       
	 * 获取购买人数
	 * @param buycount
	 */
	@NotNull
	public int getBuycount() {
		return buycount;
	}
	
	public void setBuycount(int buycount) {
		this.buycount = buycount;
	} 

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

    @OneToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getGrdesc() {
		return grdesc;
	}
	public void setGrdesc(String grdesc) {
		this.grdesc = grdesc;
	}
	
	/**
	 * 获取允许参与商品
	 * 
	 * @return 允许参与商品
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "xx_group_purchases_product")
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置允许参与商品
	 * 
	 * @param products
	 *            允许参与商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
