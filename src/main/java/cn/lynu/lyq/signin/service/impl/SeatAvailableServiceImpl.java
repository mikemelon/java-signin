package cn.lynu.lyq.signin.service.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import cn.lynu.lyq.signin.db.HibernateSessionFactory;
import cn.lynu.lyq.signin.model.SeatAvailable;
import cn.lynu.lyq.signin.service.SeatAvailableService;
@Component("seatAvailableService")
public class SeatAvailableServiceImpl implements SeatAvailableService {

	public List<SeatAvailable> findAllSeatAvailable(){
		Session s = HibernateSessionFactory.getSession();
//		Configuration cfg=new Configuration().configure();
//		SessionFactory sf=cfg.buildSessionFactory();
//		Session s=sf.getCurrentSession();
		s.clear();
		String queryString = "from SeatAvailable";

		Query query = s.createQuery(queryString);
		
		@SuppressWarnings("unchecked")
		List<SeatAvailable> list  = (List<SeatAvailable>)query.list();
		if(list==null || list.size()==0){
//			System.out.println("findAllSeatAvailable:没有找到任何座位可用");
			return null;
		}else{
			return list;
		}		
	}
	
	public boolean deleteAvailableSeatForRowAndColumn(int row, int column){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();
		try{
			Query query = s.createQuery("delete from SeatAvailable where row=? and col=?");
			query.setInteger(0, row);
			query.setInteger(1, column);
			query.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		trans.commit();		
		return true;
	}	

	
	public boolean deleteAllAvailableSeat(){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();
		try{
			Query query = s.createQuery("delete from SeatAvailable");
			query.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		trans.commit();		
		return true;
	}
	
	public boolean updateSeatForRowAndColumn(int row, int column){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();
		try{
			Query query = s.createQuery("from SeatAvailable where row=? and col=?");
			query.setInteger(0, row);
			query.setInteger(1, column);			
			@SuppressWarnings("unchecked")
			List<SeatAvailable> list=(List<SeatAvailable>)query.list();
			
			if(list==null || list.size()==0){//没找到就新建一条
				SeatAvailable seatAvailable=new SeatAvailable();
				seatAvailable.setRow(row);
				seatAvailable.setCol(column);
				s.saveOrUpdate(seatAvailable);
			}else{//找到了就删除这条
				SeatAvailable seatAvailable=(SeatAvailable)list.get(0);
				s.delete(seatAvailable);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		trans.commit();		
		return true;		
	}
	
	public boolean updateSeatForMultipleRowAndColumn(int rowNum, int colNum){
		Session s = HibernateSessionFactory.getSession();
		Transaction trans = s.beginTransaction();
		try{
//			设置可用的座位之前，先删掉所有的座位信息 begin 2014-11-12
			Query query = s.createQuery("delete from SeatAvailable");
			query.executeUpdate();
			//设置可用的座位之前，先删掉所有的座位信息 end 2014-11-12
			for(int i=0; i<rowNum; i++){
				for(int j=0; j<colNum; j++){
					SeatAvailable seatAvailable=new SeatAvailable();
					seatAvailable.setRow(i+1);
					seatAvailable.setCol((16-colNum)/2+j+1);//座位要居中
					s.saveOrUpdate(seatAvailable);					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		trans.commit();		
		return true;		
	}	
}
