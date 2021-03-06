package cn.lynu.lyq.signin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.lynu.lyq.signin.model.SeatAvailable;

@Service
public interface SeatAvailableService {
	public List<SeatAvailable> findAllSeatAvailable();
	public boolean deleteAvailableSeatForRowAndColumn(int row, int column);
	public boolean deleteAllAvailableSeat();
	public boolean updateSeatForRowAndColumn(int row, int column);
	public boolean updateSeatForMultipleRowAndColumn(int rowNum, int colNum);
}