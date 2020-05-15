package com.tianju.memeo.service

import com.tianju.memeo.resource.Resource
import org.apache.commons.dbcp2.BasicDataSource

object MySqlConnectionPool {
  final val connectionPool = new BasicDataSource()
  connectionPool.setDriverClassName(Resource.driver)
  connectionPool.setUrl(Resource.dbUrl)
  connectionPool.setInitialSize(3)
  connectionPool.setUsername(Resource.dbUser)
  connectionPool.setPassword(Resource.dbPassword)
}
