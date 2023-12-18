package com.util;



import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dao<T> {
	String clazzname;
	Class clazz;
	String jsonfile;
	public Dao(T entity) {
		clazz=entity.getClass();
		clazzname=clazz.getSimpleName().toLowerCase();
		jsonfile=clazzname+".csv";
		File file=new File(jsonfile);
		if(!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	//根据字段查询集合
	public List<T> queryByKey(String keyvalue){
		List<String> slist=FileUtil.readFileByLines(jsonfile);
		
		List<T> tlist=new ArrayList<>();
		for (int i = 0; i < slist.size(); i++) {
			T t=txtToEntity(slist.get(i));
			Object tvalue= getValue(t, getKey(), clazz);
			if(tvalue.toString().equals(keyvalue)){
				tlist.add(t);
			}
			
		}
		return tlist;
	}
	public List<T> query(T entity){
		List<T> list=getAll();
		List<String> prolist=new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			if(!field.getName().equals(getKey())&&!field.getName().equals("dbutil")){
				Object obj=getValue(entity, field.getName(), clazz);
				if(obj!=null&&!obj.toString().equals("")){
					prolist.add(field.getName());
				}
			}
		}
		List<T> resList = new ArrayList<>();
		for (T e : list) {
			boolean flag = true;
			if(flag){
				for(int i = 0; i < prolist.size(); i++){
					String filterProp = prolist.get(i);
					String keyword = getValue(entity, filterProp, clazz).toString();
					Object value = getValue(e, filterProp, clazz);
					if(value == null || !value.toString().toLowerCase().contains(keyword.toLowerCase())){
						flag = false;
						break;
					}
				}
			}
			if(flag){
				resList.add(e);
			}
		}
		list.clear();
		list.addAll(resList);
		return list;
	}
	private List<Field> getDeclaredFields(){
		List<Field> dlist=new ArrayList<>();
		for (int i = 0; i < clazz.getDeclaredFields().length; i++) {
			if(!clazz.getDeclaredFields()[i].getName().equals("dbutil")){
				dlist.add(clazz.getDeclaredFields()[i]);
			}
		}
		return dlist;
	}
	public T txtToEntity(String txt){
		T t = null;
		try {
			t = (T) clazz.newInstance();
			String pros[]=txt.split(",");
			for (int i = 0; i < pros.length; i++) {
				if(i==0){
					setValue(getDeclaredFields().get(i).getName(), clazz, t, Integer.valueOf(pros[i]));
				}else{
					setValue(getDeclaredFields().get(i).getName(), clazz, t, pros[i]);
				}
				
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}
	public String entityToTxt(T t){
		StringBuffer sbf = new StringBuffer();
		for (Field field : clazz.getDeclaredFields()) {
			if(!field.getName().equals("dbutil")){
				Object obj=getValue(t, field.getName(), clazz);
				sbf.append(obj).append(",");
			}
		}
		
		return sbf.toString();
	}
	//查询所有的数据
	public List<T> getAll(){
		List<String> slist=FileUtil.readFileByLines(jsonfile);
		List<T> tlist=new ArrayList<>();
		for (int i = 0; i < slist.size(); i++) {
			tlist.add(txtToEntity(slist.get(i)));
		}
		return tlist;
	}
	//删除
	public void delBykey(String key) {
		List<T> tlist=getAll();
		for (int i = 0; i < tlist.size(); i++) {
			Object value= getValue(tlist.get(i), getKey(), clazz);
			if(value.toString().equals(key)){
				tlist.remove(i);break;
			}
		}
		try {
			FileUtil.writeTxtFile(listToStr(tlist), new File(jsonfile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String listToStr(List<T> tlist){
		StringBuffer sbf=new StringBuffer();
		for (int i = 0; i < tlist.size(); i++) {
			sbf.append(entityToTxt(tlist.get(i))).append("\r\n");
		}
		return sbf.toString();
	}
	//根据主键修改
	public void update(T entity) {
		Integer value1=(Integer) getValue(entity, getKey(), clazz);
		List<T> tlist=getAll();
		for (int i = 0; i < tlist.size(); i++) {
			Integer value=(Integer) getValue(tlist.get(i), getKey(), clazz);
			
			if(value.toString().equals(value1.toString())){
				try {
					for (Field field : clazz.getDeclaredFields()) {
					  if(!field.getName().equals(getKey())&&!field.getName().equals("dbutil")){
						Object o=getValue(entity, field.getName(), clazz);
						setValue(field.getName(), clazz, tlist.get(i), o);
					  }
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		try {
			FileUtil.writeTxtFile(listToStr(tlist), new File(jsonfile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//新增
	public void add(T t) {
		setValue("id", clazz, t, new Random().nextInt(100000));
		List<T> tlist=getAll();
		tlist.add(t);
		try {
			FileUtil.writeTxtFile(listToStr(tlist), new File(jsonfile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Object getValue(Object entity, String fieldName, Class clazz) {
		PropertyDescriptor pd;
		try {
			pd = new PropertyDescriptor(fieldName, clazz);
			Method wM = pd.getReadMethod();
			return wM.invoke(entity)==null?"":wM.invoke(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public void setValue(String fieldName,Class clazz,Object o,Object fieldValue){
		try {
			PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
			Method wM = pd.getWriteMethod();
			wM.invoke(o, fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getKey() {
		try {
			for (Field field : clazz.getDeclaredFields()) {
					return field.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
