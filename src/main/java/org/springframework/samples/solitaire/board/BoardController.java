package org.springframework.samples.solitaire.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.solitaire.card.Card;
import org.springframework.samples.solitaire.card.CardService;
import org.springframework.samples.solitaire.playZone.PlayZone;
import org.springframework.samples.solitaire.player.Player;
import org.springframework.samples.solitaire.player.PlayerService;
import org.springframework.samples.solitaire.statistics.Statistics;
import org.springframework.samples.solitaire.statistics.StatisticsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {


	private static final String VIEWS_BOARD = "board/chooseDifficult";
	private static final String BOARD_1 = "board/board1";
	private static final String BOARD_2 = "board/board2";

	@Autowired
	BoardService boardService;
	@Autowired
	CardService cardService;
	@Autowired
	StatisticsService statisticsService;
	@Autowired
	PlayerService playerService;


	@GetMapping(value = "/startGame")
	public String initCreationForm(Map<String, Object> model) { 
		Board board = new Board();
		model.put("board", board);
		model.put("playzone", startGame()); 
		return VIEWS_BOARD;
	}
	
	Integer i = 1;
	@GetMapping(value = "/difficult1")
	public String creationEasyMode(Map<String, Object> model, HttpServletResponse response) {
		
		while(i==1) {
			response.addHeader("Refresh", "1");
			i++;
		}

		List<Card> monton1 = cardService.findAllCardsDeck(5, 0);
		List<Card> monton2 = cardService.findAllCardsDeck(6, 0);
		List<Card> monton3 = cardService.findAllCardsDeck(7, 0);
		List<Card> monton4 = cardService.findAllCardsDeck(8, 0);

		Integer TotalSize = monton1.size() + monton2.size() + monton3.size() + monton4.size();

		if(TotalSize == 52) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			Player player = playerService.findByUsername(username);
			Statistics statistics = statisticsService.findById(player.getId());
			statistics.setGames(statistics.getGames()+1);
			statistics.setGamesWon(statistics.getGamesWon()+1);
			statistics.setTotalScore((statistics.getGamesWon()*10-statistics.getGamesLost()*6)/statistics.getGames());
			statisticsService.saveStatistics(statistics);
			return "redirect:/board/youwin";
		}
		
		model.put("now", new Date());
		model.put("board", boardService.findById(1)); 
		return BOARD_1;
	}
	
	
	
	@GetMapping(value = "/board/giveup")
	public String creationGiveUp(Map<String, Object> model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Player player = playerService.findByUsername(username);
		Statistics statistics = statisticsService.findById(player.getId());
		statistics.setGamesLost(statistics.getGamesLost()+1);
		statistics.setTotalScore((statistics.getGamesWon()*10-statistics.getGamesLost()*6)/statistics.getGames());
		statistics.setGames(statistics.getGames()+1);
		statisticsService.saveStatistics(statistics);
		return "/board/giveUp";  
	}

	@GetMapping(value = "/board/youwin")
	public String creationYouWin(Map<String, Object> model) {
		

		return "/board/youWin";
	}


	//	====================================Play=================================================================

	@GetMapping(value = "/board/showCard/{boardId}")
	public String showCardBoard(@PathVariable("boardId") int boardId, Map<String, Object> model) {
		List<Card> cartasBoard = this.cardService.findAllCardsBoardId(boardId);
		List<Card> cartasF = this.cardService.findAllCardsBoardIdfalse(boardId);
		model.put("cardsVisible", cartasBoard);
		model.put("cardsNoVisibles", cartasF);
		return "redirect: board/board1";

	}


	@GetMapping(value = "/board/moveCard/{cardId}")
	public ModelAndView moveCard(@PathVariable("cardId") int cardId, Map<String, Object> model ) {
		Card card = this.cardService.findCardById(cardId);
		Integer boardId = card.getBoard().getId();
		//Cambiar logica para el resto de modos
		List<Card> cartasBoard = this.cardService.findAllCardsBoardId(1);
		List<Card> cartasF = this.cardService.findAllCardsBoardIdfalse(1);
		List<Card> cartasPosicion = this.cardService.findCardByPosition(card.getXPosition());
		Integer num = card.getNumber();

		if(card.getNumber() == 1) {
			switch(card.getSuit()) {
			case "DIAMONDS" :
				for(Card c1 : cartasPosicion) {
					if(c1.getYPosition() == card.getYPosition()-1 && c1.getIsShowed()== false) {
						c1.setIsShowed(true);
						this.cardService.saveCard(c1);
					}
				}
				card.setXPosition(5);
				card.setYPosition(0);
				card.setIsShowed(false);
				this.cardService.saveCard(card);
				break;
			case "SPADES" :
				for(Card c1 : cartasPosicion) {
					if(c1.getYPosition() == card.getYPosition()-1 && c1.getIsShowed()== false) {
						c1.setIsShowed(true);
						this.cardService.saveCard(c1);
					}
				}
				card.setXPosition(6);
				card.setYPosition(0);
				card.setIsShowed(false);
				this.cardService.saveCard(card);
				break;
			case "HEARTS" :
				for(Card c1 : cartasPosicion) {
					if(c1.getYPosition() == card.getYPosition()-1 && c1.getIsShowed()== false) {
						c1.setIsShowed(true);
						this.cardService.saveCard(c1);
					}
				}
				card.setXPosition(7);
				card.setYPosition(0);
				card.setIsShowed(false);
				this.cardService.saveCard(card);
				break;
			default :
				for(Card c1 : cartasPosicion) {
					if(c1.getYPosition() == card.getYPosition()-1 && c1.getIsShowed()== false) {
						c1.setIsShowed(true);
						this.cardService.saveCard(c1);
					}
				}
				card.setXPosition(8);
				card.setYPosition(0);
				card.setIsShowed(false);
				this.cardService.saveCard(card);
				break;
			}
		}else if(card.getNumber() == 13){ 
			for(Card c : cartasF) {
				if(c.getNumber() == 12 && c.getYPosition()==0 && (c.getXPosition()==5 || c.getXPosition()==6 || c.getXPosition()==7 || c.getXPosition( )==8)) {

					switch(card.getSuit()) {
					case "DIAMONDS" :

						card.setXPosition(5);
						card.setYPosition(0);
						card.setIsShowed(false);
						this.cardService.saveCard(card);
						break;
					case "SPADES" :

						card.setXPosition(6);
						card.setYPosition(0);
						card.setIsShowed(false);
						this.cardService.saveCard(card);
						break;
					case "HEARTS" :

						card.setXPosition(7);
						card.setYPosition(0);
						card.setIsShowed(false);
						this.cardService.saveCard(card);
						break;
					default :

						card.setXPosition(8);
						card.setYPosition(0);
						card.setIsShowed(false);
						this.cardService.saveCard(card);
						break;
					}
				}else{
					for(i=2;i<9;i++) {
						Card k = this.cardService.findAllCardsVacia(i,2);
						if(k == null){
							for(Card c1 : cartasPosicion) {
								if(c1.getYPosition() == card.getYPosition()-1 && c1.getIsShowed()== false) {
									c1.setIsShowed(true);
									this.cardService.saveCard(c1);
								}
							}
							List<Card> listaAuxiliar = new ArrayList<>();
							for(Card c2 : cartasBoard) {
								if(card.getYPosition()!=0) {
									if(card.getXPosition() == c2.getXPosition() && card.getYPosition() < c2.getYPosition() 
											&& card.getNumber() > c2.getNumber() && c2.getIsShowed()==true) {
										listaAuxiliar.add(c2);
									}
								}
							}
							card.setXPosition(i);
							card.setYPosition(2); 
							this.cardService.saveCard(card);
							Integer v = 1;
							for(Card cartaAux : listaAuxiliar) {
								cartaAux.setXPosition(card.getXPosition());
								cartaAux.setYPosition(card.getYPosition()+v);
								this.cardService.saveCard(cartaAux);
								v++;
							}
							break;
						}
					}
				}
			}
		}else{ 
			for(Card c : cartasF) {
				Card k = this.cardService.findAllCardsVacia(card.getXPosition(),card.getYPosition()+1);
				if((card.getYPosition()==0 && card.getXPosition()==1) || k==null) {
					if(c.getNumber() == num -1 && c.getSuit().equals(card.getSuit()) && c.getYPosition()==0 && (c.getXPosition()==5||
							c.getXPosition()==6||c.getXPosition()==7||c.getXPosition()==8)) {
						for(Card c1 : cartasPosicion) {
							if(c1.getYPosition() == card.getYPosition()-1 && c1.getIsShowed()== false) {
								c1.setIsShowed(true);
								this.cardService.saveCard(c1);
							}
						}
						card.setXPosition(c.getXPosition()); 
						card.setYPosition(c.getYPosition()); 
						card.setIsShowed(false);
						this.cardService.saveCard(card);
					}
				}
			}
			model.put("board", boardService.findById(boardId));			
		}
		return new ModelAndView("redirect:/difficult1");
	}



	@GetMapping(value = "/board/moveCardP/{card1Id}&{cardId}")
	public ModelAndView moveCard(@PathVariable("card1Id") int card1Id, @PathVariable("cardId") int cardId, Map<String, Object> model ) {
		Card card = this.cardService.findCardById(cardId); //--> donde
		Card card1 = this.cardService.findCardById(card1Id); //--> mazo
		Integer boardId = card1.getBoard().getId();
		//Cambiar logica para el resto de modos
		List<Card> cartasBoard = this.cardService.findAllCardsBoardId(1);
		List<Card> cartasF = this.cardService.findAllCardsBoardIdfalse(1);
		List<Card> cartasPosicion = this.cardService.findCardByPosition(card1.getXPosition());
		Integer num = card1.getNumber();

		List<Card> listaAuxiliar = new ArrayList<>();
		for(Card c : cartasBoard) {
			if(card1.getYPosition()!=0) {
				if(card1.getXPosition() == c.getXPosition() && card1.getYPosition() < c.getYPosition() 
						&& card1.getNumber() > c.getNumber() && c.getIsShowed()==true) {
					listaAuxiliar.add(c);
				}
			}
		}
		Card k = this.cardService.findAllCardsVacia(card.getXPosition(),card.getYPosition()+1);
		if(k == null) {
			for(Card c1 : cartasPosicion) {
				if(c1.getYPosition() == card1.getYPosition()-1 && c1.getIsShowed()== false) {
					c1.setIsShowed(true);
					this.cardService.saveCard(c1);
				}
			}

			card1.setXPosition(card.getXPosition());
			card1.setYPosition(card.getYPosition() + 1); 
			this.cardService.saveCard(card1);
			Integer i = 1;
			for(Card cartaAux : listaAuxiliar) {
				cartaAux.setXPosition(card.getXPosition());
				cartaAux.setYPosition(card1.getYPosition()+i);
				this.cardService.saveCard(cartaAux);
				i++;
			}
		}
		for(Card c : cartasF) {
			Card k1 = this.cardService.findAllCardsVacia(card1.getXPosition(),card1.getYPosition()+1);
			if((card1.getYPosition()==0 && card1.getXPosition()==1) || k1==null) {
				if(c.getNumber() == num -1 && c.getSuit().equals(card1.getSuit()) && c.getYPosition()==0 && (c.getXPosition()==5 ||
						c.getXPosition()==6 || c.getXPosition()==7 || c.getXPosition( )==8)) {
					for(Card c1 : cartasPosicion) {
						if(c1.getYPosition() == card1.getYPosition()-1 && c1.getIsShowed()== false) {
							c1.setIsShowed(true);
							this.cardService.saveCard(c1);
						}
					}
					card1.setXPosition(c.getXPosition());
					card1.setYPosition(c.getYPosition()); 
					card1.setIsShowed(false);
					this.cardService.saveCard(card1);
				}
			}
		}
		model.put("board", boardService.findById(boardId));
		return new ModelAndView("redirect:/difficult1");
	}


	@GetMapping(value = "/board/moveCardDeck")
	public ModelAndView moveCardDeck(Map<String, Object> model ) {
		List<Card> BarajaInicial = this.cardService.findAllCardsDeck(0, 0);
		List<Card> Carta10 = this.cardService.findAllCardsDeck(1, 0);
		if(BarajaInicial.size() > 0) {
			int num = (int) (Math.random() * (BarajaInicial.size())) ;
			Card initCard = BarajaInicial.get(num);
			if(Carta10.isEmpty()) {
				initCard.setXPosition(1);
				initCard.setYPosition(0);
				initCard.setIsShowed(true);
				this.cardService.saveCard(initCard);
				Carta10.add(initCard);
				BarajaInicial.remove(initCard);
			}else {
				Card c = Carta10.get(0);
				c.setXPosition(0);
				c.setYPosition(0);
				c.setIsShowed(false);
				this.cardService.saveCard(c);
				initCard.setXPosition(1);
				initCard.setYPosition(0);
				initCard.setIsShowed(true);
				this.cardService.saveCard(initCard);
				BarajaInicial.add(c);
				Carta10.remove(c);
			}
		}


		model.put("board", boardService.findById(1));
		return new ModelAndView("redirect:/difficult1");
	}


	//	====================================PlayZone=================================================================

	public PlayZone startGame() {
		List<Card> cards = cardService.findAll();
		Card cardAux = new Card();
		PlayZone playzone = new PlayZone(); 
		List<Card> listaAuxResultado = new ArrayList<>();
		for(Card c1:cards) {
			c1.setXPosition(0);
			c1.setYPosition(0);
			c1.setIsShowed(false); 
			this.cardService.saveCard(c1); 
		}
		for(int c = 2; c<9;c++) {
			Integer nF = 2;
			for(int f = 2; f<9;f++) {
				if(nF == c ) {
					int num = (int) (Math.random() * (cards.size())) ;
					cardAux = cards.get(num);
					cards.remove(cardAux);
					cardAux.setIsShowed(true);
					cardAux.setXPosition(c);
					cardAux.setYPosition(f);
					cardService.saveCard(cardAux);
					listaAuxResultado.add(cardAux);
					break;
				}else {
					int num = (int) (Math.random() * (cards.size())) ;
					cardAux = cards.get(num);
					cards.remove(cardAux);
					cardAux.setIsShowed(false);
					cardAux.setXPosition(c);
					cardAux.setYPosition(f);
					cardService.saveCard(cardAux);
					listaAuxResultado.add(cardAux);
					nF++;
				}
			}
		}
		playzone.setPlayZoneCard(listaAuxResultado);
		return playzone;
	}



	//	====================================Card=================================================================


	@GetMapping(value = "/card/{cardId}/edit")
	public String initUpdateCardForm(@PathVariable("cardId") int cardId, Model model) {
		Card card = this.cardService.findCardById(cardId);
		if(card.getNumber() == 1) {
			return "redirect: /board/moveCard/{cardId}";
		}else {
			model.addAttribute(card);
			return "card/createOrUpdateCardForm";
		}

	}

	@PostMapping(value = "/card/{cardId}/edit")
	public String processUpdateCardForm(@Valid Card card, BindingResult result,
			@PathVariable("cardId") int cardId) {
		if (result.hasErrors()) {
			return "card/createOrUpdateCardForm";
		}
		else {
			card.setId(cardId);
			this.cardService.saveCard(card);
			return "redirect:/difficult1";
		}

	}

}


