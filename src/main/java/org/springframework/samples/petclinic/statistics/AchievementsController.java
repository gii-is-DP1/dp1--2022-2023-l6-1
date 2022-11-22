/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.statistics;


import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class AchievementsController {

	private final String  ACHIEVEMENTS_LISTING_VIEW ="/achievements/AchievementsListing";
	
	private final AchievementsService achievementsService;

	@Autowired
	public AchievementsController(AchievementsService achievementsService) {
		this.achievementsService = achievementsService;
	}

	@GetMapping(value = "/achievements")
	public String initCreationForm(Map<String, Object> model) {
		Achievements achievements = new Achievements();
		model.put("achievements", achievements);
		return ACHIEVEMENTS_LISTING_VIEW;
	}

	
}