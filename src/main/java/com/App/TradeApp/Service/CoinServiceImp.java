package com.App.TradeApp.Service;

import com.App.TradeApp.Model.Coin;
import com.App.TradeApp.Repository.CoinRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class CoinServiceImp implements CoinService {

    @Autowired
    private CoinRepo coinRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page) {
        String url = "https://api.coingecko.com/api/v3/coins/market?vs_currency=usd&per_page=10&page=" + page;

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            List<Coin> coinList = objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {
            });

            return coinList;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public String getMarketChart(String coinId, int days) {


        String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days;

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Coin findById(String coinId) {

        Optional<Coin> coin = coinRepo.findById(coinId);
        if(coin.isEmpty()) {
            throw new RuntimeException("coin not found");
        }
        return coin.get();
    }

    @Override
    public String searchCoin(String keyword) {
        String url = "https://api.coingecko.com/api/v3/search?query=" + keyword;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);


        return response.getBody();
    }

    @Override
    public String getTop50() {

        String url = "https://api.coingecko.com/api/v3/coins/markets/vs_currency=usd&per_page=50&page=1" ;

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTreadingCoins() {

        String url = "https://api.coingecko.com/api/v3/search/treading";

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCoinDetails(String coinId) {
        String url = "https://api.coingecko.com/api/v3/coins/" + coinId;

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            JsonNode node = objectMapper.readTree(response.getBody());

            Coin coin = new Coin();

            coin.setId(node.get("id").asText());
            coin.setName(node.get("name").asText());
            coin.setSymbol(node.get("symbol").asText());
            coin.setImage(node.get("image").get("large").asText());

            JsonNode marketData = node.get("market_data");
            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(marketData.get("market_Cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change24h").get("usd").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_ChangePercentage24h").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_ChangePercentage24h").asLong());
            coin.setTotalSupply(marketData.get("total_supply").asLong());
            coinRepo.save(coin);

            return response.getBody();
        } catch (RestClientException | JsonProcessingException e) {
            throw new RuntimeException(e);

        }
    }
}
