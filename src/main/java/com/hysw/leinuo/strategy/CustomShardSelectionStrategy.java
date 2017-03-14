package com.hysw.leinuo.strategy;

import com.hysw.leinuo.entity.ShardableEntity;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

import java.util.List;

/**
 * Created by leinuo on 17-2-16.
 */
public class CustomShardSelectionStrategy implements ShardSelectionStrategy {

    private List<ShardId> shardIds;

    public CustomShardSelectionStrategy(List<ShardId> shardIds){
        this.shardIds=shardIds;
    }
    public ShardId selectShardIdForNewObject(Object obj) {
        if(obj instanceof ShardableEntity) {
            String id = ((ShardableEntity)obj).getIdentifier();
            int sudId = Integer.valueOf(id.substring(id.length()-1));
            System.out.println("selection param.id="+ id);
            if(id==null) return this.shardIds.get(0);
          //  Integer i = new Integer(id.substring(0, 1));
            //our shard selection is identified by the
            //first char(number) in contact id
            //0-4 => shards0, 5-9 => shards1
            System.out.println("on continent:" + this.shardIds.get(sudId%2));
            return  this.shardIds.get(sudId%2);
        }
        //for non-shardable entities we just use shard0
        return this.shardIds.get(0);
    }
}
