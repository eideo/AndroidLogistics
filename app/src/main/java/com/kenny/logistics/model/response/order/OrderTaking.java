package com.kenny.logistics.model.response.order;

import java.util.Date;

public class OrderTaking{
	//"")
	private Integer id;
	//"订单外键")
	private Integer fk_order_customer_id;
	//"车辆外键")
	private Integer fk_car_id;
	//"司机外键")
	private Integer fk_driver_id;
	//"应付账款")
	private Integer recive;
	//"应收账款")
	private Integer pay;
	//"关联status表")
	private String status;
	//"")
	private Date time;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getFk_order_customer_id(){
		return fk_order_customer_id;
	}

	public void setFk_order_customer_id(Integer fk_order_customer_id){
		this.fk_order_customer_id = fk_order_customer_id;
	}

	public Integer getFk_car_id(){
		return fk_car_id;
	}

	public void setFk_car_id(Integer fk_car_id){
		this.fk_car_id = fk_car_id;
	}

	public Integer getFk_driver_id(){
		return fk_driver_id;
	}

	public void setFk_driver_id(Integer fk_driver_id){
		this.fk_driver_id = fk_driver_id;
	}

	public Integer getRecive(){
		return recive;
	}

	public void setRecive(Integer recive){
		this.recive = recive;
	}

	public Integer getPay(){
		return pay;
	}

	public void setPay(Integer pay){
		this.pay = pay;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public Date getTime(){
		return time;
	}

	public void setTime(Date time){
		this.time = time;
	}

}
