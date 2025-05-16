package com.App.TradeApp.Service;

import com.App.TradeApp.Model.Asset;
import com.App.TradeApp.Model.Coin;
import com.App.TradeApp.Model.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(Long assetId);

    Asset getAssetByUserIdAndId(Long userId, Long assetId);

    List<Asset> getUsersAssets(Long userid);

    Asset updateAsset(Long assetID, double quantity);

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

    void deleteAsset(Long assetId);
}




