package com.model;


public class Youxifenshu implements java.io.Serializable ,Comparable<Youxifenshu>{


	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String wanjia;
	public String getWanjia() {
		return this.wanjia;
	}
	public void setWanjia(String wanjia) {
		this.wanjia = wanjia;
	}
	private String yingdedejushu;
	public String getYingdedejushu() {
		return this.yingdedejushu;
	}
	public void setYingdedejushu(String yingdedejushu) {
		this.yingdedejushu = yingdedejushu;
	}
	@Override
	public int compareTo(Youxifenshu o) {
		return Integer.valueOf(o.getYingdedejushu())-Integer.valueOf(this.getYingdedejushu());
	}
	
}
