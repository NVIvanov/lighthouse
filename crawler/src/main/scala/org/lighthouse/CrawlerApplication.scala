package org.lighthouse

import net.ruippeixotog.scalascraper.browser.{HtmlUnitBrowser, JsoupBrowser}
import net.ruippeixotog.scalascraper.util.ProxyUtils
import org.jsoup.Connection
import org.lighthouse.api.Crawler
import org.lighthouse.crawler.impl.BulgaryCrawler
import org.lighthouse.crawler.services.CrawlerExecutor
import org.lighthouse.crawler.services.impl.CrawlerExecutorImpl
import org.lighthouse.domain.service.SettingsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.{CommandLineRunner, SpringApplication}
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.support.CronTrigger

/**
 * @author nivanov
 * on 22.10.2017.
 */

object CrawlerApplication extends App{
    SpringApplication.run(classOf[Config], args:_*)
}


@SpringBootApplication
class Config {

  @Autowired
  var crawlers: java.util.List[Crawler] = _

  @Autowired
  var settingsService: SettingsService = _

  def crawlerExecutor: CrawlerExecutor = crawlerExecutor(settingsService, crawlers)

  @Bean
  def commandLineRunner: CommandLineRunner = {
    new CommandLineRunner {
      override def run(args: String*): Unit = {
        if (settingsService.isProxyEnabled) {
          ProxyUtils.setProxy(settingsService.getProxyHost, settingsService.getProxyPort)
        }

        taskScheduler.schedule(new Runnable {
          override def run(): Unit = crawlerExecutor.execute()
        }, new CronTrigger(settingsService.getIndexingInterval))
      }
    }
  }

  @Bean
  def browser(): HtmlUnitBrowser = {
    new HtmlUnitBrowser()
  }

  @Bean
  def jsoupBrowser(): JsoupBrowser = {
    new JsoupBrowser() {
      override def defaultRequestSettings(conn: Connection): Connection = {
        super.defaultRequestSettings(conn)
          .header("Accept", "text/html,application/xhtml+xml,application/xml,text/xsl")
      }
    }
  }

  import org.springframework.context.annotation.Bean
  import org.springframework.scheduling.TaskScheduler
  import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

  @Bean
  def taskScheduler: TaskScheduler = {
    new ThreadPoolTaskScheduler
  }

  @Bean
  def crawlerExecutor(@Autowired settingsService: SettingsService, @Autowired crawlers: java.util.List[Crawler]): CrawlerExecutor = {
    val executor = new CrawlerExecutorImpl
    executor.settingsService = settingsService
    executor.crawlers = crawlers
    executor
  }

  @Bean
  def bulgaryCrawler(): Crawler = {
    new BulgaryCrawler(browser(), jsoupBrowser())
  }
}