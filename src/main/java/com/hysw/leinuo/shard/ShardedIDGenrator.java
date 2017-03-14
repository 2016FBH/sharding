package com.hysw.leinuo.shard;

import com.hysw.leinuo.HibernateShardUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShardedIDGenrator implements IdentifierGenerator {

    static Long id;

    static {
        id = 0l;
        try {
            Class.forName(HibernateShardUtil.DATA_Dirver);
            Connection con = DriverManager.getConnection(HibernateShardUtil.URL0, HibernateShardUtil.USER_NAME, HibernateShardUtil.PASSWORD);
            Statement stmt = con.createStatement();
            String query = "select max(id) from recorder";
            ResultSet rset = stmt.executeQuery(query);
            if (rset.next()) {
                Long tempid = rset.getLong(1);
                if (id < tempid)
                    id = tempid;
            }
            stmt.close();
            con.close();
            con = DriverManager.getConnection(HibernateShardUtil.URL1, HibernateShardUtil.USER_NAME, HibernateShardUtil.PASSWORD);
            stmt = con.createStatement();
            query = "select max(id) from recorder";
            rset = stmt.executeQuery(query);
            if (rset.next()) {
                Long tempid = rset.getLong(1);
                if (id < tempid)
                    id = tempid;
            }
            System.out.println(id);
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Serializable generate(SessionImplementor arg0, Object arg1)
            throws HibernateException {
        id = id + 1;
        return id;
    }

}
