package cn.lynu.lyq.signin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lynu.lyq.signin.model.SeatAvailable;
import cn.lynu.lyq.signin.service.SeatAvailableService;
@Transactional
@Component("seatAvailableService")
public class SeatAvailableServiceImpl implements SeatAvailableService {
	private static Logger logger = LoggerFactory.getLogger(SeatAvailableServiceImpl.class);
	@Resource private SessionFactory sessionFactory;
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<SeatAvailable> findAllSeatAvailable(){
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("from SeatAvailable");
		@SuppressWarnings("unchecked")
		List<SeatAvailable> list  = (List<SeatAvailable>)query.list();
		if(list==null || list.size()==0){
//			logger.info("findAllSeatAvailable:没有找到任何座位可用");
			return null;
		}else{
			return list;
		}		
	}
	
	@Override
	public boolean deleteAvailableSeatForRowAndColumn(int row, int column){
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("delete from SeatAvailable where row=? and col=?");
		query.setInteger(0, row);
		query.setInteger(1, column);
		query.executeUpdate();
		logger.info("deleteAvailableSeatForRowAndColumn： 删掉第"+row+"行，"+"第"+column+"列的座位");
		return true;
	}	

	@Override
	public boolean deleteAllAvailableSeat(){
		Session s = sessionFactory.getCurrentSession();
		Query query = s.createQuery("delete from SeatAvailable");
		query.executeUpdate();
		logger.info("deleteAllAvailableSeat： 删掉所有的座位");
		return true;
	}
	
	@Override
	public boolean updateSeatForRowAndColumn(int row, int column){
		Session s = sessionFactory.getCurrentSession();
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
		return true;		
	}
	
	@Override
	public boolean updateSeatForMultipleRowAndColumn(int rowNum, int colNum){
		Session s = sessionFactory.getCurrentSession();
//		设置可用的座位之前，先删掉所有的座位信息 begin 2014-11-12
		Query query = s.createQuery("delete from SeatAvailable");
		query.executeUpdate();
//		设置可用的座位之前，先删掉所有的座位信息 end 2014-11-12
		for(int i=0; i<rowNum; i++){
			for(int j=0; j<colNum; j++){
				SeatAvailable seatAvailable=new SeatAvailable();
				seatAvailable.setRow(i+1);
				seatAvailable.setCol((16-colNum)/2+j+1);//座位要居中
				s.saveOrUpdate(seatAvailable);					
			}
		}
		return true;		
	}	
}
