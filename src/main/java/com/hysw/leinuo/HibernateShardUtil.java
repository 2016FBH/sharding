package com.hysw.leinuo;

import com.hysw.leinuo.strategy.CustomShardResolutionStrategy;
import com.hysw.leinuo.strategy.CustomShardSelectionStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.ShardedConfiguration;
import org.hibernate.shards.cfg.ConfigurationToShardConfigurationAdapter;
import org.hibernate.shards.strategy.ShardStrategy;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.strategy.ShardStrategyImpl;
import org.hibernate.shards.strategy.access.SequentialShardAccessStrategy;
import org.hibernate.shards.strategy.access.ShardAccessStrategy;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HibernateShardUtil {

	public static String USER_NAME = "root";
	public static String PASSWORD = "root";

	public static String URL0 = "jdbc:mysql://localhost:3306/test";
	public static String URL1 = "jdbc:mysql://localhost:3306/test1";
	public static String DATA_Dirver = "com.mysql.jdbc.Driver";


	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	static {
		try {
			Configuration config = new Configuration();
			config.configure("shard0.hibernate.cfg.xml");
			config.addResource("RecorderEntity.hbm.xml");
			List shardConfigs = new ArrayList();
			shardConfigs.add(new ConfigurationToShardConfigurationAdapter(
					new Configuration().configure("shard0.hibernate.cfg.xml")));
			shardConfigs.add(new ConfigurationToShardConfigurationAdapter(
					new Configuration().configure("shard1.hibernate.cfg.xml")));
			ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
			ShardedConfiguration shardedConfig = new ShardedConfiguration(
					config, shardConfigs, shardStrategyFactory);
			sessionFactory = shardedConfig.buildShardedSessionFactory();
		} catch (Throwable ex) {
			ex.printStackTrace();
			sessionFactory = null;
		}
	}

	static ShardStrategyFactory buildShardStrategyFactory() {
		ShardStrategyFactory shardStrategyFactory = new ShardStrategyFactory() {
			public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
				ShardSelectionStrategy pss = new CustomShardSelectionStrategy(shardIds);
				ShardResolutionStrategy prs = new CustomShardResolutionStrategy(
						shardIds);
				ShardAccessStrategy pas = new SequentialShardAccessStrategy();
				return new ShardStrategyImpl(pss, prs, pas);
			}
		};
		return shardStrategyFactory;
	}
}
