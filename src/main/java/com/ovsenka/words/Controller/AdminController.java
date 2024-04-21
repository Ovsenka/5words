package com.ovsenka.words.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ovsenka.words.Entity.BotEntity;
import com.ovsenka.words.Entity.GameEntity;
import com.ovsenka.words.Entity.PrizeEntity;
import com.ovsenka.words.Record.AuthResponse;
import com.ovsenka.words.Repository.BotRepository;
import com.ovsenka.words.Repository.GameRepository;
import com.ovsenka.words.Repository.PrizeRepository;
import com.ovsenka.words.Repository.StartInfoRepository;
import com.ovsenka.words.сonfig.AppConfiguration;
import jakarta.annotation.Nullable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private BotRepository botRepository;
    private PrizeRepository prizeRepository;

    private GameRepository gameRepository;

    private StartInfoRepository startInfoRepository;

    @Autowired
    public AdminController(BotRepository botRepository, PrizeRepository prizeRepository, GameRepository gameRepository, StartInfoRepository startInfoRepository) {
        this.botRepository = botRepository;
        this.prizeRepository = prizeRepository;
        this.gameRepository = gameRepository;
        this.startInfoRepository = startInfoRepository;
    }

    @GetMapping("/stats")
    public String getStats(Model model){
        User pr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = pr.getUsername();
        Optional<BotEntity> bot = botRepository.findByToken(username);
        List<GameEntity> games = gameRepository.findAllByBot(bot.get());
        Set<Long> users = new HashSet<>();
        int gameCount = 0, g1 = 0, g2 = 0;
        for(GameEntity game: games){
            if (!users.contains(game.getUserId().getId())) gameCount++;
            users.add(game.getUserId().getId());
            if (game.getPrize() != null && game.getResult().equals("1") && game.getPrize().getNumber() == 1L) g1++;
            if (game.getPrize() != null && game.getResult().equals("1") && game.getPrize().getNumber() == 2L) g2++;
        }
        long usersCount = startInfoRepository.findAllByBot(bot.get()).stream().count();
        model.addAttribute("bot_id", bot.get().getId());
        model.addAttribute("manager_link", bot.get().getManagerLink());
        model.addAttribute("game_count", gameCount);
        model.addAttribute("g1_count", g1);
        model.addAttribute("g2_count", g2);
        model.addAttribute("users", usersCount);
        return "admin/stats";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        User pr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = pr.getUsername();
        Optional<BotEntity> bot = botRepository.findByToken(username);
        if (!bot.get().getManagerLink().isBlank()) {
            model.addAttribute("managerLink", bot.get().getManagerLink());
        }
        prizeRepository.findAllByBot(bot.get())
                .stream()
                .forEach(prize -> {
                        if (prize.getNumber() == 1 &&
                                !prize.getTitle().isBlank()) {
                            model.addAttribute("gift1", prize.getTitle());
                        }
                        if (prize.getNumber() == 2 &&
                                !prize.getTitle().isBlank()) {
                            model.addAttribute("gift2", prize.getTitle());
                        }
                });
        return "admin/settings";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/settings")
    public String processSettings(
            Model model,
            RedirectAttributes redirAttrs,
            @RequestParam(value = "manager", required = false) String manager,
            @RequestParam(value = "gift1", required = false) String gift1,
            @RequestParam(value = "gift2", required = false) String gift2,
            @RequestParam(value = "file1", required = false) MultipartFile file1,
            @RequestParam(value = "file2", required = false) MultipartFile file2
    ) {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            User pr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = pr.getUsername();
            Optional<BotEntity> foundedBot = botRepository.findByToken(username);
//            System.out.println(manager);
//            System.out.println(gift1);
//            System.out.println(file1.toString());
//            System.out.println(gift2);
//            System.out.println(file2.toString());
//            System.out.println(gift1.isBlank());
//            System.out.println(gift2.isBlank());
//
//            System.out.println(file1.isEmpty());
//            System.out.println(file2.isEmpty());
            if (foundedBot.isPresent()) {
                BotEntity bot = foundedBot.get();
                Long botId = bot.getId();
                if (!manager.isBlank()) {
                    bot.setManagerLink(manager);
                    botRepository.save(bot);
                }
                if (prizeRepository.findAllByBot(bot).isEmpty()){
                    if (!gift1.isBlank()){
                        PrizeEntity newPrize = new PrizeEntity();
                        newPrize.setBot(bot);
                        newPrize.setTitle(gift1);
                        newPrize.setNumber(1L);
                        if (!file1.isEmpty()) {
                            try {
                                newPrize.setFile(file1.getBytes());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        prizeRepository.save(newPrize);
                    }
                    if (!gift2.isBlank()){
                        PrizeEntity newPrize = new PrizeEntity();
                        newPrize.setBot(bot);
                        newPrize.setTitle(gift2);
                        newPrize.setNumber(2L);
                        if (!file2.isEmpty()) {
                            try {
                                newPrize.setFile(file2.getBytes());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        prizeRepository.save(newPrize);
                    }

                }
                prizeRepository.findAllByBot(bot)
                        .stream()
                        .forEach(prize -> {
                            if (prize.getNumber() == 1) {
                                if (!gift1.isBlank()) prize.setTitle(gift1);
                                if (!file1.isEmpty()) {
                                    try {
                                        prize.setFile(file1.getBytes());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);

                                    }
                                }
                            }
                            if (prize.getNumber() == 2) {
                                if (!gift2.isBlank()) prize.setTitle(gift2);
                                if (!file2.isEmpty()) {
                                    try {
                                        prize.setFile(file2.getBytes());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            prizeRepository.save(prize);
                        });
            }
        }
        redirAttrs.addFlashAttribute("success", "Настройки успешно обновлены!");
        return "redirect:/admin/settings";
    }

    @GetMapping("/sender")
    public String getSenderPage(Model model) {
        return "admin/sender";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/sender")
    public String sendMsg(
            Model model,
            RedirectAttributes redirAttrs,
            @RequestParam String text
    ) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject senderRequest = new JSONObject();
        User pr = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = pr.getUsername();
        Optional<BotEntity> bot = botRepository.findByToken(username);
        senderRequest.put("key", username);
        senderRequest.put("bot_id", bot.get().getId());
        senderRequest.put("text", text);
        senderRequest.put("image", "string");
        senderRequest.put("video", "string");

        HttpEntity<String> request =
                new HttpEntity<String>(senderRequest.toString(), headers);

        ResponseEntity<String> responseEntity = restTemplate.
                postForEntity(AppConfiguration.API_SENDER_URI, request, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            redirAttrs.addFlashAttribute("success", "Рассылка выполнена успешно!");
            return "redirect:/admin/sender";
        }
        redirAttrs.addFlashAttribute("error", "Error! "+responseEntity.getBody());
        return "redirect:/admin/sender";
    }
    //return new ModelAndView("/admin/sender?success");
    // return new ModelAndView("/admin/sender?error");

    @GetMapping("/select")
    public String getSelectPage() {
        return "admin/select";
    }

    @GetMapping()
    public String getAdminPanel() {
        return "redirect:/admin/select";
    }

}
