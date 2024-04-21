package com.ovsenka.words.Controller;

import com.ovsenka.words.Entity.BotEntity;
import com.ovsenka.words.Entity.GameEntity;
import com.ovsenka.words.Entity.PrizeEntity;
import com.ovsenka.words.Entity.UserEntity;
import com.ovsenka.words.Repository.BotRepository;
import com.ovsenka.words.Repository.GameRepository;
import com.ovsenka.words.Repository.PrizeRepository;
import com.ovsenka.words.Repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/game")
public class GameController {

        private BotRepository botRepository;
        private GameRepository gameRepository;

        private UserRepository userRepository;

        private PrizeRepository prizeRepository;

        @Autowired
        public GameController(BotRepository botRepository, GameRepository gameRepository, UserRepository userRepository, PrizeRepository prizeRepository) {
                this.botRepository = botRepository;
                this.gameRepository = gameRepository;
                this.userRepository = userRepository;
                this.prizeRepository = prizeRepository;
        }



        @GetMapping()
        public String startGame(
                @RequestParam(value = "bot") Long botId
        ) {
                return "index.html";
        }

        @PostMapping(value = "/result")
        public ResponseEntity<String> gameResult(
                @RequestBody String recJson
        ){
                JSONObject jsonObject = new JSONObject(recJson);
                Long botId = jsonObject.getLong("bot_id");
                Long gameIndex = jsonObject.getLong("game_ind");
                int status = jsonObject.getInt("status");
                Long userId = jsonObject.getLong("chat_id");
                System.out.println("BOT="+botId);
                System.out.println(botId);
                System.out.println(gameIndex);
                System.out.println(status);
                System.out.println(userId);
                GameEntity gameEnt = new GameEntity();
                Optional<UserEntity> user = userRepository.findByChatId(String.valueOf(userId));
                Optional<BotEntity> bot = botRepository.findById(botId);
                List<PrizeEntity> prizes = prizeRepository.findAllByBot(bot.get());
                Optional<PrizeEntity> prize = null;
                if (gameIndex == 1L){
                        prize = prizes.stream().filter(num -> num.getNumber() == 1).findFirst();
                }
                else if (gameIndex == 3L){
                        prize = prizes.stream().filter(num -> num.getNumber() == 2).findFirst();
                }
                if (user.isPresent() && bot.isPresent()){
                        gameEnt.setUserId(user.get());
                        gameEnt.setResult(String.valueOf(status));
                        gameEnt.setBot(bot.get());
                        if (prize != null) gameEnt.setPrize(prize.get());
                        else gameEnt.setPrize(null);
                        gameEnt.setGameIndex(Math.toIntExact(gameIndex));
                        gameEnt.setResultTime(String.valueOf(System.currentTimeMillis()));
                        gameRepository.saveAndFlush(gameEnt);
                        return new ResponseEntity("Saved succesfully!", HttpStatus.OK);
                }
                return new ResponseEntity("Bad request!", HttpStatus.BAD_REQUEST);
        }


}
