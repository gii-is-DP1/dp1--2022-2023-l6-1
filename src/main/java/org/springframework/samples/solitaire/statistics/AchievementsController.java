package org.springframework.samples.solitaire.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.solitaire.player.Player;
import org.springframework.samples.solitaire.player.PlayerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AchievementsController {
	
	private static final String VIEWS_ACHIEVEMENTS = "achievements/myAchievements";
	
	private final AchievementsService achievementsService;
	private final StatisticsService statisticsService;
	private final PlayerService playerService;
	private final AchievementsStatisticsService achievementsStatisticsService;

	
	@Autowired
	public AchievementsController(AchievementsStatisticsService achievementsStatisticsService,AchievementsService achievementsService, StatisticsService statisticsService, PlayerService playerService) {
		this.achievementsStatisticsService = achievementsStatisticsService;
		this.achievementsService = achievementsService;
		this.statisticsService = statisticsService;
		this.playerService = playerService;
		 
	} 
	
	
	@GetMapping(value = "/achievements/achievementsStatistics")
	public String initUnlockForm(Map<String, Object> model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Player player = this.playerService.findByUsername(username);
		Integer id = player.getId();
		Statistics stats = this.statisticsService.findById(id);
		Integer id_stats = stats.getId();
		
		Collection<AchievementsStatistics> achievementStatistics = this.achievementsStatisticsService.findById(id_stats);
		List<Achievements> achievementsList = new ArrayList<>();
		for(AchievementsStatistics achievementStatistic:achievementStatistics) {
			achievementsList.add(achievementStatistic.getAchievement());
		}
		
		Iterable<Achievements> achievements = this.achievementsService.findAll();
		
		for(Achievements achievement:achievements) {
			String condicionStringC = achievement.getCondition_unlocked().split(">")[0];
			String condicionNumero = achievement.getCondition_unlocked().trim().split("=")[1];
			Integer condicionNumeroN = Integer.valueOf(condicionNumero);
			AchievementsStatistics achievementsStatistics = new AchievementsStatistics();
			if(condicionStringC.equals("games")) {
				if(stats.getGames()>=condicionNumeroN) {
					if(!achievementsList.contains(achievement)) {
						achievementsStatistics.setAchievement(achievement);
						achievementsStatistics.setStatistics(stats);
						
						this.achievementsStatisticsService.saveAchievementsStatistics(achievementsStatistics);
						
					}
				}
			}	
			
			else if(condicionStringC.equals("gamesWon")) {
				if(stats.getGamesWon() >= condicionNumeroN) {
					if(!achievementsList.contains(achievement)) {
						achievementsStatistics.setAchievement(achievement);
						achievementsStatistics.setStatistics(stats);
						this.achievementsStatisticsService.saveAchievementsStatistics(achievementsStatistics);
					}
				}
				
			}
			
			else if(condicionStringC.equals("gamesLost")) {
				if(stats.getGamesLost() >= condicionNumeroN) {
					if(!achievementsList.contains(achievement)) {
						achievementsStatistics.setAchievement(achievement);
						achievementsStatistics.setStatistics(stats);
						this.achievementsStatisticsService.saveAchievementsStatistics(achievementsStatistics);
					}
				}
				
			}
		}
		return "redirect:/achievements";
	}

	@GetMapping(value = "/achievements")
	public String initCreationForm(Map<String, Object> model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Player player = this.playerService.findByUsername(username);
		Integer id = player.getId();
		Statistics stats = this.statisticsService.findById(id);
		Integer id_stats = stats.getId();
		Collection<AchievementsStatistics> achievementStatistics = this.achievementsStatisticsService.findById(id_stats);
		List<Achievements> achievements = new ArrayList<>();
		for(AchievementsStatistics achievementStatistic:achievementStatistics) {
			achievements.add(achievementStatistic.getAchievement());
		}
		model.put("achievements",achievements);
		return VIEWS_ACHIEVEMENTS;
	}
	
	
	
}
