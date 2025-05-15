package com.App.TradeApp.Service;

import com.App.TradeApp.Model.Asset;
import com.App.TradeApp.Model.Coin;
import com.App.TradeApp.Model.User;
import com.App.TradeApp.Repository.AssetRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetRepo assetRepo;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return  asset;
    }

    @Override
    public Asset getAssetById(Long assetId) {
        return assetRepo.findById(assetId);
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAssets(Long userid) {
        return assetRepo.findByUserId(userid);
    }

    @Override
    public Asset updateAsset(Long assetID, double quantity) {
        Asset oldAsset = getAssetById(assetID);
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());

        return assetRepo.save(oldAsset);

    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepo.findByUserIdAndCoinId(userId, Long.valueOf(coinId));
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepo.deleteById(assetId);
    }
}
