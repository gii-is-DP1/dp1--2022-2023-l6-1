package org.springframework.samples.solitaire.statistics;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.solitaire.friends.Friends;
import org.springframework.samples.solitaire.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsService {

	
	private StatisticsRepository statisticsRepository;
	
	@Autowired
	public StatisticsService(StatisticsRepository statisticsRepository) {
		this.statisticsRepository = statisticsRepository;
	}
	
	@Transactional(readOnly = true)
	public Statistics findById(int id) throws DataAccessException{
		return statisticsRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Statistics> findByIdOptional(int id) throws DataAccessException{
		return statisticsRepository.findByPlayerIdOptional(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Statistics> findAll() throws DataAccessException{
		return statisticsRepository.findAll();
	}
	
	@Transactional
	public void saveStatistics(Statistics statistics) throws DataAccessException {
		//creating Friends
    	statisticsRepository.save(statistics);	
	}
	
	@Transactional
	public void deleteStatistics(Statistics statistics) throws DataAccessException {
		//deleting statistics
		
		statisticsRepository.delete(statistics);	
		
	}
	
}
