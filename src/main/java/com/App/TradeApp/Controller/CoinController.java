package com.App.TradeApp.Controller;


import com.App.TradeApp.Model.Coin;
import com.App.TradeApp.Service.CoinService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page) throws Exception{
        List<Coin> coinList = coinService.getCoinList(page);
        return new ResponseEntity<>(coinList, HttpStatus.ACCEPTED);

    }

    @GetMapping("/{coinId}/chart")
    ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,@RequestParam("days") int days) throws Exception{
        String coinList = coinService.getMarketChart(coinId,days);
        JsonNode node = objectMapper.readTree(coinList);
        return new ResponseEntity<>(node, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode> SearchCoin(@RequestParam("p") String keywork) throws Exception{
        String coinList = coinService.searchCoin(keywork);
        JsonNode node = objectMapper.readTree(coinList);
        return ResponseEntity.ok(node);
    }

    @GetMapping("/top50")
    ResponseEntity<JsonNode> getTop50Coin() throws Exception{
        String coinList = coinService.getTop50();
        JsonNode node = objectMapper.readTree(coinList);
        return ResponseEntity.ok(node);
    }

    @GetMapping("/treading")
    ResponseEntity<JsonNode> getTreadingCoin() throws Exception{
        String coinList = coinService.getTreadingCoins();
        JsonNode node = objectMapper.readTree(coinList);
        return ResponseEntity.ok(node);
    }

    @GetMapping("/details/{coinId}")
    ResponseEntity<JsonNode> getCoinDetail(@PathVariable String coinId) throws Exception{
        String coinList = coinService.getCoinDetails(coinId);
        JsonNode node = objectMapper.readTree(coinList);
        return ResponseEntity.ok(node);
    }



}
