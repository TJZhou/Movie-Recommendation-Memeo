package com.tianju.memeo.model

import scala.collection.mutable.ArrayBuffer

case class ALSModel(userId: Long, recommendations: ArrayBuffer[(Long, Float)])
