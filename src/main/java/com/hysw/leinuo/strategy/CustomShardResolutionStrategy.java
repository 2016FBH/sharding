package com.hysw.leinuo.strategy;

import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.ShardResolutionStrategyData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leinuo on 17-2-16.
 */
public class CustomShardResolutionStrategy implements ShardResolutionStrategy {

    private List<ShardId> shardIds;

    public CustomShardResolutionStrategy(List<ShardId> shardIds){
        this.shardIds = shardIds;
        for (ShardId id : shardIds) {
            System.out.println("id=" + id);
        }
    }

    public List selectShardIdsFromShardResolutionStrategyData(
            ShardResolutionStrategyData arg0){
       /* List ids = new ArrayList();
        String id = (String)arg0.getId();
        System.out.println("resolution param.id="+ id);
        if(id==null || id.length() == 0) ids.add(this.shardIds.get(0));
        else{
            //our shard selection is identified by the
            //first char(number) in contact id
            //0-4 => shards0, 5-9 => shards1
            Integer i = new Integer(id.substring(0, 1));
            ids.add(this.shardIds.get(i%2));
        }*/
        return this.shardIds;
    }
}
