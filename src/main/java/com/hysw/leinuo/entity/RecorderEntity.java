package com.hysw.leinuo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by leinuo on 17-2-16.
 * 监测数据表
 */
@Entity
@Table(name ="recorder")
public class RecorderEntity implements  ShardableEntity {

    @Id
    @Column
    private Long id;
    @Column
    private String data;
    @Column
    private Date date;
    @Column
    private String number;

    public RecorderEntity(){}

    public RecorderEntity(String data, Date date, String number) {
        this.data = data;
        this.date = date;
        this.number = number;
    }

    public RecorderEntity(Long id, String data, Date date, String number) {
        this.id = id;
        this.data = data;
        this.date = date;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdentifier(){
        return this.number;
    }

    public String toString(){
        return "{ id=\"" + this.id + "\""
                + ", data=\"" + this.data + "\""
                + ", date=\"" + this.date + "\""
                + ", number=\"" + this.number + "\" }";
    }
}
