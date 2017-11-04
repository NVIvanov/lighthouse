package org.lighthouse.crawler.services.impl

import java.time.LocalDateTime

import org.lighthouse.api.Crawler
import org.lighthouse.crawler.services.CrawlerExecutor
import org.lighthouse.domain.service.SettingsService
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
  * @author nivanov 
  *         on 02.11.2017.
  */

class CrawlerExecutorImpl extends CrawlerExecutor{
  val logger: Logger = CrawlerExecutorImpl.logger
  val crawlerQueue: mutable.Set[Crawler] = new mutable.LinkedHashSet[Crawler]()

  @Autowired
  var crawlers: java.util.List[Crawler]  = _

  @Autowired
  var settingsService: SettingsService = _

  override def execute(): Unit = {
    var crawler: Crawler = null
    if (crawlerQueue.isEmpty) {
      val crawlerList = crawlers.toList
        .filter(c => c.updated() == null || c.updated().plusDays(settingsService.getCountryInterval.longValue()).isBefore(LocalDateTime.now()))
        .sortWith((c1, c2) => c1.updated() == null || c1.updated().isAfter(c2.updated()))
      if (crawlerList.isEmpty) {
        return
      }
      crawler = crawlerList.head
    } else {
      crawler = crawlerQueue.head
      crawlerQueue.remove(crawler)
    }

    if (crawler.isStarted.get()) {
      logger.info("Crawler ещё выполняется")
    } else {
      crawler.start()
      logger.info("Crawler запущен")
    }
  }

  override def execute(countries: String*): Unit = {
    val sendToQueue = crawlers.filter(c => countries.contains(c.countryName()))
    crawlerQueue ++= sendToQueue
  }

  override def status: Status = {
    if (crawlers.exists(c => c.isStarted.get()))
      Status.Busy
    else
      Status.Ready
  }
}


object CrawlerExecutorImpl {
  def logger: Logger = LoggerFactory.getLogger(classOf[CrawlerExecutor])
}