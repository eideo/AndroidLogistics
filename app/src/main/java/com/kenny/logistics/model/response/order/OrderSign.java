package com.kenny.logistics.model.response.order;

import java.util.Date;

public class OrderSign{
	//"")
	private Integer id;
	//"订单处理表id")
	private Integer fk_order_taking_id;
	//"订单表id")
	private Integer fk_order_customer_id;
	//"签收照片")
	private String order_img;
	//"")
	private Date time;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getFk_order_taking_id(){
		return fk_order_taking_id;
	}

	public void setFk_order_taking_id(Integer fk_order_taking_id){
		this.fk_order_taking_id = fk_order_taking_id;
	}

	public Integer getFk_order_customer_id(){
		return fk_order_customer_id;
	}

	public void setFk_order_customer_id(Integer fk_order_customer_id){
		this.fk_order_customer_id = fk_order_customer_id;
	}

	public String getOrder_img(){
		return order_img;
	}

	public void setOrder_img(String order_img){
		this.order_img = order_img;
	}

	public Date getTime(){
		return time;
	}

	public void setTime(Date time){
		this.time = time;
	}

}
