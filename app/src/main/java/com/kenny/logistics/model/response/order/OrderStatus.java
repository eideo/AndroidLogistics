package com.kenny.logistics.model.response.order;

import java.util.Date;

public class OrderStatus{
	//"")
	private Integer id;
	//"订单编号，随机生成")
	private String order_number;
	//"订单当前状态信息")
	private String status;
	//"操作的用户标识")
	private Integer fk_user_id;
	//"")
	private Date time;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getOrder_number(){
		return order_number;
	}

	public void setOrder_number(String order_number){
		this.order_number = order_number;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public Integer getFk_user_id(){
		return fk_user_id;
	}

	public void setFk_user_id(Integer fk_user_id){
		this.fk_user_id = fk_user_id;
	}

	public Date getTime(){
		return time;
	}

	public void setTime(Date time){
		this.time = time;
	}

}
