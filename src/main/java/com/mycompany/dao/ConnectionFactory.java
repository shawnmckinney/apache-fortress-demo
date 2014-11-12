/*
 * This is free and unencumbered software released into the public domain.
 */

package com.mycompany.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Used to generate a connection factory object used by Mybatis DAO impls.
 *
 * @author Shawn McKinney
 * @version $Rev$
 */

@Component
public class ConnectionFactory
{
	private static SqlSessionFactory sqlSessionFactory;	
	
    @Autowired(required=true)
    public void setSqlSessionFactory (SqlSessionFactory sqlSessionFactory)
    {
    	ConnectionFactory.sqlSessionFactory = sqlSessionFactory;
        return; 
    }

    protected static SqlSessionFactory getSqlSessionFactory() {
    	return ConnectionFactory.sqlSessionFactory;
    }
    
}
